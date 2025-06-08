package com.otc.survey.modules.core.domain.service.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.model.line.LineUserProfile;
import com.otc.survey.modules.core.domain.service.LineConfigService;

import lombok.Data;

@ServiceCommand
public class LoadLineUserProfileCommand extends BaseServiceCommand
{
	@Autowired
	protected LineConfigService lineConfigService;
	
	
	public Response loadUserProfile(Request request)
	{
		logger.info("### {}.loadUserProfile ###", getClass().getSimpleName());
		
		String channelAccessToken = getChannelAccessToken(request);
		
		if(channelAccessToken == null || channelAccessToken.isBlank()) {
			throw new IllegalArgumentException("Param 'channelAccessToken' couldn't be null or empty");
		}
		
		if(request.getLineUserId() == null || request.getLineUserId().isBlank()) {
			throw new IllegalArgumentException("Param 'lineUserId' couldn't be null or empty");
		}
		
		LineUserProfile lineUserProfile = RestClient.create()
			.get()
			.uri(lineConfigService.getApiEndpointLoadUserProfile().replace("${userId}", request.getLineUserId()))
			.header("Authorization", "Bearer " + channelAccessToken)
			.retrieve()
			.body(LineUserProfile.class);
		
		logger.info("lineUserProfile => {}", lineUserProfile);
		
		Response response = new Response();
		response.setLineUserProfile(lineUserProfile);
		
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
		protected String lineUserId; // required
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected LineUserProfile lineUserProfile;
	}
}