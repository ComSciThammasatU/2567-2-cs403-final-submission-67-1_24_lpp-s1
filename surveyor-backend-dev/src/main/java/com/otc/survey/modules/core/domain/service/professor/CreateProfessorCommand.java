package com.otc.survey.modules.core.domain.service.professor;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.constant.RoleConst;
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
import com.otc.survey.modules.core.domain.service.ProfessorConfigService;
import com.otc.survey.modules.core.domain.service.professor.dto.CreateProfessorRequest;
import com.otc.survey.modules.core.domain.service.professor.dto.CreateProfessorResponse;
import com.otc.survey.modules.core.domain.service.professor.dto.ProfessorInfo;
import com.otc.survey.modules.core.domain.service.professor.exception.CreateProfessorException;
import com.otc.survey.modules.core.domain.service.user.UserService;

import lombok.Data;

@ServiceCommand
public class CreateProfessorCommand extends BaseServiceCommand 
{
	@Autowired
	protected ProfessorConfigService professorConfigService;
	
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
	
	public Response createProfessor(Request request) throws CreateProfessorException
	{
		logger.info("### {}.createProfessor ###", getClass().getSimpleName());
		
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
		
		ProfessorInfo professorInfo = new ProfessorInfo();
		professorInfo.setUser(user);
		professorInfo.setRoles(loadRoles(userRoleMappers)); // ???
		professorInfo.setUserGroups(loadUserGroups(userGroupMappers)); // ???
		
		CreateProfessorResponse createProfessorResponse = new CreateProfessorResponse();
		createProfessorResponse.setProfessorInfo(professorInfo);
		
		Response response = new Response();
		response.setCreateProfessorResponse(createProfessorResponse);
		
		return response;
	};
	
	protected boolean validate(Request request) throws CreateProfessorException
	{
		CreateProfessorRequest createProfessorRequest = request.getCreateProfessorRequest();
		
		if(createProfessorRequest == null) {
			throw new IllegalArgumentException("Param 'createProfessorRequest' Couldn't Be Null");
		}
		
		if(createProfessorRequest.getAccountName() == null || createProfessorRequest.getAccountName().isBlank()) {
			throw new IllegalArgumentException("Param 'createProfessorRequest.accountName' Couldn't Be Null OR Empty");
		}
		
		if(createProfessorRequest.getEmail() == null || createProfessorRequest.getEmail().isBlank()) {
			throw new IllegalArgumentException("Param 'createProfessorRequest.email' Couldn't Be Null OR Empty");
		}
		
		if(createProfessorRequest.getUsername() == null || createProfessorRequest.getUsername().isBlank()) {
			throw new IllegalArgumentException("Param 'createProfessorRequest.username' Couldn't Be Null OR Empty");
		}
		
//		if(createProfessorRequest.getPassword() == null || createProfessorRequest.getPassword().isBlank()) {
//			throw new IllegalArgumentException("Param 'createProfessorRequest.password' Couldn't Be Null OR Empty");
//		}
		
		if(userRepository.existsByUsername(createProfessorRequest.getUsername())) {
			throw new CreateProfessorException.DuplicateUsername(createProfessorRequest.getUsername());
		}
		
		if(userRepository.existsByEmail(createProfessorRequest.getEmail())) {
			throw new CreateProfessorException.DuplicateEmail(createProfessorRequest.getEmail());
		}
		
		return true;
	}
	
	protected StatusCode getInitialStatus(CreateProfessorRequest createProfessorRequest)
	{
		return createProfessorRequest.getInitialStatus() != null ? createProfessorRequest.getInitialStatus() : professorConfigService.getInitialCreateStatus();
	}
	
	protected User buildUser(Request request)
	{
		CreateProfessorRequest createProfessorRequest = request.getCreateProfessorRequest();
		
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUsername(createProfessorRequest.getUsername());
		user.setPassword(createProfessorRequest.getPassword());
		user.setEmail(createProfessorRequest.getEmail());
		user.setAccountName(createProfessorRequest.getAccountName());
		user.setUserTypeId(UserTypeConst.ID_PROFESSOR);
		user.setAccountLocked(false);
		user.setPasswordLocked(false);
		user.setForceChangePassword(false);
		user.setLoginFailTimes(0);
		user.setAccountActivatedAt(request.getPerformedAt());
		user.setAccountExpiredAt(null);
		user.setPasswordExpiredAt(null);
		user.setProfileImageId(null);
		user.setStatus(getInitialStatus(createProfessorRequest));
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
		UserRoleMapper endUserRoleMapper = new UserRoleMapper();
		endUserRoleMapper.setId(UUID.randomUUID().toString());
		endUserRoleMapper.setUserId(user.getId());
		endUserRoleMapper.setRoleId(RoleConst.ID_ADMIN_SYSTEM);
		endUserRoleMapper.setStatus(getInitialStatus(request.getCreateProfessorRequest()));
		endUserRoleMapper.setCreatedBy(user.getId());
		endUserRoleMapper.setCreatedAt(request.getPerformedAt());
		
		List<UserRoleMapper> userRoleMappers = List.of(
				endUserRoleMapper
			);
		
		return userRoleMappers;
	}
	
	protected List<UserGroupMapper> buildUserGroupMappers(Request request, User user)
	{
		List<String> userGroupIds = request.getCreateProfessorRequest().getUserGroupIds();
		
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
				userGroupMapper.setStatus(getInitialStatus(request.getCreateProfessorRequest()));
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
		protected CreateProfessorRequest createProfessorRequest;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected CreateProfessorResponse createProfessorResponse;
	}
}
