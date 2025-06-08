package com.otc.survey.modules.survey.domain.service.form_template.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class FormTemplateUpdateException extends SurveyException 
{
	public FormTemplateUpdateException() {
		super();
	}

	public FormTemplateUpdateException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public FormTemplateUpdateException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}
	
	
	public static class ParentTemplateNotFound extends FormTemplateUpdateException
	{
		protected String parentTemplateId;
		
		public ParentTemplateNotFound(String parentTemplateId) {
			super(null, "Invalid Data", "Parent Template [id: "+parentTemplateId+"] Not Found");
			this.parentTemplateId = parentTemplateId;
		}
		
		public String getParentTemplateId() {
			return parentTemplateId;
		}
	}
	
	
	public static class DuplicateTemplateCode extends FormTemplateUpdateException
	{
		protected String templateCode;
		
		public DuplicateTemplateCode(String templateCode) {
			super(null, "Invalid Data", "Duplicate template code '"+templateCode+"'");
			this.templateCode = templateCode;
		}
		
		public String getTemplateCode() {
			return templateCode;
		}
	}
}