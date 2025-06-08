package com.otc.survey.modules.survey.domain.service.form_template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
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
import com.otc.survey.modules.survey.domain.service.form_template.dto.FormTemplateUpdateDTO;
import com.otc.survey.modules.survey.domain.service.form_template.exception.FormTemplateUpdateException;

import lombok.Data;

@ServiceCommand
public class FormTemplateUpdateCommand extends BaseServiceCommand
{
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateElementRepository formTemplateElementRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	@Autowired
	protected FormTemplateService formTemplateService;
	
	
	public Response updateFormTemplate(Request request) throws FormTemplateUpdateException
	{
		logger.info("### {}.updateFormTemplate ###", getClass().getSimpleName());
		
		validate(request);
		
		FormTemplateUpdateDTO.RequestPayload requestPayload = request.getRequestPayload();
		
		FormTemplate parentTemplate = formTemplateRepository.findById(requestPayload.getParentTemplateId()).orElse(null);
		
		if(parentTemplate == null) {
			throw new FormTemplateUpdateException.ParentTemplateNotFound(requestPayload.getParentTemplateId());
		}
		
		parentTemplate.setStatus(StatusCode.Closed);
		parentTemplate.setLastModifiedBy(request.getPerformedBy());
		parentTemplate.setLastModifiedAt(request.getPerformedAt());
		parentTemplate = formTemplateRepository.save(parentTemplate);
		logger.info("Update parentTemplate => {}", parentTemplate);
		
		FormTemplate formTemplate = requestPayload.toFormTemplate(parentTemplate);
		formTemplate.setLastModifiedBy(request.getPerformedBy());
		formTemplate.setLastModifiedAt(request.getPerformedAt());
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
		
		FormTemplateUpdateDTO.ResponsePayload responsePayload = new FormTemplateUpdateDTO.ResponsePayload();
		responsePayload.setParentTemplate(parentTemplate);
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
	
	protected boolean validate(Request request) throws FormTemplateUpdateException
	{
		FormTemplateUpdateDTO.RequestPayload requestPayload = request.getRequestPayload();
		
		if(requestPayload == null) {
			throw new IllegalArgumentException("Param 'requestPayload' cannot be null");
		}
		
		return true;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected FormTemplateUpdateDTO.RequestPayload requestPayload;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected FormTemplateUpdateDTO.ResponsePayload responsePayload;
	}
}