package com.otc.survey.modules.core.domain.service.student.dto;

import lombok.Data;

@Data
public class CreateStudentResponse 
{
	protected StudentInfo studentInfo;
	
	
	public RegisterStudentResponse toRegisterStudentResponse()
	{
		RegisterStudentResponse registerStudentResponse = new RegisterStudentResponse();
		registerStudentResponse.setStudentInfo(studentInfo);
		
		return registerStudentResponse;
	}
}