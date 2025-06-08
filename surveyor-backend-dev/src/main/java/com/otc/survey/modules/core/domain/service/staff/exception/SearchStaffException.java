package com.otc.survey.modules.core.domain.service.staff.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class SearchStaffException extends SurveyException
{
	public SearchStaffException() {
		super();
	}

	public SearchStaffException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public SearchStaffException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}

	public static class DuplicateUsername extends SearchStaffException
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
	
	
	public static class DuplicateEmail extends SearchStaffException
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
