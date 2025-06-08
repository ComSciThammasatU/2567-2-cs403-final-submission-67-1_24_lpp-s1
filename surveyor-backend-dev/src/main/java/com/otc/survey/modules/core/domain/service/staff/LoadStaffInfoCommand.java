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
import com.otc.survey.modules.core.domain.service.staff.dto.StaffInfo;
import com.otc.survey.modules.core.domain.service.staff.exception.LoadStaffInfoException;

import lombok.Data;

@ServiceCommand
public class LoadStaffInfoCommand extends BaseServiceCommand
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
	
	public Response loadStaffInfo(Request request) throws LoadStaffInfoException
	{
		logger.info("### {}.loadStaffInfo ###", getClass().getSimpleName());
		
		if(request.getStaffId() == null || request.getStaffId().isBlank()) {
			throw new IllegalArgumentException("Param 'staffId' couldn't be null or empty");
		}
		
		User user = userRepository.findById(request.getStaffId()).orElse(null);
		
		if(user == null) {
			throw new LoadStaffInfoException.UserNotFound(request.getStaffId());
		}
		
		StaffInfo staffInfo = new StaffInfo();
		staffInfo.setUser(user);
		staffInfo.setRoles(loadRoles(userRoleMapperRepository.findByUserId(user.getId())));
		staffInfo.setUserGroups(loadUserGroups(userGroupMapperRepository.findByUserId(user.getId())));
		
		Response response = new Response();
		response.setStaffInfo(staffInfo);
		
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
		protected String staffId;
	}
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected StaffInfo staffInfo;
	}
}
