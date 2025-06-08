package com.otc.survey.modules.core.domain.service.student.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class RegisterStudentException extends SurveyException 
{
	public RegisterStudentException() {
		super();
	}

	public RegisterStudentException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public RegisterStudentException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}
	
	
	public static class DuplicateStudentCode extends RegisterStudentException
	{
		protected String studentCode;
		
		public DuplicateStudentCode(String studentCode) {
			super(null, "Invalid Data", "Duplicate student code '"+studentCode+"'");
			this.studentCode = studentCode;
		}
		
		public String getStudentCode() {
			return studentCode;
		}
	}
	
	
	public static class DuplicateUsername extends RegisterStudentException
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
	
	
	public static class DuplicateEmail extends RegisterStudentException
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