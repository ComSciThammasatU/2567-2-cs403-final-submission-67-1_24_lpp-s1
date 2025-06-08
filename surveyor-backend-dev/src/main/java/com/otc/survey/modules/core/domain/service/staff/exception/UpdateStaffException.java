package com.otc.survey.modules.core.domain.service.staff.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class UpdateStaffException extends SurveyException
{
	public UpdateStaffException() {
		super();
	}

	public UpdateStaffException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public UpdateStaffException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}

	public static class UserNotFound extends UpdateStaffException
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
	
	public static class DuplicateUsername extends UpdateStaffException
	{
		protected String username;
		
		public DuplicateUsername(String username) {
			super(null, "Invalid Data", "Duplicate username '"+username+"'");
			this.username = username;
		}
		
		public String getUsername() {
			return username;
		}
	}
	
	
	public static class DuplicateEmail extends UpdateStaffException
	{
		protected String email;
		
		public DuplicateEmail(String email) {
			super(null, "Invalid Data", "Duplicate email '"+email+"'");
			this.email = email;
		}
		
		public String getEmail() {
			return email;
		}
	}
}
