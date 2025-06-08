package com.otc.survey.modules.core.domain.service.staff;

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
import com.otc.survey.modules.core.domain.service.staff.dto.UpdateStaffRequest;
import com.otc.survey.modules.core.domain.service.staff.dto.UpdateStaffResponse;
import com.otc.survey.modules.core.domain.service.staff.exception.UpdateStaffException;

import lombok.Data;

@ServiceCommand
public class UpdateStaffCommand extends BaseServiceCommand
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
	
	public Response updateStaff(Request request) throws UpdateStaffException
	{
		logger.info("### {}.updateStaff ###", getClass().getSimpleName());
		
		validate(request);
		
		UpdateStaffRequest updateStaffRequest = request.getUpdateStaffRequest();
		
		User user = userRepository.findById(updateStaffRequest.getUserId()).orElse(null);
		
		if(user == null) {
			throw new UpdateStaffException.UserNotFound(updateStaffRequest.getUserId());
		}

		checkDuplicateUsername(updateStaffRequest);
		
		checkDuplicateEmail(updateStaffRequest);
		
		user.setUsername(updateStaffRequest.getUsername());
		user.setEmail(updateStaffRequest.getEmail());
		user.setAccountName(updateStaffRequest.getAccountName());
		user.setStatus(updateStaffRequest.getStatus());
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
		
		UpdateStaffResponse updateStaffResponse = new UpdateStaffResponse();
		updateStaffResponse.setUser(user);
		updateStaffResponse.setRoles(loadRoles(userRoleMapperRepository.findByUserId(user.getId())));
		updateStaffResponse.setUserGroups(loadUserGroups(userGroupMappers));
		
		Response response = new Response();
		response.setUpdateStaffResponse(updateStaffResponse);
		
		return response;
	}
	
	protected boolean validate(Request request) throws UpdateStaffException
	{
		UpdateStaffRequest updateStaffRequest = request.getUpdateStaffRequest();
		
		
		if(updateStaffRequest.getUserId() == null || updateStaffRequest.getUserId().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStaffRequest.userId' Couldn't Be Null OR Empty");
		}
		
		if(updateStaffRequest.getUsername() == null || updateStaffRequest.getUsername().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStaffRequest.username' Couldn't Be Null OR Empty");
		}
		
		if(updateStaffRequest.getEmail() == null || updateStaffRequest.getEmail().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStaffRequest.email' Couldn't Be Null OR Empty");
		}
		
		if(updateStaffRequest.getAccountName() == null || updateStaffRequest.getAccountName().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStaffRequest.accountName' Couldn't Be Null OR Empty");
		}
		
		return true;
	}
	
	protected boolean checkDuplicateUsername(UpdateStaffRequest updateStaffRequest) throws UpdateStaffException
	{
		User comparedUser = userRepository.findByUsername(updateStaffRequest.getUsername());
		if(comparedUser != null && !comparedUser.getId().equals(updateStaffRequest.getUserId())) {
			throw new UpdateStaffException.DuplicateUsername(updateStaffRequest.getUsername());
		}
		return true;
	}
	
	protected boolean checkDuplicateEmail(UpdateStaffRequest updateStaffRequest) throws UpdateStaffException
	{
		User comparedUser = userRepository.findByEmail(updateStaffRequest.getEmail());
		if(comparedUser != null && !comparedUser.getId().equals(updateStaffRequest.getUserId())) {
			throw new UpdateStaffException.DuplicateEmail(updateStaffRequest.getEmail());
		}
		return true;
	}
	
	protected List<UserRoleMapper> buildUserRoleMappers(Request request, User user)
	{
		List<String> roleIds = request.getUpdateStaffRequest().getRoleIds();
		
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
		List<String> userGroupIds = request.getUpdateStaffRequest().getUserGroupIds();
		
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
		protected UpdateStaffRequest updateStaffRequest;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected UpdateStaffResponse updateStaffResponse;
	}
}
