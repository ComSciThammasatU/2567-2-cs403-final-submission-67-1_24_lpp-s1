package com.otc.survey.modules.core.domain.service.staff;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.constant.UserTypeConst;
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
import com.otc.survey.modules.core.domain.service.StaffConfigService;
import com.otc.survey.modules.core.domain.service.staff.dto.CreateStaffRequest;
import com.otc.survey.modules.core.domain.service.staff.dto.CreateStaffResponse;
import com.otc.survey.modules.core.domain.service.staff.dto.StaffInfo;
import com.otc.survey.modules.core.domain.service.staff.exception.CreateStaffException;
import com.otc.survey.modules.core.domain.service.user.UserService;

import lombok.Data;

@ServiceCommand
public class CreateStaffCommand extends BaseServiceCommand
{
	@Autowired
	protected StaffService staffService;
	
	@Autowired
	protected StaffConfigService staffConfigService;
	
	@Autowired
	protected UserService userService;
	
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
	
	
	public Response createStaff(Request request) throws CreateStaffException
	{
		logger.info("### {}.createStaff ###", getClass().getSimpleName());
		
		validate(request);
		
		User user = buildUser(request);
		userRepository.save(user);
		logger.info("Create user => {}", user);
		
		List<UserRoleMapper> userRoleMappers = buildUserRoleMappers(request, user);
		userRoleMapperRepository.saveAll(userRoleMappers);
		logger.info("Create userRoleMappers => {}", userRoleMappers);
		
		List<UserGroupMapper> userGroupMappers = buildUserGroupMappers(request, user);
		if(userGroupMappers != null && !userGroupMappers.isEmpty()) {
			userGroupMapperRepository.saveAll(userGroupMappers);
			logger.info("Create userGroupMappers => {}", userGroupMappers);
		}
		
		StaffInfo staffInfo = new StaffInfo();
		staffInfo.setUser(user);
		staffInfo.setRoles(loadRoles(userRoleMappers)); 
		staffInfo.setUserGroups(loadUserGroups(userGroupMappers)); 
		
		CreateStaffResponse createStaffResponse = new CreateStaffResponse();
		createStaffResponse.setStaffInfo(staffInfo);
		
		staffService.notifyRegistration(req -> {
			req.copyFrom(request);
			req.setStaffInfo(staffInfo);
		});
		
		Response response = new Response();
		response.setCreateStaffResponse(createStaffResponse);
		
		return response;
	}
	
	protected boolean validate(Request request) throws CreateStaffException
	{
		CreateStaffRequest createStaffRequest = request.getCreateStaffRequest();
		
		if(createStaffRequest == null) {
			throw new IllegalArgumentException("Param 'createProfessorRequest' Couldn't Be Null");
		}
		
		if(createStaffRequest.getAccountName() == null || createStaffRequest.getAccountName().isBlank()) {
			throw new IllegalArgumentException("Param 'createProfessorRequest.accountName' Couldn't Be Null OR Empty");
		}
		
		if(createStaffRequest.getEmail() == null || createStaffRequest.getEmail().isBlank()) {
			throw new IllegalArgumentException("Param 'createProfessorRequest.email' Couldn't Be Null OR Empty");
		}
		
		if(createStaffRequest.getUsername() == null || createStaffRequest.getUsername().isBlank()) {
			throw new IllegalArgumentException("Param 'createProfessorRequest.username' Couldn't Be Null OR Empty");
		}
		
		/*
		if(createStaffRequest.getPassword() == null || createStaffRequest.getPassword().isBlank()) {
			throw new IllegalArgumentException("Param 'createProfessorRequest.password' Couldn't Be Null OR Empty");
		}
		*/
		
		if(userRepository.existsByUsername(createStaffRequest.getUsername())) {
			throw new CreateStaffException.DuplicateUsername(createStaffRequest.getUsername());
		}
		
		if(userRepository.existsByEmail(createStaffRequest.getEmail())) {
			throw new CreateStaffException.DuplicateEmail(createStaffRequest.getEmail());
		}
		
		return true;
	}
	
	protected StatusCode getInitialStatus(CreateStaffRequest createStaffRequest)
	{
		return createStaffRequest.getInitialStatus() != null ? createStaffRequest.getInitialStatus() : staffConfigService.getInitialCreateStatus();
	}
	
	protected User buildUser(Request request)
	{
		CreateStaffRequest createStaffRequest = request.getCreateStaffRequest();
		
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUsername(createStaffRequest.getUsername());
		user.setPassword(createStaffRequest.getPassword());
		user.setEmail(createStaffRequest.getEmail());
		user.setAccountName(createStaffRequest.getAccountName());
		user.setUserTypeId(UserTypeConst.ID_STAFF);
		user.setAccountLocked(false);
		user.setPasswordLocked(false);
		user.setForceChangePassword(false);
		user.setLoginFailTimes(0);
		user.setAccountActivatedAt(request.getPerformedAt());
		user.setAccountExpiredAt(null);
		user.setPasswordExpiredAt(null);
		user.setProfileImageId(null);
		user.setStatus(getInitialStatus(createStaffRequest));
		user.setCreatedBy(user.getId());
		user.setCreatedAt(request.getPerformedAt());
		
		if(user.getPassword() == null || user.getPassword().isBlank()) {
			String password = userService.generatePassword(req -> {
				req.copyFrom(request);
				req.setUser(user);
			}).getGeneratedPassword();
			
			user.setPassword(password);
		}
		
		return user;
	}
	
	protected List<UserRoleMapper> buildUserRoleMappers(Request request, User user)
	{
		List<String> roleIds = request.getCreateStaffRequest().getRoleIds();
		
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
				userRoleMapper.setStatus(getInitialStatus(request.getCreateStaffRequest()));
				userRoleMapper.setCreatedBy(user.getId());
				userRoleMapper.setCreatedAt(request.getPerformedAt());
				
				return userRoleMapper;
			})
			.toList();
	}
	
	protected List<UserGroupMapper> buildUserGroupMappers(Request request, User user)
	{
		List<String> userGroupIds = request.getCreateStaffRequest().getUserGroupIds();
		
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
				userGroupMapper.setStatus(getInitialStatus(request.getCreateStaffRequest()));
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
		protected CreateStaffRequest createStaffRequest;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected CreateStaffResponse createStaffResponse;
	}
}