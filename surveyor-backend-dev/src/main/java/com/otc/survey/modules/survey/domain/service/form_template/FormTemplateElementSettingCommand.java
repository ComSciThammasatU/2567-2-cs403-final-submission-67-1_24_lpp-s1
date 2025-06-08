package com.otc.survey.modules.survey.domain.service.form_template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.common.domain.util.JsonUtil;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateElement;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;
import com.otc.survey.modules.survey.domain.service.form_template.dto.FormTemplateElementSettingDTO;
import com.otc.survey.modules.survey.domain.service.form_template.exception.FormTemplateElementSettingException;

import lombok.Data;

@ServiceCommand
public class FormTemplateElementSettingCommand extends BaseServiceCommand
{
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateElementRepository formTemplateElementRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	
	public Response setElement(Request request) throws FormTemplateElementSettingException
	{
		logger.info("### {}.setElement ###", getClass().getSimpleName());
		
		FormTemplateElementSettingDTO.RequestPayload requestPayload = request.getRequestPayload();
		
		if(requestPayload == null) {
			throw new IllegalArgumentException("Param 'requestPayload' couldn't be null");
		}
		
		if(requestPayload.getFormTemplateId() == null || requestPayload.getFormTemplateId().isBlank()) {
			throw new IllegalArgumentException("Param 'requestPayload.formTemplateId' couldn't be null or empty");
		}
		
		if(requestPayload.getElementDatas() == null || requestPayload.getElementDatas().isEmpty()) {
			throw new IllegalArgumentException("Param 'requestPayload.elementDatas' couldn't be null or empty");
		}
		
		FormTemplate formTemplate = formTemplateRepository.findById(requestPayload.getFormTemplateId()).orElse(null);
		
		if(formTemplate == null) {
			throw new FormTemplateElementSettingException.TemplateNotFound(requestPayload.getFormTemplateId());
		}
		
		formTemplateElementRepository.removeByTemplateId(requestPayload.getFormTemplateId());
		formTemplateElementRepository.flush();
		
		List<FormTemplateElement> formTemplateElements = new ArrayList<>();
		int order = 0;
		for(Map<String, Object> elementData : requestPayload.getElementDatas()) {
			order++;
			
			if(elementData == null || elementData.isEmpty()) {
				throw new FormTemplateElementSettingException.InvalidElementData(requestPayload.getFormTemplateId(), null);
			}
			
			FormTemplateElement formTemplateElement = new FormTemplateElement();
			formTemplateElement.setId(UUID.randomUUID().toString());
			formTemplateElement.setTemplateId(requestPayload.getFormTemplateId());
			formTemplateElement.setElementCode((String) elementData.get("name"));
			formTemplateElement.setElementTypeId((String) elementData.get("type"));
			formTemplateElement.setOrderNo(order);
			formTemplateElement.setTitle("");
			formTemplateElement.setDescription("");
			try {
				formTemplateElement.setProperty(JsonUtil.stringify(elementData));
			} catch(Exception ex) {
				throw new FormTemplateElementSettingException.InvalidElementData(requestPayload.getFormTemplateId(), formTemplateElement.getElementCode());
			}
			formTemplateElement.setStatus(StatusCode.Active);
			formTemplateElement.setCreatedBy(request.getPerformedBy());
			formTemplateElement.setCreatedAt(request.getPerformedAt());
			
			formTemplateElements.add(formTemplateElement);
		}
		
		formTemplateElementRepository.saveAll(formTemplateElements);
		
		FormTemplateInfo formTemplateInfo = new FormTemplateInfo();
		formTemplateInfo.setFormTemplate(formTemplate);
		formTemplateInfo.setFormTemplateElements(formTemplateElements);
		formTemplateInfo.setFormTemplateAuthorities(formTemplateAuthorityRepository.findAllActiveByTemplateId(requestPayload.getFormTemplateId()));
		
		FormTemplateElementSettingDTO.ResponsePayload responsePayload = new FormTemplateElementSettingDTO.ResponsePayload();
		responsePayload.setFormTemplateInfo(formTemplateInfo);
		
		Response response = new Response();
		response.setResponsePayload(responsePayload);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected FormTemplateElementSettingDTO.RequestPayload requestPayload;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected FormTemplateElementSettingDTO.ResponsePayload responsePayload;
	}
}