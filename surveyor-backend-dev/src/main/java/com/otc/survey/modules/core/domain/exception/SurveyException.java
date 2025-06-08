package com.otc.survey.modules.core.domain.exception;

public class SurveyException extends Exception
{
	protected String errorCode;
	protected String errorTitle;
	protected String errorMessage;
	
	public SurveyException() {
		super();
	}

//	public SurveyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}
//
//	public SurveyException(String message, Throwable cause) {
//		super(message, cause);
//	}
//
//	public SurveyException(String message) {
//		super(message);
//	}
//
//	public SurveyException(Throwable cause) {
//		super(cause);
//	}

	public SurveyException(String errorCode, String errorTitle, String errorMessage) {
		this(null, errorCode, errorTitle, errorMessage);
	}
	
	public SurveyException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
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