package com.otc.survey.modules.core.domain.service.user;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.User;

import lombok.Data;

@ServiceCommand
public class GeneratePasswordCommand extends BaseServiceCommand
{
	public Response generatePassword()
	{
		logger.info("### {}.generatePassword ###", getClass().getSimpleName());
		
		Response response = new Response();
		response.setGeneratedPassword("1234");
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected User user;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected String generatedPassword;
	}
}