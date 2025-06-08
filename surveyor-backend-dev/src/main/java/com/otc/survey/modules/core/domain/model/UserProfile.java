package com.otc.survey.modules.core.domain.model;

import java.util.List;

import com.otc.survey.modules.core.domain.entity.Role;
import com.otc.survey.modules.core.domain.entity.User;
import com.otc.survey.modules.core.domain.entity.UserGroup;
import com.otc.survey.modules.core.domain.entity.UserType;

import lombok.Data;

@Data
public class UserProfile 
{
	protected User user;
	protected UserType userType;
	protected List<UserGroup> userGroups;
	protected List<Role> roles;
}