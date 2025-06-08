package com.otc.survey.modules.core.domain.service.student;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
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
import com.otc.survey.modules.core.domain.service.student.dto.UpdateStudentRequest;
import com.otc.survey.modules.core.domain.service.student.dto.UpdateStudentResponse;
import com.otc.survey.modules.core.domain.service.student.exception.UpdateStudentException;

import lombok.Data;

@ServiceCommand
public class UpdateStudentCommand extends BaseServiceCommand
{
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
	
	
	public Response updateStudent(Request request) throws UpdateStudentException
	{
		logger.info("### {}.updateStudent ###", getClass().getSimpleName());
		
		validate(request);
		
		UpdateStudentRequest updateStudentRequest = request.getUpdateStudentRequest();
		
		Student student = studentRepository.findById(updateStudentRequest.getStudentId()).orElse(null);
		
		if(student == null) {
			throw new UpdateStudentException.StudentNotFound(updateStudentRequest.getStudentId());
		}
		
		User user = userRepository.findById(updateStudentRequest.getUserId()).orElse(null);
		
		if(user == null) {
			throw new UpdateStudentException.UserNotFound(updateStudentRequest.getUserId());
		}
		
		checkDuplicateStudentCode(updateStudentRequest);
		
		checkDuplicateUsername(updateStudentRequest);
		
		checkDuplicateEmail(updateStudentRequest);
		
		student.setStudentCode(updateStudentRequest.getStudentCode());
		student.setDegreeId(updateStudentRequest.getStudyDegreeId());
		student.setClassLevel(updateStudentRequest.getStudyClassLevel());
		student.setStatus(updateStudentRequest.getStatus());
		student.setLastModifiedBy(request.getPerformedBy());
		student.setLastModifiedAt(request.getPerformedAt());
		studentRepository.save(student);
		logger.info("Update student => {}", student);
		
		user.setUsername(updateStudentRequest.getUsername());
		user.setEmail(updateStudentRequest.getEmail());
		user.setAccountName(updateStudentRequest.getAccountName());
		user.setStatus(updateStudentRequest.getStatus());
		user.setLastModifiedBy(request.getPerformedBy());
		user.setLastModifiedAt(request.getPerformedAt());
		userRepository.save(user);
		logger.info("Update user => {}", user);
		
		userGroupMapperRepository.removeByUserId(user.getId());
		userGroupMapperRepository.flush();
		logger.info("Remove userGroupMappers by user id [{}]", user.getId());
		
		List<UserGroupMapper> userGroupMappers = buildUserGroupMappers(request, user);
		if(userGroupMappers != null && !userGroupMappers.isEmpty()) {
			userGroupMapperRepository.saveAll(userGroupMappers);
			logger.info("Create userGroupMappers => {}", userGroupMappers);
		}
		
		UpdateStudentResponse updateStudentResponse = new UpdateStudentResponse();
		updateStudentResponse.setStudent(student);
		updateStudentResponse.setUser(user);
		updateStudentResponse.setRoles(loadRoles(userRoleMapperRepository.findByUserId(user.getId())));
		updateStudentResponse.setUserGroups(loadUserGroups(userGroupMappers));
		
		Response response = new Response();
		response.setUpdateStudentResponse(updateStudentResponse);
		
		return response;
	}
	
	protected boolean validate(Request request) throws UpdateStudentException
	{
		UpdateStudentRequest updateStudentRequest = request.getUpdateStudentRequest();
		
		if(updateStudentRequest == null) {
			throw new IllegalArgumentException("Param 'updateStudentRequest' Couldn't Be Null");
		}
		
		if(updateStudentRequest.getStudentId() == null || updateStudentRequest.getStudentId().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStudentRequest.studentId' Couldn't Be Null OR Empty");
		}
		
		if(updateStudentRequest.getStudentCode() == null || updateStudentRequest.getStudentCode().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStudentRequest.studentCode' Couldn't Be Null OR Empty");
		}
		
		if(updateStudentRequest.getStudyDegreeId() == null || updateStudentRequest.getStudyDegreeId().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStudentRequest.studyDegreeId' Couldn't Be Null OR Empty");
		}
		
		if(updateStudentRequest.getStudyClassLevel() == null || updateStudentRequest.getStudyClassLevel() <= 0) {
			throw new IllegalArgumentException("Param 'updateStudentRequest.studyClassLevel' Couldn't Be Null OR Empty");
		}
		
		if(updateStudentRequest.getUserId() == null || updateStudentRequest.getUserId().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStudentRequest.userId' Couldn't Be Null OR Empty");
		}
		
		if(updateStudentRequest.getUsername() == null || updateStudentRequest.getUsername().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStudentRequest.username' Couldn't Be Null OR Empty");
		}
		
		if(updateStudentRequest.getEmail() == null || updateStudentRequest.getEmail().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStudentRequest.email' Couldn't Be Null OR Empty");
		}
		
		if(updateStudentRequest.getAccountName() == null || updateStudentRequest.getAccountName().isBlank()) {
			throw new IllegalArgumentException("Param 'updateStudentRequest.accountName' Couldn't Be Null OR Empty");
		}
		
		return true;
	}
	
	protected boolean checkDuplicateStudentCode(UpdateStudentRequest updateStudentRequest) throws UpdateStudentException
	{
		Student comparedStudent = studentRepository.findByStudentCode(updateStudentRequest.getStudentCode());
		if(comparedStudent != null && !comparedStudent.getId().equals(updateStudentRequest.getStudentId())) {
			throw new UpdateStudentException.DuplicateStudentCode(updateStudentRequest.getStudentCode());
		}
		return true;
	}
	
	protected boolean checkDuplicateUsername(UpdateStudentRequest updateStudentRequest) throws UpdateStudentException
	{
		User comparedUser = userRepository.findByUsername(updateStudentRequest.getUsername());
		if(comparedUser != null && !comparedUser.getId().equals(updateStudentRequest.getUserId())) {
			throw new UpdateStudentException.DuplicateUsername(updateStudentRequest.getUsername());
		}
		return true;
	}
	
	protected boolean checkDuplicateEmail(UpdateStudentRequest updateStudentRequest) throws UpdateStudentException
	{
		User comparedUser = userRepository.findByEmail(updateStudentRequest.getEmail());
		if(comparedUser != null && !comparedUser.getId().equals(updateStudentRequest.getUserId())) {
			throw new UpdateStudentException.DuplicateEmail(updateStudentRequest.getEmail());
		}
		return true;
	}
	
	protected List<UserGroupMapper> buildUserGroupMappers(Request request, User user)
	{
		List<String> userGroupIds = request.getUpdateStudentRequest().getUserGroupIds();
		
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
		protected UpdateStudentRequest updateStudentRequest;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected UpdateStudentResponse updateStudentResponse;
	}
}