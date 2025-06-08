package com.otc.survey.modules.core.endpoint.controller.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.core.domain.model.line.LineUserProfile;
import com.otc.survey.modules.core.domain.service.line.LineService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/line/load-user-profile")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM
	}
)
public class LoadLineUserProfileAPI extends AbstractJsonBodyAPI<LoadLineUserProfileAPI.RequestMessage, LoadLineUserProfileAPI.BodyResponseMessage>
{
	@Autowired
	protected LineService lineService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "RequestMessage couldn't be null");
		}
		
		if(requestMessage.getLineUserId() == null || requestMessage.getLineUserId().isBlank()) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'lineUserId' couldn't be null or empty");
		}
		
		LineUserProfile lineUserProfile = lineService.loadUserProfile(req -> {
			setupServiceRequest(request, req);
			req.setChannelAccessToken(requestMessage.getChannelAccessToken());
			req.setLineUserId(requestMessage.getLineUserId());
		}).getLineUserProfile();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.lineUserProfile(lineUserProfile)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected String channelAccessToken; // optional
		protected String lineUserId; // required
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected LineUserProfile lineUserProfile;
	}
}