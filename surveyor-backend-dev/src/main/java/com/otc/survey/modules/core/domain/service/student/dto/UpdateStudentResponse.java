package com.otc.survey.modules.core.domain.service.student.dto;

import java.util.List;

import com.otc.survey.modules.core.domain.entity.Role;
import com.otc.survey.modules.core.domain.entity.Student;
import com.otc.survey.modules.core.domain.entity.User;
import com.otc.survey.modules.core.domain.entity.UserGroup;

import lombok.Data;

@Data
public class UpdateStudentResponse 
{
	protected Student student;
	protected User user;
	protected List<Role> roles;
	protected List<UserGroup> userGroups;
}