package com.otc.survey.modules.core.domain.service.line;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.LineAccount;
import com.otc.survey.modules.core.domain.model.line.LineUserProfile;
import com.otc.survey.modules.core.domain.repository.LineAccountRepository;

import lombok.Data;

@ServiceCommand
public class CreateLineAccountCommand extends BaseServiceCommand
{
	@Autowired
	protected LineService lineService;
	
	@Autowired
	protected LineAccountRepository lineAccountRepository;
	
	
	public Response createLineAccount(Request request)
	{
		logger.info("### {}.createLineAccount ###", getClass().getSimpleName());
		
		if(request.getLineUserProfile() == null) {
			if(request.getLineUserId() == null || request.getLineUserId().isBlank()) {
				throw new IllegalArgumentException("Param 'lineUserId' couldn't be null or empty");
			}
		}
		
		LineUserProfile lineUserProfile = getLineUserProfile(request);
		logger.info("lineUserProfile => {}", lineUserProfile);
		
		if(lineUserProfile == null) {
			throw new IllegalArgumentException("LineUserProfile Not Found");
		}
		
		LineAccount lineAccount = buildLineAccount(request, lineUserProfile);
		lineAccountRepository.save(lineAccount);
		logger.info("Create lineAccount => {}", lineAccount);
		
		Response response = new Response();
		response.setLineUserProfile(lineUserProfile);
		response.setLineAccount(lineAccount);
		
		return response;
	}
	
	protected LineUserProfile getLineUserProfile(Request request)
	{
		LineUserProfile lineUserProfile = request.getLineUserProfile();
		
		if(lineUserProfile == null) {
			lineUserProfile = lineService.loadUserProfile(req -> {
				req.copyFrom(request);
				req.setLineUserId(request.getLineUserId());
			}).getLineUserProfile();
		}
		
		return lineUserProfile;
	}
	
	protected LineAccount buildLineAccount(Request request, LineUserProfile lineUserProfile)
	{
		LineAccount lineAccount = new LineAccount();
		lineAccount.setId(UUID.randomUUID().toString());
		lineAccount.setUserId(null);
		lineAccount.setLineUserId(lineUserProfile.getUserId());
		lineAccount.setLineAccountName(lineUserProfile.getDisplayName());
		lineAccount.setLineEmail(lineUserProfile.getEmail());
		lineAccount.setStatus(StatusCode.Active);
		lineAccount.setCreatedBy("LineMessaging");
		lineAccount.setCreatedAt(request.getPerformedAt());
		
		return lineAccount;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String lineUserId;
		protected LineUserProfile lineUserProfile;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected LineUserProfile lineUserProfile;
		protected LineAccount lineAccount;
	}
}