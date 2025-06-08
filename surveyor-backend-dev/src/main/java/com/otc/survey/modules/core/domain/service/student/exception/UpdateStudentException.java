package com.otc.survey.modules.core.domain.service.student.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class UpdateStudentException extends SurveyException 
{
	public UpdateStudentException() {
		super();
	}

	public UpdateStudentException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public UpdateStudentException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}
	
	
	public static class StudentNotFound extends UpdateStudentException
	{
		protected String studentId;
		
		public StudentNotFound(String studentId) {
			super(null, "Invalid Data", "Student [id: "+studentId+"] Not Found");
			this.studentId = studentId;
		}
		
		public String getStudentId() {
			return studentId;
		}
	}
	
	
	public static class DuplicateStudentCode extends UpdateStudentException
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
	
	
	public static class UserNotFound extends UpdateStudentException
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
	
	
	public static class DuplicateUsername extends UpdateStudentException
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
	
	
	public static class DuplicateEmail extends UpdateStudentException
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