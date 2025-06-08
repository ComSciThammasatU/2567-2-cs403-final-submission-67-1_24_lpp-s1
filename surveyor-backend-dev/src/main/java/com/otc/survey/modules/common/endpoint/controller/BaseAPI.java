package com.otc.survey.modules.common.endpoint.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.otc.survey.modules.common.domain.model.HttpRequestInfo;
import com.otc.survey.modules.common.domain.model.HttpRequestInfoContainer;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.model.UserProfile;

import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseAPI 
{
	public static final String DEFAULT_SUCCESS_STATUS_CODE = "200";
	public static final String DEFAULT_SUCCESS_STATUS_MSG_TITLE = ResponseMessage.Head.Status.Type.Success.name();
	public static final String DEFAULT_SUCCESS_STATUS_MSG_MESSAGE = ResponseMessage.Head.Status.Type.Success.name();
	
	public static final String DEFAULT_ERROR_STATUS_CODE = "500999";
	public static final String DEFAULT_ERROR_STATUS_MSG_TITLE = ResponseMessage.Head.Status.Type.Error.name();
	public static final String DEFAULT_ERROR_STATUS_MSG_MESSAGE = ResponseMessage.Head.Status.Type.Error.name();
	
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected HttpRequestInfo getRequestInfo(HttpServletRequest request) {
		return HttpRequestInfoContainer.get();
	}
	
	protected String getTraceId(HttpServletRequest request) {
		return getRequestInfo(request).getTraceId();
	}
	
	protected String getRequestId(HttpServletRequest request) {
		return getRequestInfo(request).getRequestId();
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
	
	
	// ****************************** replySuccess ****************************** //
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request)
    {
    	return replySuccess(request, DEFAULT_SUCCESS_STATUS_CODE, null);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request, B body)
    {
    	return replySuccess(request, DEFAULT_SUCCESS_STATUS_CODE, body);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request, String statusCode)
    {
    	return replySuccess(request, statusCode, null);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request, String statusCode, String statusTitle, String statusMessage)
    {
    	return replySuccess(request, statusCode, statusTitle, statusMessage, null);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request, String statusCode, B body)
    {
    	return replySuccess(request, statusCode, DEFAULT_SUCCESS_STATUS_MSG_TITLE, DEFAULT_SUCCESS_STATUS_MSG_MESSAGE, body);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replySuccess(HttpServletRequest request, String statusCode, String statusTitle, String statusMessage, B body)
    {
    	ResponseMessage.Head.Status status = new ResponseMessage.Head.Status();
    	status.setType(ResponseMessage.Head.Status.Type.Success);
    	status.setCode(statusCode);
    	status.setTitle(statusTitle);
    	status.setMessage(statusMessage);
    	
    	//List<ResponseMessage.Head.Metadata> metadatas = buildMetadatas(request);
    	
    	ResponseMessage.Head head = new ResponseMessage.Head();
    	head.setTraceId(getTraceId(request));
    	head.setRequestId(getRequestId(request));
    	head.setStatus(status);
    	head.setRequestInfo(getRequestInfo(request));
    	//head.setMetadatas(metadatas);
    	
    	ResponseMessage<B> responseMessage = new ResponseMessage<B>();
    	responseMessage.setHead(head);
        responseMessage.setBody(body);
        
        return ResponseEntity.ok(responseMessage);
    }
    // ****************************** replySuccess ****************************** //
    
    
    // ****************************** replyError ****************************** //
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replyError(HttpServletRequest request, String errorCode, String errorTitle, String errorMessage)
    {
    	return replyError(request, errorCode, errorTitle, errorMessage, null);
    }
    
    protected <B extends ResponseMessage.Body> ResponseEntity<ResponseMessage<B>> replyError(HttpServletRequest request, String errorCode, String errorTitle, String errorMessage, Map<String, Object> payload)
    {
    	ResponseMessage.Head.Status status = new ResponseMessage.Head.Status();
    	status.setType(ResponseMessage.Head.Status.Type.Error);
    	status.setCode(errorCode);
    	status.setTitle(errorTitle);
    	status.setMessage(errorMessage);
    	
    	//List<ResponseMessage.Head.Metadata> metadatas = buildMetadatas(request);
        
        ResponseMessage.Head head = new ResponseMessage.Head();
        head.setTraceId(getTraceId(request));
        head.setRequestId(getRequestId(request));
    	head.setStatus(status);
    	head.setRequestInfo(getRequestInfo(request));
    	head.setPayload(payload);
    	//head.setMetadatas(metadatas);
        
        ResponseMessage<B> responseMessage = new ResponseMessage<>();
        responseMessage.setHead(head);
        
        return ResponseEntity.ok(responseMessage);
    }
}