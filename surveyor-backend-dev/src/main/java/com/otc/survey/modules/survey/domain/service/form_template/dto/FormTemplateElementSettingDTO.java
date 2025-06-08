package com.otc.survey.modules.survey.domain.service.form_template.dto;

import java.util.List;
import java.util.Map;

import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;

import lombok.Data;

public class FormTemplateElementSettingDTO 
{
	@Data
	public static class RequestPayload
	{
		protected String formTemplateId;
		protected List<Map<String, Object>> elementDatas;
	}
	
	
	@Data
	public static class ResponsePayload
	{
		protected FormTemplateInfo formTemplateInfo;
	}
}