package com.otc.survey.modules.core.domain.service.user.exception;

import com.otc.survey.modules.core.domain.exception.SurveyException;

public class LoginException extends SurveyException
{
	public LoginException() {
		super();
	}

	public LoginException(String errorCode, String errorTitle, String errorMessage) {
		super(errorCode, errorTitle, errorMessage);
	}

	public LoginException(Throwable cause, String errorCode, String errorTitle, String errorMessage) {
		super(cause, errorCode, errorTitle, errorMessage);
	}
	
	
	public static class InvalidUsername extends LoginException
	{
		protected String username;
		
		public InvalidUsername(String username) {
			super(null, "Invalid Username", "Username '"+username+"' Not Found");
			this.username = username;
		}
		
		public String getUsername() {
			return username;
		}
	}
	
	
	public static class InvalidPassword extends LoginException
	{
		protected String password;
		
		public InvalidPassword(String password) {
			super(null, "Invalid Password", "Incorrect Password");
			this.password = password;
		}
		
		public String getPassword() {
			return password;
		}
	}
}