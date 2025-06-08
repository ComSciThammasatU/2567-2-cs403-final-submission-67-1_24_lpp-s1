package com.otc.survey.modules.core.domain.service.user.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class UserException extends SurveyException
{
	public UserException() {
		super();
	}

//	public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}
//
//	public UserException(String message, Throwable cause) {
//		super(message, cause);
//	}
//
//	public UserException(String message) {
//		super(message);
//	}
//
//	public UserException(Throwable cause) {
//		super(cause);
//	}

	public UserException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}
	
	public UserException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}

	public static class UserNotFound extends UserException
	{
		protected String userId;
		
		public UserNotFound(String userId) {
			super(null, "User Not Found", "User id='"+userId+"' Not Found");
			this.userId = userId;
		}
		
		public String getUserId() {
			return userId;
		}
	}
}