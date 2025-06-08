package com.otc.survey.modules.core.domain.service.staff.dto;

import java.util.List;

import com.otc.survey.modules.common.domain.constant.StatusCode;

import lombok.Data;

@Data
public class UpdateStaffRequest 
{
	protected String userId;
	protected String username;
	protected String email;
	protected String accountName;
	protected List<String> roleIds;
	protected List<String> userGroupIds;
	protected StatusCode status;
}
