package com.otc.survey.modules.core.domain.exception;

public class SurveyRuntimeException extends RuntimeException
{
	protected String errorCode;
	protected String errorTitle;
	protected String errorMessage;
	
	public SurveyRuntimeException() {
		
	}
	
	public SurveyRuntimeException(String errorCode, String errorTitle, String errorMessage) {
		this(null, errorCode, errorTitle, errorMessage);
	}
	
	public SurveyRuntimeException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
		this.errorTitle = errorTitle;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getErrorTitle() {
		return errorTitle;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}