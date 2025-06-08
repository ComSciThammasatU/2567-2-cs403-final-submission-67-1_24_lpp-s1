package com.otc.survey.modules.core.domain.service.student.dto;

import com.otc.survey.modules.core.domain.model.line.LineUserProfile;

import lombok.Data;

@Data
public class RegisterStudentRequest 
{
	protected String studentCode;
	protected String accountName;
	protected String email;
	protected String username;
	protected String password;
	protected String studyDegreeId;
	protected Integer studyClassLevel;
	protected LineUserProfile lineUserProfile;
	
	
	public CreateStudentRequest toCreateStudentRequest()
	{
		CreateStudentRequest createStudentRequest = new CreateStudentRequest();
		createStudentRequest.setStudentCode(studentCode);
		createStudentRequest.setAccountName(accountName);
		createStudentRequest.setEmail(email);
		createStudentRequest.setUsername(username);
		createStudentRequest.setPassword(password);
		createStudentRequest.setStudyDegreeId(studyDegreeId);
		createStudentRequest.setStudyClassLevel(studyClassLevel);
		
		return createStudentRequest;
	}
}