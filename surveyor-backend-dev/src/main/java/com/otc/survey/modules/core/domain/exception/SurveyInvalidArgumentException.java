package com.otc.survey.modules.core.domain.exception;

public class SurveyInvalidArgumentException extends SurveyRuntimeException
{
	public SurveyInvalidArgumentException() {
		super();
	}

	public SurveyInvalidArgumentException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public SurveyInvalidArgumentException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}
}