package com.otc.survey.modules.survey.domain.service.form_document.dto;

import java.util.List;
import java.util.Map;

import com.otc.survey.modules.survey.domain.model.FormDocumentInfo;

import lombok.Data;

public class FormDocumentSubmitDTO 
{
	@Data
	public static class RequestPayload
	{
		protected String templateId;
		protected String userId;
		protected List<Element> elements;
		
		
		@Data
		public static class Element
		{
			protected String templateElementId;
			protected String elementTypeId;
			protected String elementValue;
			protected Map<String, Object> elementDatas;
			protected int orderNo;
		}
	}
	
	
	@Data
	public static class ResponsePayload
	{
		protected FormDocumentInfo formDocumentInfo;
	}
}