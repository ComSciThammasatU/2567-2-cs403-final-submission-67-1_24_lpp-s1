package com.otc.survey.modules.survey.domain.service.form_document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.survey.domain.constant.FormDocumentStatus;
import com.otc.survey.modules.survey.domain.entity.FormDocument;
import com.otc.survey.modules.survey.domain.entity.FormDocumentElement;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.model.FormDocumentInfo;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormDocumentElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormDocumentRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;
import com.otc.survey.modules.survey.domain.service.form_document.dto.FormDocumentSubmitDTO;
import com.otc.survey.modules.survey.domain.service.form_document.exception.FormDocumentSubmitException;

import lombok.Data;

@ServiceCommand
public class FormDocumentSubmitCommand extends BaseServiceCommand
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
	
	
	public Response submit(Request request) throws FormDocumentSubmitException
	{
		logger.info("### {}.submit ###", getClass().getSimpleName());
		
		FormDocumentSubmitDTO.RequestPayload requestPayload = request.getRequestPayload();
		
		if(requestPayload == null) {
			throw new IllegalArgumentException("Param 'requestPayload' couldn't be null");
		}
		
		if(requestPayload.getTemplateId() == null || requestPayload.getTemplateId().isBlank()) {
			throw new IllegalArgumentException("Param 'requestPayload.templateId' couldn't be null or empty");
		}
		
		if(requestPayload.getUserId() == null || requestPayload.getUserId().isBlank()) {
			throw new IllegalArgumentException("Param 'requestPayload.userId' couldn't be null or empty");
		}
		
		if(requestPayload.getElements() == null || requestPayload.getElements().isEmpty()) {
			throw new IllegalArgumentException("Param 'requestPayload.elements' couldn't be null or empty");
		}
		
		FormTemplate formTemplate = formTemplateRepository.findById(requestPayload.getTemplateId()).orElse(null);
		
		if(formTemplate == null) {
			throw new FormDocumentSubmitException.TemplateNotFound(requestPayload.getTemplateId());
		}
		
		FormDocument existingDocument = formDocumentRepository.findByTemplateIdAndSubmittedUserId(requestPayload.getTemplateId(), requestPayload.getUserId());
		
		if(existingDocument != null && existingDocument.getDocumentStatus() == FormDocumentStatus.Completed) {
			if(existingDocument.getDocumentStatus() == FormDocumentStatus.Completed) {
				throw new FormDocumentSubmitException.DuplicateDocument(requestPayload.getTemplateId(), requestPayload.getUserId());
			} else {
				formDocumentRepository.delete(existingDocument);
				formDocumentRepository.flush();
				formDocumentRepository.detach(existingDocument);
			}
		}
		
		FormDocument formDocument = new FormDocument();
		formDocument.setId(UUID.randomUUID().toString());
		formDocument.setTemplateId(requestPayload.getTemplateId());
		formDocument.setDocumentNo(formTemplate.getCode() + "-" + formTemplate.getRevision() + "-" + new SimpleDateFormat("yyMMddHHmmssSSS", Locale.ENGLISH).format(request.getPerformedAt()));
		formDocument.setDocumentStatus(FormDocumentStatus.Completed);
		formDocument.setSubmittedUserId(requestPayload.getUserId());
		formDocument.setSubmittedAt(request.getPerformedAt());
		formDocument.setStatus(StatusCode.Active);
		formDocument.setCreatedBy(request.getPerformedBy());
		formDocument.setCreatedAt(request.getPerformedAt());
		
		List<FormDocumentElement> formDocumentElements = new ArrayList<>();
		for(FormDocumentSubmitDTO.RequestPayload.Element element : requestPayload.getElements()) {
			FormDocumentElement formDocumentElement = new FormDocumentElement();
			formDocumentElement.setId(UUID.randomUUID().toString());
			formDocumentElement.setDocumentId(formDocument.getId());
			formDocumentElement.setTemplateId(formDocument.getTemplateId());
			formDocumentElement.setTemplateElementId(element.getTemplateElementId());
			formDocumentElement.setElementTypeId(element.getElementTypeId());
			formDocumentElement.setElementValue(element.getElementValue());
			formDocumentElement.setOrderNo(element.getOrderNo());
			formDocumentElement.setStatus(StatusCode.Active);
			formDocumentElement.setCreatedBy(request.getPerformedBy());
			formDocumentElement.setCreatedAt(request.getPerformedAt());
			
			formDocumentElements.add(formDocumentElement);
		}
		
		formDocumentRepository.save(formDocument);
		formDocumentElementRepository.saveAll(formDocumentElements);
		
		FormDocumentInfo formDocumentInfo = new FormDocumentInfo();
		formDocumentInfo.setFormDocument(formDocument);
		formDocumentInfo.setFormDocumentElements(formDocumentElements);
		formDocumentInfo.setFormTemplateInfo(
				FormTemplateInfo
				.builder()
				.formTemplate(formTemplate)
				.formTemplateElements(formTemplateElementRepository.findByTemplateId(formDocument.getTemplateId()))
				.formTemplateAuthorities(formTemplateAuthorityRepository.findByTemplateId(formDocument.getTemplateId()))
				.build()
			);
		
		FormDocumentSubmitDTO.ResponsePayload responsePayload = new FormDocumentSubmitDTO.ResponsePayload();
		responsePayload.setFormDocumentInfo(formDocumentInfo);
		
		Response response = new Response();
		response.setResponsePayload(responsePayload);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected FormDocumentSubmitDTO.RequestPayload requestPayload;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected FormDocumentSubmitDTO.ResponsePayload responsePayload;
	}
}