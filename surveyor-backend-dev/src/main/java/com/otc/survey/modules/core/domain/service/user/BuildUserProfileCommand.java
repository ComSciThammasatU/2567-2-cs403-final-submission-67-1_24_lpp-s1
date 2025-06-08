package com.otc.survey.modules.core.domain.service.user;

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
import com.otc.survey.modules.core.domain.exception.SurveyInvalidArgumentException;
import com.otc.survey.modules.core.domain.model.UserProfile;
import com.otc.survey.modules.core.domain.repository.RoleRepository;
import com.otc.survey.modules.core.domain.repository.UserGroupMapperRepository;
import com.otc.survey.modules.core.domain.repository.UserGroupRepository;
import com.otc.survey.modules.core.domain.repository.UserRepository;
import com.otc.survey.modules.core.domain.repository.UserRoleMapperRepository;
import com.otc.survey.modules.core.domain.repository.UserTypeRepository;
import com.otc.survey.modules.core.domain.service.user.exception.UserException;

import lombok.Data;

@ServiceCommand
public class BuildUserProfileCommand extends BaseServiceCommand
{
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected UserTypeRepository userTypeRepository;
	
	@Autowired
	protected UserGroupRepository userGroupRepository;
	
	@Autowired
	protected UserGroupMapperRepository userGroupMapperRepository;
	
	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected UserRoleMapperRepository userRoleMapperRepository;
	
	
	public Response buildUserProfile(Request request) throws UserException
	{
		logger.info("### {}.buildUserProfile ###", getClass().getSimpleName());
		
		User user = request.getUser();
		
		if((request.getUserId() == null || request.getUserId().isBlank()) && user == null) {
			throw new SurveyInvalidArgumentException("400001", "Invalid Argument", "Argument 'userId' couldn't be null or empty");
		}
		
		if(user == null) {
			user = userRepository.findById(request.getUserId()).orElse(null);
		}
		
		if(user == null) {
			throw new UserException.UserNotFound(request.getUserId());
		}
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUser(user);
		userProfile.setUserType(userTypeRepository.findById(user.getUserTypeId()).orElse(null));
		userProfile.setUserGroups(loadUserGroups(user));
		userProfile.setRoles(loadRoles(user));
		
		Response response = new Response();
		response.setUserProfile(userProfile);
		
		return response;
	}
	
	protected List<UserGroup> loadUserGroups(User user)
	{
		List<UserGroupMapper> userGroupMappers = userGroupMapperRepository.findAllActiveByUserId(user.getId());
		
		if(userGroupMappers == null || userGroupMappers.isEmpty()) {
			return null;
		}
		
		List<String> groupIds = userGroupMappers
				.stream()
				.filter(e -> e != null && e.getId() != null)
				.map(e -> e.getGroupId())
				.distinct()
				.toList();
		
		if(groupIds == null || groupIds.isEmpty()) {
			return null;
		}
		
		return userGroupRepository.findAllById(groupIds);
	}
	
	protected List<Role> loadRoles(User user)
	{
		List<UserRoleMapper> userRoleMappers = userRoleMapperRepository.findAllActiveByUserId(user.getId());
		
		if(userRoleMappers == null || userRoleMappers.isEmpty()) {
			return null;
		}
		
		List<String> roleIds = userRoleMappers
				.stream()
				.filter(e -> e != null && e.getId() != null)
				.map(e -> e.getRoleId())
				.distinct()
				.toList();
		
		if(roleIds == null || roleIds.isEmpty()) {
			return null;
		}
		
		return roleRepository.findAllById(roleIds);
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String userId;
		protected User user;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected UserProfile userProfile;
	}
}