package com.otc.survey.modules.core.domain.service.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineReplyMessageRequest;
import com.otc.survey.modules.core.domain.service.LineConfigService;

import lombok.Data;

@ServiceCommand
public class LineReplyMessageCommand extends BaseServiceCommand
{
	@Autowired
	protected LineConfigService lineConfigService;
	
	
	public Response replyMessage(Request request)
	{
		logger.info("### {}.replyMessage ###", getClass().getSimpleName());
		
		String channelAccessToken = getChannelAccessToken(request);
		
		if(channelAccessToken == null || channelAccessToken.isBlank()) {
			throw new IllegalArgumentException("Param 'channelAccessToken' couldn't be null or empty");
		}
		
		if(request.getReplyMessage() == null) {
			throw new IllegalArgumentException("Param 'replyMessage' couldn't be null");
		}
		
		if(request.getReplyMessage().getReplyToken() == null || request.getReplyMessage().getReplyToken().isBlank()) {
			throw new IllegalArgumentException("Param 'replyMessage.replyToken' couldn't be null or empty");
		}
		
		if(request.getReplyMessage().getMessages() == null || request.getReplyMessage().getMessages().isEmpty()) {
			throw new IllegalArgumentException("Param 'replyMessage.messages' couldn't be null or empty");
		}
		
		RestClient.create()
				.post()
				.uri(lineConfigService.getApiEndpointReplyMessage())
				.header("Authorization", "Bearer " + channelAccessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.body(request.getReplyMessage())
				.retrieve()
				.toBodilessEntity();
		
		Response response = new Response();
		
		return response;
	}
	
	protected String getChannelAccessToken(Request request) 
	{
		String channelAccessToken = request.getChannelAccessToken();
		
		if(channelAccessToken == null || channelAccessToken.isBlank()) {
			channelAccessToken = lineConfigService.getMessagingChannelAccessToken();
		}
		
		return channelAccessToken;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String channelAccessToken; // optional
		protected LineReplyMessageRequest replyMessage; // required
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		
	}
}