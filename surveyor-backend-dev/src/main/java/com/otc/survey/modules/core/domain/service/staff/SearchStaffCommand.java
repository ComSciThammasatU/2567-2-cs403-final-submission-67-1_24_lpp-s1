package com.otc.survey.modules.core.domain.service.staff;

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
import com.otc.survey.modules.core.domain.service.staff.dto.SearchStaffRequest;
import com.otc.survey.modules.core.domain.service.staff.dto.SearchStaffResponse;
import com.otc.survey.modules.core.domain.service.staff.dto.StaffInfo;
import com.otc.survey.modules.core.domain.service.staff.exception.SearchStaffException;

import lombok.Data;

@ServiceCommand
public class SearchStaffCommand extends BaseServiceCommand
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
	
	public Response searchStaff(Request request) throws SearchStaffException
	{
		logger.info("### {}.searchStaff ###", getClass().getSimpleName());
		
		List<Role> roles = roleRepository.findAll();
		List<UserGroup> userGroups = userGroupRepository.findAll();
		
		List<User> users = userRepository.findAllStaffs();
		List<UserRoleMapper> userRoleMappers = loadUserRoleMappers(users);
		List<UserGroupMapper> userGroupMappers = loadUserGroupMappers(users);
		
		List<StaffInfo> staffInfos = users
				.stream()
				.map(user -> {
					StaffInfo staffInfo = new StaffInfo();
					staffInfo.setUser(user);
					staffInfo.setRoles(getRolesByUser(roles, userRoleMappers, user));
					staffInfo.setUserGroups(getUserGroupsByUser(userGroups, userGroupMappers, user));
					
					return staffInfo;
				})
				.toList();
		
		
		SearchStaffResponse searchStaffResponse = new SearchStaffResponse();
		searchStaffResponse.setStaffInfos(staffInfos);
		
		Response response = new Response();
		response.setSearchStaffResponse(searchStaffResponse);
		
		return response;
	}
	
	protected List<UserRoleMapper> loadUserRoleMappers(List<User> users)
	{
		if(users == null || users.isEmpty()) {
			return null;
		}
		
		List<String> userIds = users
				.stream()
				.filter(e -> e != null && e.getId() != null && !e.getId().isBlank())
				.map(e -> e.getId())
				.distinct()
				.toList();
		
		if(userIds == null || userIds.isEmpty()) {
			return null;
		}
		
		return userRoleMapperRepository.findByUserIdIn(userIds);
	}
	
	protected List<UserGroupMapper> loadUserGroupMappers(List<User> users)
	{
		if(users == null || users.isEmpty()) {
			return null;
		}
		
		List<String> userIds = users
				.stream()
				.filter(e -> e != null && e.getId() != null && !e.getId().isBlank())
				.map(e -> e.getId())
				.distinct()
				.toList();
		
		if(userIds == null || userIds.isEmpty()) {
			return null;
		}
		
		return userGroupMapperRepository.findByUserIdIn(userIds);
	}
	
	protected List<Role> getRolesByUser(List<Role> roles, List<UserRoleMapper> userRoleMappers, User user)
	{
		if(roles == null || roles.isEmpty()) {
			return null;
		}
		
		if(userRoleMappers == null || userRoleMappers.isEmpty()) {
			return null;
		}
		
		if(user == null || user.getId() == null || user.getId().isBlank()) {
			return null;
		}
		
		List<String> filteredRoleIds = userRoleMappers
				.stream()
				.filter(e -> e.getUserId().equals(user.getId()))
				.map(e -> e.getRoleId())
				.distinct()
				.toList();
		
		if(filteredRoleIds == null || filteredRoleIds.isEmpty()) {
			return null;
		}
		
		return roles
				.stream()
				.filter(e -> filteredRoleIds.contains(e.getId()))
				.distinct()
				.toList();
	}
	
	protected List<UserGroup> getUserGroupsByUser(List<UserGroup> userGroups, List<UserGroupMapper> userGroupMappers, User user)
	{
		if(userGroups == null || userGroups.isEmpty()) {
			return null;
		}
		
		if(userGroupMappers == null || userGroupMappers.isEmpty()) {
			return null;
		}
		
		if(user == null || user.getId() == null || user.getId().isBlank()) {
			return null;
		}
		
		List<String> filteredGroupIds = userGroupMappers
				.stream()
				.filter(e -> e.getUserId().equals(user.getId()))
				.map(e -> e.getGroupId())
				.distinct()
				.toList();
		
		if(filteredGroupIds == null || filteredGroupIds.isEmpty()) {
			return null;
		}
		
		return userGroups
				.stream()
				.filter(e -> filteredGroupIds.contains(e.getId()))
				.distinct()
				.toList();
	}
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected SearchStaffRequest searchStaffRequest;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected SearchStaffResponse searchStaffResponse;
	}
}
