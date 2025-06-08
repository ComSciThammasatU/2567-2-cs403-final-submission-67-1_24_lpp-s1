package com.otc.survey.modules.core.domain.service.student.dto;

import java.util.List;

import com.otc.survey.modules.common.domain.constant.StatusCode;

import lombok.Data;

@Data
public class CreateStudentRequest 
{
	protected String studentCode;
	protected String accountName;
	protected String email;
	protected String username;
	protected String password;
	protected String studyDegreeId;
	protected Integer studyClassLevel;
	protected List<String> userGroupIds;
	protected StatusCode initialStatus;
}