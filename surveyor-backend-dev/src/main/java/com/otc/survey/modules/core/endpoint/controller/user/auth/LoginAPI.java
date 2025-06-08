package com.otc.survey.modules.core.endpoint.controller.user.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.model.ClientAppInfo;
import com.otc.survey.modules.core.domain.model.UserProfile;
import com.otc.survey.modules.core.domain.service.user.LoginCommand;
import com.otc.survey.modules.core.domain.service.user.UserService;
import com.otc.survey.modules.core.domain.service.user.exception.LoginException;
import com.otc.survey.modules.core.domain.service.user.exception.UserException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/user/auth/login")
public class LoginAPI extends AbstractJsonBodyAPI<LoginAPI.RequestMessage, LoginAPI.BodyResponseMessage>
{
	@Autowired
	protected UserService userService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(
					request, 
					"400001", 
					"Invalid Request Message", 
					"RequestMessage couldn't be null"
				);
		}
		
		if(requestMessage.getUsername() == null || requestMessage.getUsername().isBlank()) {
			return replyError(
					request, 
					"400002", 
					"Invalid Request Message", 
					"Param 'username' couldn't be null or empty"
				);
		}
		
		if(requestMessage.getPassword() == null || requestMessage.getPassword().isBlank()) {
			return replyError(
					request, 
					"400002", 
					"Invalid Request Message", 
					"Param 'password' couldn't be null or empty"
				);
		}
		
		try {
			LoginCommand.Response loginResponse = userService.login(req -> {
				setupServiceRequest(request, req);
				req.setClientAppInfo(requestMessage.getClientAppInfo());
				req.setUsername(requestMessage.getUsername());
				req.setPassword(requestMessage.getPassword());
			});
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.accessToken(loginResponse.getAccessToken())
					.authSession(loginResponse.getAuthSession())
					.userProfile(loginResponse.getUserProfile())
					.build();
			
			return replySuccess(request, DEFAULT_SUCCESS_STATUS_CODE, "Login Success", "Session created", bodyResponseMessage);
			
		} catch (LoginException ex) {
			logger.error(ex.getMessage(), ex);
			
			if(ex instanceof LoginException.InvalidUsername) {
				return replyError(request, "400101", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof LoginException.InvalidPassword) {
				return replyError(request, "400102", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			return replyError(request, "400199", ex.getErrorTitle(), ex.getErrorMessage());
			
		} catch (UserException ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "400201", ex.getErrorTitle(), ex.getErrorMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "500999", "Request Processing Failed", ex.getMessage());
		}
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected ClientAppInfo clientAppInfo;
		protected String username;
		protected String password;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected String accessToken;
		protected AuthSession authSession;
		protected UserProfile userProfile;
	}
}