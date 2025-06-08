package com.otc.survey.modules.survey.domain.service.form_template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;
import com.otc.survey.modules.survey.domain.entity.FormTemplateElement;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;

import lombok.Data;

@ServiceCommand
public class FormTemplateLoadInfoCommand extends BaseServiceCommand
{
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateElementRepository formTemplateElementRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	
	public Response loadTemplateInfo(Request request)
	{
		logger.info("### {}.loadTemplate ###", getClass().getSimpleName());
		
		if(request.getFormTemplateId() == null || request.getFormTemplateId().isBlank()) {
			throw new IllegalArgumentException("Param 'formTemplateId' couldn't be null or empty");
		}
		
		FormTemplate formTemplate = formTemplateRepository.findById(request.getFormTemplateId()).orElse(null);
		List<FormTemplateElement> formTemplateElements = null;
		List<FormTemplateAuthority> formTemplateAuthorities = null;

		if(formTemplate != null) {
			formTemplateElements = formTemplateElementRepository.findByTemplateId(request.getFormTemplateId());
			formTemplateAuthorities = formTemplateAuthorityRepository.findByTemplateId(request.getFormTemplateId());
		}
		
		FormTemplateInfo formTemplateInfo = new FormTemplateInfo();
		formTemplateInfo.setFormTemplate(formTemplate);
		formTemplateInfo.setFormTemplateElements(formTemplateElements);
		formTemplateInfo.setFormTemplateAuthorities(formTemplateAuthorities);
		
		Response response = new Response();
		response.setFormTemplateInfo(formTemplateInfo);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String formTemplateId;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected FormTemplateInfo formTemplateInfo;
	}
}