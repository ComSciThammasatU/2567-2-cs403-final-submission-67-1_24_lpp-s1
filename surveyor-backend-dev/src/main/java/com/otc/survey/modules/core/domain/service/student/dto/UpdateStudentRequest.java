package com.otc.survey.modules.core.domain.service.student.dto;

import java.util.List;

import com.otc.survey.modules.common.domain.constant.StatusCode;

import lombok.Data;

@Data
public class UpdateStudentRequest 
{
	protected String studentId;
	protected String studentCode;
	protected String studyDegreeId;
	protected Integer studyClassLevel;
	
	protected String userId;
	protected String username;
	protected String email;
	protected String accountName;
	
	protected List<String> userGroupIds;
	
	protected StatusCode status;
}