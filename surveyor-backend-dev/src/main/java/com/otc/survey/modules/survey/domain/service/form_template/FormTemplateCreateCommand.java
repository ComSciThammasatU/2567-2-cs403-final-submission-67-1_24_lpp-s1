package com.otc.survey.modules.survey.domain.service.form_template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.survey.domain.constant.FormTemplateStatus;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;
import com.otc.survey.modules.survey.domain.service.form_template.dto.FormTemplateCreateDTO;
import com.otc.survey.modules.survey.domain.service.form_template.exception.FormTemplateCreateException;

import lombok.Data;

@ServiceCommand
public class FormTemplateCreateCommand extends BaseServiceCommand
{
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateElementRepository formTemplateElementRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	@Autowired
	protected FormTemplateService formTemplateService;
	
	
	public Response createFormTemplate(Request request) throws FormTemplateCreateException
	{
		logger.info("### {}.createFormTemplate ###", getClass().getSimpleName());
		
		FormTemplateCreateDTO.RequestPayload requestPayload = request.getRequestPayload();
		
		FormTemplate formTemplate = requestPayload.toFormTemplate();
		formTemplate.setCreatedBy(request.getPerformedBy());
		formTemplate.setCreatedAt(request.getPerformedAt());
		formTemplate = formTemplateRepository.save(formTemplate);
		logger.info("Create formTemplate => {}", formTemplate);
		
		List<FormTemplateAuthority> formTemplateAuthorities = requestPayload.toFormTemplateAuthorities(formTemplate);
		if(formTemplateAuthorities != null && !formTemplateAuthorities.isEmpty()) {
			formTemplateAuthorities.forEach(formTemplateAuthority -> {
				formTemplateAuthority.setCreatedBy(request.getPerformedBy());
				formTemplateAuthority.setCreatedAt(request.getPerformedAt());
			});
			
			formTemplateAuthorities = formTemplateAuthorityRepository.saveAll(formTemplateAuthorities);
			logger.info("Create formTemplateAuthorities => {}", formTemplateAuthorities);
		}
		
		FormTemplateInfo formTemplateInfo = new FormTemplateInfo();
		formTemplateInfo.setFormTemplate(formTemplate);
		formTemplateInfo.setFormTemplateAuthorities(formTemplateAuthorities);
		
		FormTemplateCreateDTO.ResponsePayload responsePayload = new FormTemplateCreateDTO.ResponsePayload();
		responsePayload.setFormTemplateInfo(formTemplateInfo);
		
		if(formTemplate.getTemplateStatus() == FormTemplateStatus.Open) {
			String templateId = formTemplate.getId();
			formTemplateService.sendNotification(req -> {
				req.copyFrom(request);
				req.setTemplateId(templateId);
			});
		}
		
		Response response = new Response();
		response.setResponsePayload(responsePayload);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected FormTemplateCreateDTO.RequestPayload requestPayload;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected FormTemplateCreateDTO.ResponsePayload responsePayload;
	}
}