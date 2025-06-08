package com.otc.survey.modules.common.endpoint.interceptor;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.domain.annotation.Interceptor;
import com.otc.survey.modules.common.domain.constant.ApplicationConfigConst;
import com.otc.survey.modules.common.domain.model.HttpRequestInfo;
import com.otc.survey.modules.common.domain.model.HttpRequestInfoContainer;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.util.JsonUtil;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.BaseAPI;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.model.UserProfile;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseInterceptor extends OncePerRequestFilter 
{
	public static final String REGEX_ENDPOINT_API = ".*/api/.*";
	public static final String REGEX_ENDPOINT_ACTUATOR = ".*/actuator/.*";
	public static final String REGEX_ENDPOINT_RESOURCES = ".*/resources/.*";
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected WebApplicationContext applicationContext;
	
	@Autowired
	@Qualifier("requestMappingHandlerMapping")
	protected RequestMappingHandlerMapping controllerMapping;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		
		if(isPreflightRequest(request, response)) {
			logger.info("Preflight Request [{} : {}]", request.getMethod(), request.getRequestURI());
			response.sendError(HttpStatus.OK.value());
			return;
		}
		
		if(matchesActuatorEndpoint(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if(shouldIntercept(request)) {
			logger.info("### {}.intercept ###", getClass().getSimpleName());
			getRequestInfo(request).getExecutedInterceptors().add(getClass().getSimpleName());
			
			intercept(request, response, filterChain);
		} else {
			filterChain.doFilter(request, response);
		}
		
		HttpRequestInfoContainer.set(null);
	}
	
	protected boolean isPreflightRequest(HttpServletRequest request, HttpServletResponse response)
	{
		return request.getMethod().equals(HttpMethod.OPTIONS.name()) && matchesAPIEndpoint(request);
	}
	
	protected boolean matchesRequest(HttpServletRequest request) 
	{
		Interceptor interceptor = getClass().getAnnotation(Interceptor.class);
		
		if(interceptor == null) {
			return false;
		}
		
		String[] interceptPaths = interceptor.interceptPaths();
		String[] ignorePaths = interceptor.ignorePaths();
		
		if((interceptPaths == null || interceptPaths.length == 0) && (ignorePaths == null || ignorePaths.length == 0)) {
			return true;
		}
		
		if(ignorePaths != null && ignorePaths.length > 0) {
			for(String ignorePath : ignorePaths) {
				if(Pattern.matches(ignorePath, request.getRequestURI())) {
					return false;
				}
			}
		}
		
		if(interceptPaths != null && interceptPaths.length > 0) {
			for(String interceptPath : interceptPaths) {
				if(Pattern.matches(interceptPath, request.getRequestURI())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected boolean shouldIntercept(HttpServletRequest request) throws ServletException {
		return matchesRequest(request) && !super.shouldNotFilter(request);
	}
	
	protected abstract void intercept(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;
	
	protected String extractUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}
	
	protected String extractAccessToken(HttpServletRequest request) {
		String accessToken = request.getHeader("survey-access-token");
		
		if(accessToken == null || accessToken.isBlank()) {
			accessToken = request.getParameter("survey-access-token");
		}
		
		if(accessToken == null || accessToken.isBlank()) {
			accessToken = request.getHeader("Authorization");
			
			if(accessToken != null && !accessToken.isBlank() && accessToken.startsWith("Bearer ")) {
				accessToken = accessToken.replace("Bearer ", "");
			}
		}
		
		return accessToken;
	}
	
	protected String extractTraceId(HttpServletRequest request) {
		return request.getHeader(ApplicationConfigConst.HTTP_HEADER_TRACE_ID);
	}
	
	protected HttpRequestInfo initialRequestInfo(HttpServletRequest request) {
		logger.debug("### {}.initialRequestInfo ###", getClass().getSimpleName());
		HttpRequestInfoContainer.set(new HttpRequestInfo());
		return HttpRequestInfoContainer.get();
	}
	
	protected HttpRequestInfo getRequestInfo(HttpServletRequest request) {
		HttpRequestInfo requestInfo = HttpRequestInfoContainer.get();
		if(requestInfo == null) {
			requestInfo = initialRequestInfo(request);
		}
		return requestInfo;
	}
	
	protected String getTraceId(HttpServletRequest request) {
		return getRequestInfo(request).getTraceId();
	}
	
	protected String getRequestId(HttpServletRequest request) {
		return getRequestInfo(request).getTraceId();
	}
	
	protected AuthSession getAuthSession(HttpServletRequest request) {
		return getRequestInfo(request).getAuthSession();
	}
	
	protected UserProfile getUserProfile(HttpServletRequest request) {
		return getRequestInfo(request).getUserProfile();
	}
	
	protected void setupServiceRequest(HttpServletRequest request, BaseServiceCommandRequest commandRequest)
    {
        AuthSession authSession = getAuthSession(request);
        UserProfile userProfile = getUserProfile(request);
        
        commandRequest.setPerformedAPI(request.getRequestURI());
        if(authSession != null) {
        	commandRequest.setPerformedPlatform(authSession.getClientAppPlatform());
        	commandRequest.setPerformedAuthSession(authSession);
        }
        if(userProfile != null) {
        	commandRequest.setPerformedBy(userProfile.getUser().getId());
        	commandRequest.setPerformedUserProfile(userProfile);
        }
        commandRequest.setPerformedTraceId(getTraceId(request));
        commandRequest.setPerformedRequestId(getRequestId(request));
        //commandRequest.setPerformedProgram(getCurrentProgram(request));
        commandRequest.setPerformedAt(new Date());
        commandRequest.setRequestInfo(getRequestInfo(request));
    }
	
	protected WebApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	protected boolean matchesAPIEndpoint(HttpServletRequest request) {
		return Pattern.matches(REGEX_ENDPOINT_API, request.getRequestURI());
	}
	
	protected boolean matchesActuatorEndpoint(HttpServletRequest request) {
		return Pattern.matches(REGEX_ENDPOINT_ACTUATOR, request.getRequestURI());
	}
	
	protected boolean matchesResourcesEndpoint(HttpServletRequest request) {
		return Pattern.matches(REGEX_ENDPOINT_RESOURCES, request.getRequestURI());
	}
	
	protected HandlerMethod getHandlerMethod(HttpServletRequest request) {
		try {
			HandlerExecutionChain handlerExecutionChain = controllerMapping.getHandler(request);
			Object handler = handlerExecutionChain != null ? handlerExecutionChain.getHandler() : null;
			if(handler != null && handler instanceof HandlerMethod handlerMethod) {
				return handlerMethod;
			}
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		return null;
	}
	
	protected Object getHandlerBean(HttpServletRequest request) {
		HandlerMethod handlerMethod = getHandlerMethod(request);
		
		if(handlerMethod == null) {
			return null;
		}
		
		return handlerMethod.getBean();
	}
	
	protected Auth getAuthConfig(HttpServletRequest request) {
		if(! matchesAPIEndpoint(request)) {
			return null;
		}
		
		HandlerMethod handlerMethod = getHandlerMethod(request);
		if(handlerMethod == null) {
			return null;
		}
		
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		if(auth == null) {
			Object handlerBean = getHandlerBean(request);
			if(handlerBean != null) {
				auth = handlerBean.getClass().getAnnotation(Auth.class);
			}
		}
		
		logger.debug("auth => {}", auth);
		
		return auth; 
	}
	
	protected boolean shouldAuthen(HttpServletRequest request) {
		Auth apiController = getAuthConfig(request);
		return apiController != null && apiController.authen();
	}
	
	protected boolean shouldAuthorize(HttpServletRequest request) {
		Auth apiController = getAuthConfig(request);
		return apiController != null && apiController.authorize();
	}
	
	protected BaseAPI getAPI(HttpServletRequest request) {
		if(! matchesAPIEndpoint(request)) {
			return null;
		}
		
		HandlerMethod handlerMethod = getHandlerMethod(request);
		
		if(handlerMethod == null || handlerMethod.getBean() == null) {
			return null;
		}
		
		return (BaseAPI) handlerMethod.getBean();
	}
	
	protected String getRequestorUserId(HttpServletRequest request) {
		return request.getParameter("userId");
	}
	
	/*
	protected List<String> getRequestorRoleIds(HttpServletRequest request) {
		String[] roleIds = request.getParameterValues("roleIds");
		return (roleIds != null && roleIds.length > 0) ? List.of(roleIds) : List.of();
	}
	*/
	
	protected List<String> getRequestorRoleIds(HttpServletRequest request) {
		UserProfile userProfile = getUserProfile(request);
		if (userProfile != null && userProfile.getRoles() != null && !userProfile.getRoles().isEmpty()) {
			return userProfile.getRoles()
					.stream()
					.filter(e -> e != null && e.getId() != null)
					.map(e -> e.getId())
					.toList();
		}
		return List.of();
	}
	
	protected List<String> getGrantedRoleIds(HttpServletRequest request) {
		Auth auth = getAuthConfig(request);
		return (auth != null && auth.grantedRoleIds().length > 0) ? List.of(auth.grantedRoleIds()) : List.of();
	}
	
	
	// ****************************** replyErrorMessage ****************************** //
	protected void replyErrorMessage(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus, String errorCode, String errorTitle, String errorMessage) 
	{
		response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
        	JsonUtil.writeValue(response.getWriter(), buildErrorMessage(request, errorCode, errorTitle, errorMessage));
        } catch(Exception ex) {
        	logger.error(ex.getMessage(), ex);
        }
	}
	
	protected ResponseMessage<?> buildErrorMessage(HttpServletRequest request, String errorCode, String errorTitle, String errorMessage)
	{
		ResponseMessage.Head.Status status = new ResponseMessage.Head.Status();
    	status.setType(ResponseMessage.Head.Status.Type.Error);
    	status.setCode(errorCode);
    	status.setTitle(errorTitle);
    	status.setMessage(errorMessage);
    	
    	ResponseMessage.Head head = new ResponseMessage.Head();
    	head.setTraceId(getRequestInfo(request).getTraceId());
    	head.setRequestId(getRequestInfo(request).getRequestId());
    	head.setStatus(status);
    	head.setRequestInfo(getRequestInfo(request));
        
        ResponseMessage<?> responseMessage = new ResponseMessage<>();
        responseMessage.setHead(head);
        
        return responseMessage;
	}
	// ****************************** replyErrorMessage ****************************** //
}