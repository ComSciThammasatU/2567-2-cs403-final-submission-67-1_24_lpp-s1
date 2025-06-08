package com.otc.survey.modules.survey.domain.service.form_template.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.core.domain.constant.Permission;
import com.otc.survey.modules.core.domain.constant.PrincipalType;
import com.otc.survey.modules.survey.domain.constant.FormTemplateStatus;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;
import com.otc.survey.modules.survey.domain.entity.FormTemplateElement;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.model.form_element.FormElementProperty;

import lombok.Data;

public class FormTemplateUpdateDTO 
{
	@Data
	public static class RequestPayload
	{
		protected String parentTemplateId;
		protected String templateGroupId;
		protected String templateCode;
		protected String templateName;
		protected String templateDescription;
		protected String remark;
		protected boolean notify;
		protected FormTemplateStatus templateStatus;
		protected StatusCode status;
		protected List<Element> elements;
		protected List<Authority> authorities;
		
		
		@Data
		public static class Element
		{
			protected String code;
			protected String typeId;
			protected int orderNo;
			protected String title;
			protected String description;
			protected FormElementProperty<?> property;
		}
		
		
		@Data
		public static class Authority
		{
			protected PrincipalType principalType;
			protected String principalRefId;
		}
		
		
		public FormTemplate toFormTemplate(FormTemplate parentFormTemplate)
		{
			FormTemplate formTemplate = new FormTemplate();
			formTemplate.setId(UUID.randomUUID().toString());
			formTemplate.setCode(templateCode);
			formTemplate.setRevision(parentFormTemplate.getRevision() + 1);
			formTemplate.setTemplateGroupId(templateGroupId);
			formTemplate.setHierachyLevel(parentFormTemplate.getHierachyLevel() + 1);
			formTemplate.setLineageTemplates(buildLineageTemplates(parentFormTemplate));
			formTemplate.setRootTemplateId(parentFormTemplate.getRootTemplateId() != null ? parentFormTemplate.getRootTemplateId() : parentFormTemplate.getId());
			formTemplate.setParentTemplateId(parentFormTemplate.getId());
			formTemplate.setName(templateName);
			formTemplate.setDescription(templateDescription);
			formTemplate.setRemark(remark);
			formTemplate.setNotify(notify);
			formTemplate.setTemplateStatus(templateStatus);
			formTemplate.setStatus(status);
			formTemplate.setCreatedBy(parentFormTemplate.getCreatedBy());
			formTemplate.setCreatedAt(parentFormTemplate.getCreatedAt());
			
			if(formTemplate.getTemplateStatus() == null) {
				formTemplate.setTemplateStatus(FormTemplateStatus.Pending);
			}
			
			if(formTemplate.getStatus() == null) {
				formTemplate.setStatus(StatusCode.Active);
			}
			
			return formTemplate;
		}
		
		protected List<FormTemplate.LineageTemplate> buildLineageTemplates(FormTemplate parentFormTemplate)
		{
			List<FormTemplate.LineageTemplate> lineageTemplates = parentFormTemplate.getLineageTemplates();
			
			if(lineageTemplates == null) {
				lineageTemplates = new ArrayList<>();
			}
			
			if(! lineageTemplates.isEmpty()) {
				lineageTemplates = new ArrayList<>(lineageTemplates);
			}
			
			lineageTemplates.add(
					FormTemplate.LineageTemplate
						.builder()
						.templateId(parentFormTemplate.getId())
						.hierachyLevel(parentFormTemplate.getHierachyLevel())
						.build()
				);
			
			return lineageTemplates;
		}
		
		
		public List<FormTemplateElement> toFormTemplateElements(FormTemplate formTemplate)
		{
			if(elements == null || elements.isEmpty()) {
				return null;
			}
			
			return elements.stream()
					.map(element -> toFormTemplateElement(formTemplate, element))
					.toList();
		}
		
		public FormTemplateElement toFormTemplateElement(FormTemplate formTemplate, Element element)
		{
			FormTemplateElement formTemplateElement = new FormTemplateElement();
			formTemplateElement.setId(UUID.randomUUID().toString());
			formTemplateElement.setTemplateId(formTemplate.getId());
			formTemplateElement.setElementCode(element.getCode());
			formTemplateElement.setElementTypeId(element.getTypeId());
			formTemplateElement.setOrderNo(element.getOrderNo());
			formTemplateElement.setTitle(element.getTitle());
			formTemplateElement.setDescription(element.getDescription());
			formTemplateElement.setProperty(null);
			formTemplateElement.setStatus(StatusCode.Active);
			
			return formTemplateElement;
		}
		
		
		public List<FormTemplateAuthority> toFormTemplateAuthorities(FormTemplate formTemplate)
		{
			if(authorities == null || authorities.isEmpty()) {
				return null;
			}
			
			return authorities.stream()
					.map(authority -> toFormTemplateAuthority(formTemplate, authority))
					.toList();
		}
		
		public FormTemplateAuthority toFormTemplateAuthority(FormTemplate formTemplate, Authority authority)
		{
			FormTemplateAuthority formTemplateAuthority = new FormTemplateAuthority();
			formTemplateAuthority.setId(UUID.randomUUID().toString());
			formTemplateAuthority.setTemplateId(formTemplate.getId());
			formTemplateAuthority.setPrincipalType(authority.getPrincipalType());
			formTemplateAuthority.setPrincipalRefId(authority.getPrincipalRefId());
			formTemplateAuthority.setPermission(Permission.Granted);
			formTemplateAuthority.setStatus(StatusCode.Active);
			
			return formTemplateAuthority;
		}
	}
	
	
	@Data
	public static class ResponsePayload
	{
		protected FormTemplate parentTemplate;
		protected FormTemplateInfo formTemplateInfo;
	}
}