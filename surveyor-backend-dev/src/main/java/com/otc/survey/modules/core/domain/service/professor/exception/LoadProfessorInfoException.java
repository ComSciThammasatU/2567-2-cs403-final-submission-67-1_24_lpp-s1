package com.otc.survey.modules.core.domain.service.professor.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class LoadProfessorInfoException extends SurveyException 
{
	public LoadProfessorInfoException() {
		super();
	}

	public LoadProfessorInfoException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public LoadProfessorInfoException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}

	public static class UserNotFound extends LoadProfessorInfoException
	{
		protected String userId;
		
		public UserNotFound(String userId) {
			super(null, "Invalid Data", "User [id: "+userId+"] Not Found");
			this.userId = userId;
		}
		
		public String getUserId() {
			return userId;
		}
	}
}
