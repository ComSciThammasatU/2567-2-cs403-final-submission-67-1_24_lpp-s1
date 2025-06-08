package com.otc.survey.modules.survey.domain.service.form_template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;
import com.otc.survey.modules.survey.domain.entity.FormTemplateElement;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;
import com.otc.survey.modules.survey.domain.service.form_template.dto.FormTemplateSearchDTO;

import lombok.Data;

@ServiceCommand
public class FormTemplateSearchCommand extends BaseServiceCommand
{
	@Autowired
	protected NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateElementRepository formTemplateElementRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	
	public Response searchFormTemplate(Request request)
	{
		logger.info("### {}.searchFormTemplate ###", getClass().getSimpleName());
		
		if(request.getRequestPayload() == null) {
			throw new IllegalArgumentException("Param 'requestPayload' couldn't be null");
		}
		
		List<FormTemplate> formTemplates = formTemplateRepository.search(jdbcTemplate, request.getRequestPayload().getCriteria());
		List<FormTemplateElement> formTemplateElements = loadFormTemplateElements(formTemplates);
		List<FormTemplateAuthority> formTemplateAuthorities = loadFormTemplateAuthorities(formTemplates);
		List<FormTemplateInfo> formTemplateInfos = buildFormTemplateInfos(formTemplates, formTemplateElements, formTemplateAuthorities);
		
		FormTemplateSearchDTO.ResponsePayload responsePayload =  new FormTemplateSearchDTO.ResponsePayload();
		responsePayload.setFormTemplateInfos(formTemplateInfos);
		
		Response response = new Response();
		response.setResponsePayload(responsePayload);
		
		return response;
	}
	
	protected List<FormTemplateElement> loadFormTemplateElements(List<FormTemplate> formTemplates)
	{
		if(formTemplates == null || formTemplates.isEmpty()) {
			return null;
		}
		
		List<String> templateIds = formTemplates.stream()
				.map(e -> e.getId())
				.distinct()
				.toList();
		
		if(templateIds == null || templateIds.isEmpty()) {
			return null;
		}
		
		return formTemplateElementRepository.findByTemplateIdIn(templateIds);
	}
	
	protected List<FormTemplateAuthority> loadFormTemplateAuthorities(List<FormTemplate> formTemplates)
	{
		if(formTemplates == null || formTemplates.isEmpty()) {
			return null;
		}
		
		List<String> templateIds = formTemplates.stream()
				.map(e -> e.getId())
				.distinct()
				.toList();
		
		if(templateIds == null || templateIds.isEmpty()) {
			return null;
		}
		
		return formTemplateAuthorityRepository.findByTemplateIdIn(templateIds);
	}
	
	protected List<FormTemplateInfo> buildFormTemplateInfos(List<FormTemplate> formTemplates, List<FormTemplateElement> formTemplateElements, List<FormTemplateAuthority> formTemplateAuthorities)
	{
		if(formTemplates == null || formTemplates.isEmpty()) {
			return null;
		}
		
		return formTemplates.stream()
				.map(formTemplate -> {
					List<FormTemplateElement> elements = null;
					List<FormTemplateAuthority> authorities = null;
					
					if(formTemplateElements != null && !formTemplateElements.isEmpty()) {
						elements = formTemplateElements
								.stream()
								.filter(e -> e.getTemplateId().equals(formTemplate.getId()))
								.toList();
					}
					
					if(formTemplateAuthorities != null && !formTemplateAuthorities.isEmpty()) {
						authorities = formTemplateAuthorities
								.stream()
								.filter(e -> e.getTemplateId().equals(formTemplate.getId()))
								.toList();
					}
					
					FormTemplateInfo formTemplateInfo = new FormTemplateInfo();
					formTemplateInfo.setFormTemplate(formTemplate);
					formTemplateInfo.setFormTemplateElements(elements);
					formTemplateInfo.setFormTemplateAuthorities(authorities);
					
					return formTemplateInfo;
				})
				.toList();
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected FormTemplateSearchDTO.RequestPayload requestPayload;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected FormTemplateSearchDTO.ResponsePayload responsePayload;
	}
}