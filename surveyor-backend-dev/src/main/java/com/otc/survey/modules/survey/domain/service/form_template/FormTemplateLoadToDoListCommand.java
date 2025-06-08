package com.otc.survey.modules.survey.domain.service.form_template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.UserGroupMapper;
import com.otc.survey.modules.core.domain.repository.UserGroupMapperRepository;
import com.otc.survey.modules.survey.domain.constant.FormTemplateStatus;
import com.otc.survey.modules.survey.domain.entity.FormDocument;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;
import com.otc.survey.modules.survey.domain.entity.FormTemplateElement;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormDocumentRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;

import lombok.Data;

@ServiceCommand
public class FormTemplateLoadToDoListCommand extends BaseServiceCommand
{
	@Autowired
	protected FormDocumentRepository formDocumentRepository;
	
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateElementRepository formTemplateElementRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	@Autowired
	protected UserGroupMapperRepository userGroupMapperRepository;
	
	
	public Response loadToDoList(Request request)
	{
		logger.info("### {}.loadToDoList ###", getClass().getSimpleName());
		
		if(request.getUserId() == null || request.getUserId().isBlank()) {
			throw new IllegalArgumentException("Param 'userId' couldn't be null or empty");
		}
		
		List<FormTemplateAuthority> formTemplateAuthorities = loadFormTemplateAuthorities(request.getUserId().trim());
		List<FormTemplate> formTemplates = loadFormTemplates(request.getUserId().trim(), formTemplateAuthorities);
		List<FormTemplateElement> formTemplateElements = loadFormTemplateElements(formTemplates);
		
		List<FormTemplateInfo> formTemplateInfos = buildFormTemplateInfos(formTemplates, formTemplateElements, formTemplateAuthorities);
		
		Response response = new Response();
		response.setFormTemplateInfos(formTemplateInfos);
		
		return response;
	}
	
	protected List<FormTemplateAuthority> loadFormTemplateAuthorities(String userId)
	{
		List<UserGroupMapper> userGroupMappers = userGroupMapperRepository.findAllActiveByUserId(userId);
		
		if(userGroupMappers != null && !userGroupMappers.isEmpty()) {
			List<String> groupIds = userGroupMappers
					.stream()
					.filter(e -> e != null && e.getGroupId() != null && !e.getGroupId().isBlank())
					.map(e -> e.getGroupId().trim())
					.distinct()
					.toList();
			
			if(groupIds != null && !groupIds.isEmpty()) {
				return formTemplateAuthorityRepository.findAllActiveByGroupIds(groupIds);
			}
		}
		
		return null;
	}
	
	protected List<FormTemplate> loadFormTemplates(String userId, List<FormTemplateAuthority> formTemplateAuthorities)
	{
		if(formTemplateAuthorities == null || formTemplateAuthorities.isEmpty()) {
			return null;
		}
		
		List<String> templateIds = formTemplateAuthorities
				.stream()
				.filter(e -> e != null && e.getTemplateId() != null && !e.getTemplateId().isBlank())
				.map(e -> e.getTemplateId().trim())
				.distinct()
				.toList();
		
		if(templateIds == null || templateIds.isEmpty()) {
			return null;
		}
		
		List<FormDocument> formDocuments = formDocumentRepository.findBySubmittedUserId(userId);
		if(formDocuments != null && !formDocuments.isEmpty()) {
			List<String> documentTemplateIds = formDocuments
					.stream()
					.filter(e -> e != null && e.getTemplateId() != null && !e.getTemplateId().isBlank())
					.map(e -> e.getTemplateId().trim())
					.distinct()
					.toList();
			
			if(documentTemplateIds != null && !documentTemplateIds.isEmpty()) {
				templateIds = templateIds
						.stream()
						.filter(templateId -> !documentTemplateIds.contains(templateId))
						.distinct()
						.toList();
			}
		}
		
		if(templateIds == null || templateIds.isEmpty()) {
			return null;
		}
		
		return formTemplateRepository.findAllActiveOpenTemplateByIds(templateIds);
	}
	
	protected List<FormTemplateElement> loadFormTemplateElements(List<FormTemplate> formTemplates)
	{
		if(formTemplates == null || formTemplates.isEmpty()) {
			return null;
		}
		
		List<String> templateIds = formTemplates
				.stream()
				.filter(e -> e != null && e.getId() != null && !e.getId().isBlank())
				.filter(e -> e.getTemplateStatus() == FormTemplateStatus.Open)
				.filter(e -> e.getStatus() == StatusCode.Active)
				.map(e -> e.getId().trim())
				.distinct()
				.toList();
		
		if(templateIds == null || templateIds.isEmpty()) {
			return null;
		}
		
		return formTemplateElementRepository.findByTemplateIdIn(templateIds);
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
		protected String userId;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected List<FormTemplateInfo> formTemplateInfos;
	}
}