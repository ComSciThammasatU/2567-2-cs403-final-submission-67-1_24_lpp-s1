package com.otc.survey.modules.survey.domain.service.form_document.dto;

import java.util.List;

import com.otc.survey.modules.survey.domain.constant.FormDocumentStatus;
import com.otc.survey.modules.survey.domain.model.FormDocumentInfo;

import lombok.Data;

public class FormDocumentSearchDTO 
{
	@Data
	public static class RequestPayload
	{
		protected SearchCriteria criteria;
		
		
		@Data
		public static class SearchCriteria
		{
			protected List<String> templateGroupIds;
			protected List<String> templateIds;
			protected String templateCode;
			protected String templateName;
			protected List<String> userGroupIds;
			protected List<FormDocumentStatus> documentStatuses;
		}
	}
	
	
	@Data
	public static class ResponsePayload
	{
		protected List<FormDocumentInfo> formDocumentInfos;
	}
}