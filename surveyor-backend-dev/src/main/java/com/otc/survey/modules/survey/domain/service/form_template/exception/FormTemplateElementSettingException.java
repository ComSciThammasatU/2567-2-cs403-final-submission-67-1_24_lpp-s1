package com.otc.survey.modules.survey.domain.service.form_template.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class FormTemplateElementSettingException extends SurveyException
{
	public FormTemplateElementSettingException() {
		super();
	}

	public FormTemplateElementSettingException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public FormTemplateElementSettingException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}
	
	
	public static class TemplateNotFound extends FormTemplateElementSettingException
	{
		protected String templateId;
		
		public TemplateNotFound(String templateId) {
			super(null, "Invalid Data", "Template [id: "+templateId+"] Not Found");
			this.templateId = templateId;
		}
		
		public String getTemplateId() {
			return templateId;
		}
	}
	
	
	public static class InvalidElementData extends FormTemplateElementSettingException
	{
		protected String parentTemplateId;
		protected String elementId;
		
		public InvalidElementData(String parentTemplateId, String elementId) {
			super(null, "Invalid Data", "Invalid element data for element id ["+elementId+"]");
			this.parentTemplateId = parentTemplateId;
			this.elementId = elementId;
		}
		
		public String getParentTemplateId() {
			return parentTemplateId;
		}
		
		public String getElementId() {
			return elementId;
		}
	}
}