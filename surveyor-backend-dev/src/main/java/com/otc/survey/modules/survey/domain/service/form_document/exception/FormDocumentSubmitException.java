package com.otc.survey.modules.survey.domain.service.form_document.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class FormDocumentSubmitException extends SurveyException
{
	public FormDocumentSubmitException() {
		super();
	}

	public FormDocumentSubmitException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public FormDocumentSubmitException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}
	
	
	public static class TemplateNotFound extends FormDocumentSubmitException
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
	
	
	public static class DuplicateDocument extends FormDocumentSubmitException
	{
		protected String templateId;
		protected String userId;
		
		public DuplicateDocument(String templateId, String userId) {
			super(null, "Invalid Data", "Duplicate document for [templateId: "+templateId+", userId: "+userId+"]");
			this.templateId = templateId;
			this.userId = userId;
		}
		
		public String getTemplateId() {
			return templateId;
		}
		
		public String getUserId() {
			return userId;
		}
	}
}