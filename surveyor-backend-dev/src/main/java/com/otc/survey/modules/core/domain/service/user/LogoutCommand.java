package com.otc.survey.modules.core.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.repository.AuthSessionRepository;

import lombok.Data;

@ServiceCommand
public class LogoutCommand extends BaseServiceCommand
{
	@Autowired
	protected AuthSessionRepository authSessionRepository;
	
	
	public Response logout(Request request)
	{
		logger.info("### {}.logout ###", getClass().getSimpleName());
		
		if(request.getAuthSessionId() == null || request.getAuthSessionId().isBlank()) {
			throw new IllegalArgumentException("Param 'authSessionId' couldn't be null or empty");
		}
		
		AuthSession authSession = authSessionRepository.findById(request.getAuthSessionId()).orElse(null);
		
		if(authSession != null) {
			authSessionRepository.delete(authSession);
			logger.info("Delete authSession => {}", authSession);
		}
		
		Response response = new Response();
		response.setAuthSession(authSession);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String authSessionId;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected AuthSession authSession;
	}
}