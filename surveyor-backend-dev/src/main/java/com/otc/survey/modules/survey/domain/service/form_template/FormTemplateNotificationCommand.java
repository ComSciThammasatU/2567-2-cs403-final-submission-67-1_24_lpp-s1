package com.otc.survey.modules.survey.domain.service.form_template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.LineAccount;
import com.otc.survey.modules.core.domain.entity.UserGroupMapper;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LinePushMessageRequest;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineTextMessage;
import com.otc.survey.modules.core.domain.repository.LineAccountRepository;
import com.otc.survey.modules.core.domain.repository.UserGroupMapperRepository;
import com.otc.survey.modules.core.domain.service.line.LineService;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;

import lombok.Data;

@ServiceCommand
public class FormTemplateNotificationCommand extends BaseServiceCommand
{
	@Autowired
	protected LineService lineService;
	
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	@Autowired
	protected UserGroupMapperRepository userGroupMapperRepository;
	
	@Autowired
	protected LineAccountRepository lineAccountRepository;
	
	
	public Response sendNotification(Request request)
	{
		logger.info("### {}.sendNotification ###", getClass().getSimpleName());
		
		if(request.getTemplateId() == null || request.getTemplateId().isBlank()) {
			throw new IllegalArgumentException("Param 'templateId' couldn't be null or empty");
		}
		
		Response response = new Response();
		
		FormTemplate formTemplate = formTemplateRepository.findById(request.getTemplateId()).orElse(null);
		
		if(formTemplate == null) {
			return response;
		}
		
		List<FormTemplateAuthority> formTemplateAuthorities = formTemplateAuthorityRepository.findByTemplateId(request.getTemplateId());
		
		if(formTemplateAuthorities == null || formTemplateAuthorities.isEmpty()) {
			return response;
		}
		
		List<String> userGroupIds = formTemplateAuthorities
				.stream()
				.filter(e -> e != null && e.getPrincipalRefId() != null && !e.getPrincipalRefId().isBlank())
				.map(e -> e.getPrincipalRefId().trim())
				.distinct()
				.toList();
		
		if(userGroupIds == null || userGroupIds.isEmpty()) {
			return response;
		}
		
		List<UserGroupMapper> userGroupMappers = userGroupMapperRepository.findAllActiveByGroupIds(userGroupIds);
		
		if(userGroupMappers == null || userGroupMappers.isEmpty()) {
			return response;
		}
		
		List<String> userIds = userGroupMappers
				.stream()
				.filter(e -> e != null && e.getUserId() != null && !e.getUserId().isBlank())
				.map(e -> e.getUserId())
				.distinct()
				.toList();
		
		if(userIds == null || userIds.isEmpty()) {
			return response;
		}
		
		List<LineAccount> lineAccounts = lineAccountRepository.findByUserIdIn(userIds);
		
		if(lineAccounts == null || lineAccounts.isEmpty()) {
			return response;
		}
		
		for(LineAccount lineAccount : lineAccounts) {
			lineService.pushMessage(req -> {
				LinePushMessageRequest pushMessageRequest = LinePushMessageRequest
						.builder()
						.to(lineAccount.getLineUserId())
						.messages(List.of(
								LineTextMessage.builder().text("กรุณาทำแบบสอบถาม " + formTemplate.getName()).build()
							))
						.build();
				
				req.copyFrom(request);
				req.setPushMessage(pushMessageRequest);
			});
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String templateId;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		
	}
}