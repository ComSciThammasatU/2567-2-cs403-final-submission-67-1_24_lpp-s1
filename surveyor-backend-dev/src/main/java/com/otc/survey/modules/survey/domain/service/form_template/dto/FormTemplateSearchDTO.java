package com.otc.survey.modules.survey.domain.service.form_template.dto;

import java.util.List;

import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;

import lombok.Data;

public class FormTemplateSearchDTO 
{
	@Data
	public static class RequestPayload
	{
		protected Criteria criteria;
		
		
		@Data
		public static class Criteria
		{
			protected String templateGroupId;
			protected String templateCode;
			protected String templateName;
			protected String templateStatus;
			protected String status;
		}
	}
	
	
	@Data
	public static class ResponsePayload
	{
		protected List<FormTemplateInfo> formTemplateInfos;
	}
}