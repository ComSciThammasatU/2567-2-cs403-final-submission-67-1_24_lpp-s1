package com.otc.survey.modules.core.domain.service.professor.dto;

import java.util.List;

import com.otc.survey.modules.common.domain.constant.StatusCode;

import lombok.Data;

@Data
public class CreateProfessorRequest 
{
	protected String accountName;
	protected String email;
	protected String username;
	protected String password;
	protected List<String> userGroupIds;
	protected StatusCode initialStatus;
}
