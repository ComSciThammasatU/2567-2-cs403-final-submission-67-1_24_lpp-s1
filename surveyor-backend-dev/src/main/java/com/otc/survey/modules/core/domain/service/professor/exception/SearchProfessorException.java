package com.otc.survey.modules.core.domain.service.professor.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class SearchProfessorException extends SurveyException 
{
	public SearchProfessorException() {
		super();
	}

	public SearchProfessorException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public SearchProfessorException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}

	public static class DuplicateUsername extends SearchProfessorException
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
	
	
	public static class DuplicateEmail extends SearchProfessorException
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
