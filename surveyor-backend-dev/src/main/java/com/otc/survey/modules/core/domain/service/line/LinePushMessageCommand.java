package com.otc.survey.modules.core.domain.service.line;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.common.domain.util.JsonUtil;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LinePushMessageRequest;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LinePushMessageResponse;
import com.otc.survey.modules.core.domain.service.LineConfigService;
import com.otc.survey.modules.core.domain.service.line.exception.LinePushMessageException;

import lombok.Data;

@ServiceCommand
public class LinePushMessageCommand extends BaseServiceCommand
{
	@Autowired
	protected LineConfigService lineConfigService;
	
	
	public Response pushMessage(Request request)
	{
		logger.info("### {}.pushMessage ###", getClass().getSimpleName());
		
		String channelAccessToken = getChannelAccessToken(request);
		
		if(channelAccessToken == null || channelAccessToken.isBlank()) {
			throw new IllegalArgumentException("Param 'channelAccessToken' couldn't be null or empty");
		}
		
		if(request.getPushMessage() == null) {
			throw new IllegalArgumentException("Param 'replyMessage' couldn't be null");
		}
		
		if(request.getPushMessage().getTo() == null || request.getPushMessage().getTo().isBlank()) {
			throw new IllegalArgumentException("Param 'pushMessage.to' couldn't be null or empty");
		}
		
		if(request.getPushMessage().getMessages() == null || request.getPushMessage().getMessages().isEmpty()) {
			throw new IllegalArgumentException("Param 'pushMessage.messages' couldn't be null or empty");
		}
		
		LinePushMessageResponse pushMessageResponse = null;
		try {
			pushMessageResponse = RestClient.create()
					.post()
					.uri(lineConfigService.getApiEndpointPushMessage())
					.header("Authorization", "Bearer " + channelAccessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.body(request.getPushMessage())
					.retrieve()
					.onStatus(buildResponseErrorHandler())
					.body(LinePushMessageResponse.Success.class);
		} catch(Exception ex) {
			if(ex instanceof LinePushMessageException pushMessageError) {
				pushMessageResponse = pushMessageError.getErrorResponse();
			} else {
				throw ex;
			}
		}
		
		Response response = new Response();
		response.setPushMessageResponse(pushMessageResponse);
		
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
	
	protected ResponseErrorHandler buildResponseErrorHandler()
	{
		return new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return response.getStatusCode() != HttpStatus.OK;
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				logger.error("!!! handleError !!!");
				logger.error("statusCode => {}", response.getStatusCode());
				logger.error("statusText => {}", response.getStatusText());
				
				Map<?, ?> errorObject = JsonUtil.parse(new String(response.getBody().readAllBytes()), Map.class);
				String errorMessage = errorObject != null ? (String) errorObject.get("message") : null;
				
				logger.error("errorMessage => {}", errorMessage);
				
				LinePushMessageResponse.Error errorResponse = LinePushMessageResponse.Error
						.builder()
						.statusCode(response.getStatusCode().value())
						.statusMessage(response.getStatusText())
						.errorMessage(errorMessage)
						.build();
				
				throw new LinePushMessageException(errorResponse);
			}
		};
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String channelAccessToken; // optional
		protected LinePushMessageRequest pushMessage; // required
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected LinePushMessageResponse pushMessageResponse;
	}
}