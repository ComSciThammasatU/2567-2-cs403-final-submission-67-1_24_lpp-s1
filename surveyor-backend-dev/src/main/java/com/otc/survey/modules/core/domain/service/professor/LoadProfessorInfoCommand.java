package com.otc.survey.modules.core.domain.service.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.Role;
import com.otc.survey.modules.core.domain.entity.User;
import com.otc.survey.modules.core.domain.entity.UserGroup;
import com.otc.survey.modules.core.domain.entity.UserGroupMapper;
import com.otc.survey.modules.core.domain.entity.UserRoleMapper;
import com.otc.survey.modules.core.domain.repository.RoleRepository;
import com.otc.survey.modules.core.domain.repository.UserGroupMapperRepository;
import com.otc.survey.modules.core.domain.repository.UserGroupRepository;
import com.otc.survey.modules.core.domain.repository.UserRepository;
import com.otc.survey.modules.core.domain.repository.UserRoleMapperRepository;
import com.otc.survey.modules.core.domain.service.professor.dto.ProfessorInfo;
import com.otc.survey.modules.core.domain.service.professor.dto.UpdateProfessorRequest;
import com.otc.survey.modules.core.domain.service.professor.exception.LoadProfessorInfoException;

import lombok.Data;

@ServiceCommand
public class LoadProfessorInfoCommand extends BaseServiceCommand
{
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected UserRoleMapperRepository userRoleMapperRepository;
	
	@Autowired
	protected UserGroupRepository userGroupRepository;
	
	@Autowired
	protected UserGroupMapperRepository userGroupMapperRepository;
	
	public Response loadProfessorInfo(Request request) throws LoadProfessorInfoException
	{
		logger.info("### {}.loadProfessorInfo ###", getClass().getSimpleName());
		
		if(request.getProfessorId() == null || request.getProfessorId().isBlank()) {
			throw new IllegalArgumentException("Param 'professorId' couldn't be null or empty");
		}
		
		User user = userRepository.findById(request.getProfessorId()).orElse(null);
		
		if(user == null) {
			throw new LoadProfessorInfoException.UserNotFound(request.getProfessorId());
		}
		
		ProfessorInfo professorInfo = new ProfessorInfo();
		professorInfo.setUser(user);
		professorInfo.setRoles(loadRoles(userRoleMapperRepository.findByUserId(user.getId())));
		professorInfo.setUserGroups(loadUserGroups(userGroupMapperRepository.findByUserId(user.getId())));
		
		Response response = new Response();
		response.setProfessorInfo(professorInfo);
		
		return response;
	}
	
	protected List<Role> loadRoles(List<UserRoleMapper> userRoleMappers)
	{
		if(userRoleMappers == null || userRoleMappers.isEmpty()) {
			return null;
		}
		
		List<String> roleIds = userRoleMappers
				.stream()
				.filter(e -> e != null && e.getRoleId() != null && !e.getRoleId().isBlank())
				.map(e -> e.getRoleId())
				.distinct()
				.toList();
		
		return roleRepository.findAllById(roleIds);
	}
	
	protected List<UserGroup> loadUserGroups(List<UserGroupMapper> userGroupMappers)
	{
		if(userGroupMappers == null || userGroupMappers.isEmpty()) {
			return null;
		}
		
		List<String> groupIds = userGroupMappers
				.stream()
				.filter(e -> e != null && e.getGroupId() != null && !e.getGroupId().isBlank())
				.map(e -> e.getGroupId())
				.distinct()
				.toList();
		
		return userGroupRepository.findAllById(groupIds);
	}
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String professorId;
	}
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected ProfessorInfo professorInfo;
	}
}
