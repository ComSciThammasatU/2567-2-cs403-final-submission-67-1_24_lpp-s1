package com.otc.survey.modules.core.domain.service.professor;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
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
import com.otc.survey.modules.core.domain.service.professor.dto.UpdateProfessorRequest;
import com.otc.survey.modules.core.domain.service.professor.dto.UpdateProfessorResponse;
import com.otc.survey.modules.core.domain.service.professor.exception.UpdateProfessorException;

import lombok.Data;

@ServiceCommand
public class UpdateProfessorCommand extends BaseServiceCommand 
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
	
	public Response updateProfessor(Request request) throws UpdateProfessorException
	{
		logger.info("### {}.updateProfessor ###", getClass().getSimpleName());
		
		validate(request);
		
		UpdateProfessorRequest updateProfessorRequest = request.getUpdateProfessorRequest();
		
		User user = userRepository.findById(updateProfessorRequest.getUserId()).orElse(null);
		
		if(user == null) {
			throw new UpdateProfessorException.UserNotFound(updateProfessorRequest.getUserId());
		}
		
		checkDuplicateUsername(updateProfessorRequest);
		
		checkDuplicateEmail(updateProfessorRequest);
		
		user.setUsername(updateProfessorRequest.getUsername());
		user.setEmail(updateProfessorRequest.getEmail());
		user.setAccountName(updateProfessorRequest.getAccountName());
		user.setStatus(updateProfessorRequest.getStatus());
		user.setLastModifiedBy(request.getPerformedBy());
		user.setLastModifiedAt(request.getPerformedAt());
		userRepository.save(user);
		logger.info("Update user => {}", user);
		
		userRoleMapperRepository.removeByUserId(user.getId());
		userRoleMapperRepository.flush(); 
		logger.info("Remove userRoleMappers by user id [{}]", user.getId());
		
		List<UserRoleMapper> userRoleMappers = buildUserRoleMappers(request, user);
		if(userRoleMappers != null && !userRoleMappers.isEmpty()) {
			userRoleMapperRepository.saveAll(userRoleMappers);
			logger.info("Create userRoleMappers => {}", userRoleMappers);
		}
		
		userGroupMapperRepository.removeByUserId(user.getId());
		userGroupMapperRepository.flush(); 
		logger.info("Remove userGroupMappers by user id [{}]", user.getId());
		
		List<UserGroupMapper> userGroupMappers = buildUserGroupMappers(request, user);
		if(userGroupMappers != null && !userGroupMappers.isEmpty()) {
			userGroupMapperRepository.saveAll(userGroupMappers);
			logger.info("Create userGroupMappers => {}", userGroupMappers);
		}
		
		UpdateProfessorResponse updateProfessorResponse = new UpdateProfessorResponse();
		updateProfessorResponse.setUser(user);
		updateProfessorResponse.setRoles(loadRoles(userRoleMapperRepository.findByUserId(user.getId())));
		updateProfessorResponse.setUserGroups(loadUserGroups(userGroupMappers));
		
		Response response = new Response();
		response.setUpdateProfessorResponse(updateProfessorResponse);
		
		return response;
	}
	
	protected boolean validate(Request request) throws UpdateProfessorException
	{
		UpdateProfessorRequest updateProfessorRequest = request.getUpdateProfessorRequest();
		
		
		if(updateProfessorRequest.getUserId() == null || updateProfessorRequest.getUserId().isBlank()) {
			throw new IllegalArgumentException("Param 'updateProfessorRequest.userId' Couldn't Be Null OR Empty");
		}
		
		if(updateProfessorRequest.getUsername() == null || updateProfessorRequest.getUsername().isBlank()) {
			throw new IllegalArgumentException("Param 'updateProfessorRequest.username' Couldn't Be Null OR Empty");
		}
		
		if(updateProfessorRequest.getEmail() == null || updateProfessorRequest.getEmail().isBlank()) {
			throw new IllegalArgumentException("Param 'updateProfessorRequest.email' Couldn't Be Null OR Empty");
		}
		
		if(updateProfessorRequest.getAccountName() == null || updateProfessorRequest.getAccountName().isBlank()) {
			throw new IllegalArgumentException("Param 'updateProfessorRequest.accountName' Couldn't Be Null OR Empty");
		}
		
		return true;
	}
	
	protected boolean checkDuplicateUsername(UpdateProfessorRequest updateProfessorRequest) throws UpdateProfessorException
	{
		User comparedUser = userRepository.findByUsername(updateProfessorRequest.getUsername());
		if(comparedUser != null && !comparedUser.getId().equals(updateProfessorRequest.getUserId())) {
			throw new UpdateProfessorException.DuplicateUsername(updateProfessorRequest.getUsername());
		}
		return true;
	}
	
	protected boolean checkDuplicateEmail(UpdateProfessorRequest updateProfessorRequest) throws UpdateProfessorException
	{
		User comparedUser = userRepository.findByEmail(updateProfessorRequest.getEmail());
		if(comparedUser != null && !comparedUser.getId().equals(updateProfessorRequest.getUserId())) {
			throw new UpdateProfessorException.DuplicateEmail(updateProfessorRequest.getEmail());
		}
		return true;
	}
	
	protected List<UserRoleMapper> buildUserRoleMappers(Request request, User user)
	{
		List<String> roleIds = request.getUpdateProfessorRequest().getRoleIds();
		
		if(roleIds == null || roleIds.isEmpty()) {
			return null;
		}
		
		return roleIds
			.stream()
			.filter(roleId -> roleId != null)
			.map(roleId -> {
				UserRoleMapper userRoleMapper = new UserRoleMapper();
				userRoleMapper.setId(UUID.randomUUID().toString());
				userRoleMapper.setUserId(user.getId());
				userRoleMapper.setRoleId(roleId);
				userRoleMapper.setStatus(StatusCode.Active);
				userRoleMapper.setCreatedBy(user.getId());
				userRoleMapper.setCreatedAt(request.getPerformedAt());
				
				return userRoleMapper;
			})
			.toList();
	}
	
	protected List<UserGroupMapper> buildUserGroupMappers(Request request, User user)
	{
		List<String> userGroupIds = request.getUpdateProfessorRequest().getUserGroupIds();
		
		if(userGroupIds == null || userGroupIds.isEmpty()) {
			return null;
		}
		
		return userGroupIds
			.stream()
			.filter(groupId -> groupId != null && !groupId.isBlank())
			.map(groupId -> {
				UserGroupMapper userGroupMapper = new UserGroupMapper();
				userGroupMapper.setId(UUID.randomUUID().toString());
				userGroupMapper.setUserId(user.getId());
				userGroupMapper.setGroupId(groupId);
				userGroupMapper.setStatus(StatusCode.Active);
				userGroupMapper.setCreatedBy(user.getId());
				userGroupMapper.setCreatedAt(request.getPerformedAt());
				
				return userGroupMapper;
			})
			.toList();
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
		protected UpdateProfessorRequest updateProfessorRequest;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected UpdateProfessorResponse updateProfessorResponse;
	}
}
