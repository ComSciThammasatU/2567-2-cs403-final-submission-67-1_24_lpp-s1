package com.otc.survey.modules.core.domain.service.staff.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class LoadStaffInfoException extends SurveyException 
{
	public LoadStaffInfoException() {
		super();
	}

	public LoadStaffInfoException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public LoadStaffInfoException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}

	public static class UserNotFound extends LoadStaffInfoException
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
