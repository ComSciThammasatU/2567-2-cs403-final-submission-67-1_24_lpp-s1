package com.otc.survey.modules.survey.domain.service.form_document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.User;
import com.otc.survey.modules.core.domain.repository.UserRepository;
import com.otc.survey.modules.survey.domain.entity.FormDocument;
import com.otc.survey.modules.survey.domain.entity.FormDocumentElement;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;
import com.otc.survey.modules.survey.domain.entity.FormTemplateElement;
import com.otc.survey.modules.survey.domain.model.FormDocumentInfo;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormDocumentElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateAuthorityRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateElementRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;

import lombok.Data;

@ServiceCommand
public class FormDocumentBuildInfoCommand extends BaseServiceCommand
{
	@Autowired
	protected FormDocumentElementRepository formDocumentElementRepository;
	
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateElementRepository formTemplateElementRepository;
	
	@Autowired
	protected FormTemplateAuthorityRepository formTemplateAuthorityRepository;
	
	@Autowired
	protected UserRepository userRepository;
	
	
	public Response buildDocumentInfo(Request request)
	{
		logger.info("### {}.buildDocumentInfo ###", getClass().getSimpleName());
		
		List<FormDocument> formDocuments = request.getFormDocuments();
		
		if(formDocuments == null || formDocuments.isEmpty()) {
			return new Response();
		}
		
		List<FormDocumentElement> formDocumentElements = loadFormDocumentElements(formDocuments);
		List<FormTemplate> formTemplates = loadFormTemplates(formDocuments);
		List<FormTemplateElement> formTemplateElements = loadFormTemplateElements(formTemplates);
		List<FormTemplateAuthority> formTemplateAuthorities = loadFormTemplateAuthorities(formTemplates);
		
		List<FormDocumentInfo> formDocumentInfos = new ArrayList<>();
		
		for(FormDocument formDocument : formDocuments) {
			List<FormDocumentElement> _documentElements = null;
			if(formDocumentElements != null && !formDocumentElements.isEmpty()) {
				_documentElements = formDocumentElements
						.stream()
						.filter(e -> e != null && e.getDocumentId() != null && e.getDocumentId().equals(formDocument.getId()))
						.toList();
			}
			
			FormTemplate formTemplate = null;
			if(formTemplates != null && !formTemplates.isEmpty()) {
				formTemplate = formTemplates
						.stream()
						.filter(e -> e != null && e.getId() != null && e.getId().equals(formDocument.getTemplateId()))
						.findFirst()
						.orElse(null);
			}
			
			List<FormTemplateElement> _formTemplateElements = null;
			if(formTemplate != null && formTemplateElements != null && !formTemplateElements.isEmpty()) {
				FormTemplate _formTemplate = formTemplate;
				_formTemplateElements = formTemplateElements
						.stream()
						.filter(e -> e != null && e.getTemplateId() != null && e.getTemplateId().equals(_formTemplate.getId()))
						.toList();
			}
			
			List<FormTemplateAuthority> _formTemplateAuthorities = null;
			if(formTemplate != null && formTemplateAuthorities != null && !formTemplateAuthorities.isEmpty()) {
				FormTemplate _formTemplate = formTemplate;
				_formTemplateAuthorities = formTemplateAuthorities
						.stream()
						.filter(e -> e != null && e.getTemplateId() != null && e.getTemplateId().equals(_formTemplate.getId()))
						.toList();
			}
			
			FormTemplateInfo formTemplateInfo = new FormTemplateInfo();
			formTemplateInfo.setFormTemplate(formTemplate);
			formTemplateInfo.setFormTemplateElements(_formTemplateElements);
			formTemplateInfo.setFormTemplateAuthorities(_formTemplateAuthorities);
			
			FormDocumentInfo formDocumentInfo = new FormDocumentInfo();
			formDocumentInfo.setFormDocument(formDocument);
			formDocumentInfo.setFormDocumentElements(_documentElements);
			formDocumentInfo.setFormTemplateInfo(formTemplateInfo);
			
			formDocumentInfos.add(formDocumentInfo);
		}
		
		fetchSubmittedUserProfile(request, formDocumentInfos);
		
		Response response = new Response();
		response.setFormDocumentInfos(formDocumentInfos);
		
		return response;
	}
	
	protected List<FormDocumentElement> loadFormDocumentElements(List<FormDocument> formDocuments)
	{
		if(formDocuments == null || formDocuments.isEmpty()) {
			return null;
		}
		
		List<String> documentIds = formDocuments
				.stream()
				.filter(e -> e != null && e.getId() != null && !e.getId().isBlank())
				.map(e -> e.getId())
				.distinct()
				.toList();
		
		if(documentIds == null || documentIds.isEmpty()) {
			return null;
		}
		
		return formDocumentElementRepository.findByDocumentIdIn(documentIds);
	}
	
	protected List<FormTemplate> loadFormTemplates(List<FormDocument> formDocuments)
	{
		if(formDocuments == null || formDocuments.isEmpty()) {
			return null;
		}
		
		List<String> templateIds = formDocuments
				.stream()
				.filter(e -> e != null && e.getTemplateId() != null && !e.getTemplateId().isBlank())
				.map(e -> e.getTemplateId())
				.distinct()
				.toList();
		
		if(templateIds == null || templateIds.isEmpty()) {
			return null;
		}
		
		return formTemplateRepository.findAllById(templateIds);
	}
	
	protected List<FormTemplateElement> loadFormTemplateElements(List<FormTemplate> formTemplates)
	{
		if(formTemplates == null || formTemplates.isEmpty()) {
			return null;
		}
		
		List<String> templateIds = formTemplates
				.stream()
				.filter(e -> e != null && e.getId() != null && !e.getId().isBlank())
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
		
		List<String> templateIds = formTemplates
				.stream()
				.filter(e -> e != null && e.getId() != null && !e.getId().isBlank())
				.map(e -> e.getId())
				.distinct()
				.toList();
		
		if(templateIds == null || templateIds.isEmpty()) {
			return null;
		}
		
		return formTemplateAuthorityRepository.findByTemplateIdIn(templateIds);
	}
	
	protected void fetchSubmittedUserProfile(Request request, List<FormDocumentInfo> formDocumentInfos)
	{
		if(request.isFetchSubmittedUser() && formDocumentInfos != null && !formDocumentInfos.isEmpty()) {
			List<String> userIds = formDocumentInfos
					.stream()
					.filter(e -> e != null && e.getFormDocument() != null && e.getFormDocument().getSubmittedUserId() != null)
					.map(e -> e.getFormDocument().getSubmittedUserId().trim())
					.distinct()
					.toList();
			
			if(userIds == null || userIds.isEmpty()) {
				return;
			}
			
			List<User> users = userRepository.findAllById(userIds);
			
			if(users == null || users.isEmpty()) {
				return;
			}
			
			for(FormDocumentInfo formDocumentInfo : formDocumentInfos) {
				User user = users
						.stream()
						.filter(e -> e.getId().equals(formDocumentInfo.getFormDocument().getSubmittedUserId()))
						.findFirst()
						.orElse(null);
				
				if(user != null) {
					formDocumentInfo.setSubmittedUser(user);
				}
			}
		}
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected List<FormDocument> formDocuments;
		protected boolean fetchSubmittedUser = false;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected List<FormDocumentInfo> formDocumentInfos;
	}
}