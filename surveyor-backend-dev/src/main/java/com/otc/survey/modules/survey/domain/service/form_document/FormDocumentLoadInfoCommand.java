package com.otc.survey.modules.survey.domain.service.form_document;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.survey.domain.entity.FormDocument;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.model.FormDocumentInfo;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormDocumentElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormDocumentRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;

import lombok.Data;

@ServiceCommand
public class FormDocumentLoadInfoCommand extends BaseServiceCommand
{
	@Autowired
	protected FormDocumentRepository formDocumentRepository;
	
	@Autowired
	protected FormDocumentElementRepository formDocumentElementRepository;
	
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateElementRepository formTemplateElementRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	
	public Response loadInfo(Request request)
	{
		logger.info("### {}.loadInfo ###", getClass().getSimpleName());
		
		if(request.getFormDocumentId() == null || request.getFormDocumentId().isBlank()) {
			throw new IllegalArgumentException("Param 'formDocumentId' couldn't be null or empty");
		}
		
		FormDocumentInfo formDocumentInfo = new FormDocumentInfo();
		
		FormDocument formDocument = formDocumentRepository.findById(request.getFormDocumentId()).orElse(null);
		
		if(formDocument != null) {
			formDocumentInfo.setFormDocument(formDocument);
			formDocumentInfo.setFormDocumentElements(formDocumentElementRepository.findByDocumentId(formDocument.getId()));
			
			FormTemplate formTemplate = formTemplateRepository.findById(formDocument.getTemplateId()).orElse(null);
			
			if(formTemplate != null) {
				FormTemplateInfo formTemplateInfo = FormTemplateInfo
						.builder()
						.formTemplate(formTemplate)
						.formTemplateElements(formTemplateElementRepository.findByTemplateId(formTemplate.getId()))
						.formTemplateAuthorities(formTemplateAuthorityRepository.findByTemplateId(formTemplate.getId()))
						.build();
				
				formDocumentInfo.setFormTemplateInfo(formTemplateInfo);
			}
		}
		
		Response response = new Response();
		response.setFormDocumentInfo(formDocumentInfo);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String formDocumentId;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected FormDocumentInfo formDocumentInfo;
	}
}