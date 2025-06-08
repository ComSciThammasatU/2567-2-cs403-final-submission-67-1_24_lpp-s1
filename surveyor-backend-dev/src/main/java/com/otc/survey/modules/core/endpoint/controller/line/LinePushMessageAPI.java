package com.otc.survey.modules.core.endpoint.controller.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LinePushMessageRequest;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LinePushMessageResponse;
import com.otc.survey.modules.core.domain.service.line.LineService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/line/push-message")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM
	}
)
public class LinePushMessageAPI extends AbstractJsonBodyAPI<LinePushMessageAPI.RequestMessage, LinePushMessageAPI.BodyResponseMessage>
{
	@Autowired
	protected LineService lineService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "RequestMessage couldn't be null");
		}
		
		if(requestMessage.getPushMessage() == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'pushMessage' couldn't be null");
		}
		
		if(requestMessage.getPushMessage().getTo() == null || requestMessage.getPushMessage().getTo().isBlank()) {
			return replyError(request, "400003", "Invalid Request Message", "Param 'pushMessage.to' couldn't be null or empty");
		}
		
		if(requestMessage.getPushMessage().getMessages() == null || requestMessage.getPushMessage().getMessages().isEmpty()) {
			return replyError(request, "400003", "Invalid Request Message", "Param 'pushMessage.messages' couldn't be null or empty");
		}
		
		LinePushMessageResponse pushMessageResponse = lineService.pushMessage(req -> {
			setupServiceRequest(request, req);
			req.setChannelAccessToken(requestMessage.getChannelAccessToken());
			req.setPushMessage(requestMessage.getPushMessage());
		}).getPushMessageResponse();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.pushMessageResponse(pushMessageResponse)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected String channelAccessToken; // optional
		protected LinePushMessageRequest pushMessage; // required
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected LinePushMessageResponse pushMessageResponse;
	}
}