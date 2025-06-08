package com.otc.survey.modules.core.endpoint.interceptor;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.otc.survey.modules.common.domain.annotation.Interceptor;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.endpoint.interceptor.BaseInterceptor;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.entity.AuthSession.SessionStatus;
import com.otc.survey.modules.core.domain.entity.User;
import com.otc.survey.modules.core.domain.model.UserProfile;
import com.otc.survey.modules.core.domain.repository.AuthSessionRepository;
import com.otc.survey.modules.core.domain.service.AuthConfigService;
import com.otc.survey.modules.core.domain.service.user.UserService;
import com.otc.survey.modules.core.domain.service.user.exception.UserException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(3)
@Interceptor(interceptPaths = {".*/api/.*"})
public class AuthenInterceptor extends BaseInterceptor
{
	@Autowired
	protected AuthConfigService authConfigService;
	
	@Autowired
	protected AuthSessionRepository authSessionRepository;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected ThreadPoolTaskExecutor taskExecutor;
	
	
	@Override
	protected void intercept(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		String accessToken = extractAccessToken(request);
		logger.debug("accessToken => {}", accessToken);
		
		if(accessToken == null || accessToken.isBlank()) {
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.UNAUTHORIZED, 
					"401001", 
					"Access Deined", 
					"Couldn't extract 'survey-access-token'"
				);
			
			return;
		}
		
		AuthSession authSession = authSessionRepository.findByAccessToken(accessToken);
		if(! validateSession(request, response, authSession)) {
			return;
		}
		getRequestInfo(request).setAuthSession(authSession);
		
		UserProfile userProfile = loadUserProfile(request, response, authSession);
		if(! validateUserProfile(request, response, userProfile)) {
			return;
		}
		getRequestInfo(request).setUserProfile(userProfile);
		
		getRequestInfo(request).setAuthenticated(true);
		
		taskExecutor.submit(() -> extendSession(authSession));
		
		filterChain.doFilter(request, response);
	}
	
	protected boolean validateSession(HttpServletRequest request, HttpServletResponse response, AuthSession authSession)
	{
		if(authSession == null) {
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.UNAUTHORIZED, 
					"401002", 
					"Access Deined", 
					"Invalid access token"
				);
			
			return false;
		}
		
		if(authSession.getSessionStatus() != SessionStatus.Activated) {
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.UNAUTHORIZED, 
					"401003", 
					"Access Deined", 
					"Session not activated"
				);
			
			return false;
		}
		
		if(authSession.getExpiredAt().before(new Date())) {
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.UNAUTHORIZED, 
					"401004", 
					"Access Deined", 
					"Session expired"
				);
			
			return false;
		}
		
		return true;
	}
	
	protected UserProfile loadUserProfile(HttpServletRequest request, HttpServletResponse response, AuthSession authSession)
	{
		try {
			return userService.buildUserProfile(req -> {
				setupServiceRequest(request, req);
				req.setUserId(authSession.getUserId());
			}).getUserProfile();
		} catch (UserException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	protected boolean validateUserProfile(HttpServletRequest request, HttpServletResponse response, UserProfile userProfile)
	{
		if(userProfile == null || userProfile.getUser() == null) {
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.UNAUTHORIZED, 
					"401101", 
					"Access Deined", 
					"User Account Not Found"
				);
			
			return false;
		}
		
		User user = userProfile.getUser();
		
		if(user.getStatus() != StatusCode.Active) {
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.UNAUTHORIZED, 
					"401102", 
					"Access Deined", 
					"User Account Status Not Active"
				);
			
			return false;
		}
		
		if(user.getAccountExpiredAt() != null && user.getAccountActivatedAt().after(new Date())) {
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.UNAUTHORIZED, 
					"401103", 
					"Access Deined", 
					"User Account Expired"
				);
			
			return false;
		}
		
		return true;
	}
	
	protected void extendSession(AuthSession authSession)
	{
		logger.info("### {}.extendSession ###", getClass().getSimpleName());
		
		Date now = new Date();
		
		authSession.setLastVerifiedAt(now);
		authSession.setLastAccessedAt(now);
		authSession.setExpiredAt(new Date(now.getTime() + getSessionExpiredDuration()));
		
		authSessionRepository.save(authSession);
	}
	
	protected long getSessionExpiredDuration()
	{
		return authConfigService.getSessionExpiredDuration();
	}
	
	@Override
	protected boolean shouldIntercept(HttpServletRequest request) throws ServletException {
		return super.shouldIntercept(request) && shouldAuthen(request);
	}
}