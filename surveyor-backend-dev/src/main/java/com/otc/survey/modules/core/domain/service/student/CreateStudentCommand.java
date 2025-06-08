package com.otc.survey.modules.core.domain.service.student;

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
import com.otc.survey.modules.core.domain.entity.Student;
import com.otc.survey.modules.core.domain.entity.User;
import com.otc.survey.modules.core.domain.entity.UserGroup;
import com.otc.survey.modules.core.domain.entity.UserGroupMapper;
import com.otc.survey.modules.core.domain.entity.UserRoleMapper;
import com.otc.survey.modules.core.domain.repository.RoleRepository;
import com.otc.survey.modules.core.domain.repository.StudentRepository;
import com.otc.survey.modules.core.domain.repository.UserGroupMapperRepository;
import com.otc.survey.modules.core.domain.repository.UserGroupRepository;
import com.otc.survey.modules.core.domain.repository.UserRepository;
import com.otc.survey.modules.core.domain.repository.UserRoleMapperRepository;
import com.otc.survey.modules.core.domain.service.StudentConfigService;
import com.otc.survey.modules.core.domain.service.student.dto.CreateStudentRequest;
import com.otc.survey.modules.core.domain.service.student.dto.CreateStudentResponse;
import com.otc.survey.modules.core.domain.service.student.dto.StudentInfo;
import com.otc.survey.modules.core.domain.service.student.exception.CreateStudentException;

import lombok.Data;

@ServiceCommand
public class CreateStudentCommand extends BaseServiceCommand
{
	@Autowired
	protected StudentConfigService studentConfigService;
	
	@Autowired
	protected StudentRepository studentRepository;
	
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
	
	
	public Response createStudent(Request request) throws CreateStudentException
	{
		logger.info("### {}.createStudent ###", getClass().getSimpleName());
		
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
		
		Student student = buildStudent(request, user);
		studentRepository.save(student);
		logger.info("Create student => {}", student);
		
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setUser(user);
		studentInfo.setStudent(student);
		studentInfo.setRoles(loadRoles(userRoleMappers));
		studentInfo.setUserGroups(loadUserGroups(userGroupMappers));
		
		CreateStudentResponse createStudentResponse = new CreateStudentResponse();
		createStudentResponse.setStudentInfo(studentInfo);
		
		Response response = new Response();
		response.setCreateStudentResponse(createStudentResponse);
		
		return response;
	}
	
	protected boolean validate(Request request) throws CreateStudentException
	{
		CreateStudentRequest createStudentRequest = request.getCreateStudentRequest();
		
		if(createStudentRequest == null) {
			throw new IllegalArgumentException("Param 'createStudentRequest' Couldn't Be Null");
		}
		
		if(createStudentRequest.getStudentCode() == null || createStudentRequest.getStudentCode().isBlank()) {
			throw new IllegalArgumentException("Param 'createStudentRequest.studentCode' Couldn't Be Null OR Empty");
		}
		
		if(createStudentRequest.getAccountName() == null || createStudentRequest.getAccountName().isBlank()) {
			throw new IllegalArgumentException("Param 'createStudentRequest.accountName' Couldn't Be Null OR Empty");
		}
		
		if(createStudentRequest.getEmail() == null || createStudentRequest.getEmail().isBlank()) {
			throw new IllegalArgumentException("Param 'createStudentRequest.email' Couldn't Be Null OR Empty");
		}
		
		if(createStudentRequest.getUsername() == null || createStudentRequest.getUsername().isBlank()) {
			throw new IllegalArgumentException("Param 'createStudentRequest.username' Couldn't Be Null OR Empty");
		}
		
		if(createStudentRequest.getPassword() == null || createStudentRequest.getPassword().isBlank()) {
			throw new IllegalArgumentException("Param 'createStudentRequest.password' Couldn't Be Null OR Empty");
		}
		
		if(createStudentRequest.getStudyDegreeId() == null || createStudentRequest.getStudyDegreeId().isBlank()) {
			throw new IllegalArgumentException("Param 'createStudentRequest.studyDegreeId' Couldn't Be Null OR Empty");
		}
		
		if(createStudentRequest.getStudyClassLevel() == null || createStudentRequest.getStudyClassLevel() <= 0) {
			throw new IllegalArgumentException("Param 'createStudentRequest.studyClassLevel' Couldn't Be Null OR Empty");
		}
		
		if(studentRepository.existsByStudentCode(createStudentRequest.getStudentCode())) {
			throw new CreateStudentException.DuplicateStudentCode(createStudentRequest.getStudentCode());
		}
		
		if(userRepository.existsByUsername(createStudentRequest.getUsername())) {
			throw new CreateStudentException.DuplicateUsername(createStudentRequest.getUsername());
		}
		
		if(userRepository.existsByEmail(createStudentRequest.getEmail())) {
			throw new CreateStudentException.DuplicateEmail(createStudentRequest.getEmail());
		}
		
		return true;
	}
	
