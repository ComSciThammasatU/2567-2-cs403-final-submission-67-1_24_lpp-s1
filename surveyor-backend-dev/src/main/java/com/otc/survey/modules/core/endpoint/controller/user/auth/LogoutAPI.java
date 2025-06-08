package com.otc.survey.modules.core.endpoint.controller.user.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/user/auth/logout")
@Auth(authen = true)
public class LogoutAPI extends AbstractJsonBodyAPI<LogoutAPI.RequestMessage, LogoutAPI.BodyResponseMessage>
{
	@Autowired
	protected UserService userService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		AuthSession authSession = userService.logout(req -> {
			setupServiceRequest(request, req);
			req.setAuthSessionId(getAuthSession(request).getId());
		}).getAuthSession();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.authSession(authSession)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected AuthSession authSession;
	}
}