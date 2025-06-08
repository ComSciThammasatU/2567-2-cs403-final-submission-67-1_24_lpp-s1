package com.otc.survey.modules.core.domain.service.student.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class LoadStudentInfoException extends SurveyException 
{
	public LoadStudentInfoException() {
		super();
	}

	public LoadStudentInfoException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public LoadStudentInfoException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}
	
	
	public static class StudentNotFound extends LoadStudentInfoException
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
	
	
	public static class UserNotFound extends LoadStudentInfoException
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