	protected StatusCode getInitialStatus(CreateStudentRequest createStudentRequest)
	{
		return createStudentRequest.getInitialStatus() != null ? createStudentRequest.getInitialStatus() : studentConfigService.getInitialCreateStatus();
	}
	
	protected User buildUser(Request request)
	{
		CreateStudentRequest createStudentRequest = request.getCreateStudentRequest();
		
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUsername(createStudentRequest.getUsername());
		user.setPassword(createStudentRequest.getPassword());
		user.setEmail(createStudentRequest.getEmail());
		user.setAccountName(createStudentRequest.getAccountName());
		user.setUserTypeId(UserTypeConst.ID_STUDENT);
		user.setAccountLocked(false);
		user.setPasswordLocked(false);
		user.setForceChangePassword(false);
		user.setLoginFailTimes(0);
		user.setAccountActivatedAt(request.getPerformedAt());
		user.setAccountExpiredAt(null);
		user.setPasswordExpiredAt(null);
		user.setProfileImageId(null);
		user.setStatus(getInitialStatus(createStudentRequest));
		user.setCreatedBy(user.getId());
		user.setCreatedAt(request.getPerformedAt());
		
		return user;
	}
	
	protected List<UserRoleMapper> buildUserRoleMappers(Request request, User user)
	{
		UserRoleMapper endUserRoleMapper = new UserRoleMapper();
		endUserRoleMapper.setId(UUID.randomUUID().toString());
		endUserRoleMapper.setUserId(user.getId());
		endUserRoleMapper.setRoleId(RoleConst.ID_USER);
		endUserRoleMapper.setStatus(getInitialStatus(request.getCreateStudentRequest()));
		endUserRoleMapper.setCreatedBy(user.getId());
		endUserRoleMapper.setCreatedAt(request.getPerformedAt());
		
		List<UserRoleMapper> userRoleMappers = List.of(
				endUserRoleMapper
			);
		
		return userRoleMappers;
	}
	
	protected List<UserGroupMapper> buildUserGroupMappers(Request request, User user)
	{
		List<String> userGroupIds = request.getCreateStudentRequest().getUserGroupIds();
		
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
				userGroupMapper.setStatus(getInitialStatus(request.getCreateStudentRequest()));
				userGroupMapper.setCreatedBy(user.getId());
				userGroupMapper.setCreatedAt(request.getPerformedAt());
				
				return userGroupMapper;
			})
			.toList();
	}
	
	protected Student buildStudent(Request request, User user)
	{
		CreateStudentRequest createStudentRequest = request.getCreateStudentRequest();
		
		Student student = new Student();
		student.setId(UUID.randomUUID().toString());
		student.setUserId(user.getId());
		student.setStudentCode(createStudentRequest.getStudentCode());
		student.setDegreeId(createStudentRequest.getStudyDegreeId());
		student.setClassLevel(createStudentRequest.getStudyClassLevel());
		student.setStatus(getInitialStatus(request.getCreateStudentRequest()));
		student.setCreatedBy(user.getId());
		student.setCreatedAt(request.getPerformedAt());
		
		return student;
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
		protected CreateStudentRequest createStudentRequest;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected CreateStudentResponse createStudentResponse;
	}
}