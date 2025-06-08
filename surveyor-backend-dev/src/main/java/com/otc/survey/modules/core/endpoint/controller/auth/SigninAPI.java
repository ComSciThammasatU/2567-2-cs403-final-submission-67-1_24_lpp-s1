package com.otc.survey.modules.core.endpoint.controller.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.model.UserProfile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/auth/signin")
public class SigninAPI extends AbstractJsonBodyAPI<SigninAPI.RequestMessage, SigninAPI.BodyResponseMessage>
{
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
					"Param 'password' couldn't be null or empty",
					Map.of("foo", "My-Foo")
				);
		}
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.accessToken("test-123")
				.userProfile(new UserProfile())
				.build();
		
		return replySuccess(request, DEFAULT_SUCCESS_STATUS_CODE, "Signin Success", "Session created", bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected String username;
		protected String password;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected String accessToken;
		protected UserProfile userProfile;
	}
}