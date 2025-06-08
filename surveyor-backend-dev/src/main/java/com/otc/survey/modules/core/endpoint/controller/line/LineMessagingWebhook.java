package com.otc.survey.modules.core.endpoint.controller.line;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.model.line.messaging.event.LineMessagingEvent;
import com.otc.survey.modules.core.domain.service.line.LineService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/line/messaging/webhook")
public class LineMessagingWebhook extends AbstractJsonBodyAPI<LineMessagingWebhook.RequestMessage, LineMessagingWebhook.BodyResponseMessage>
{
	@Autowired
	protected LineService lineService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		lineService.processMessagingEvent(req -> {
			setupServiceRequest(request, req);
			req.setEvents(requestMessage.getEvents());
		});
		
		return replySuccess(request);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected List<? extends LineMessagingEvent> events;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		
	}
}