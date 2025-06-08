package com.otc.survey.modules.core.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.entity.UserRoleMapper;

@Repository
public interface UserRoleMapperRepository extends SurveyJpaRepository<UserRoleMapper, String>
{
	public UserRoleMapper findByUserIdAndRoleId(String userId, String roleId);
	
	public List<UserRoleMapper> findByUserIdIn(Collection<String> userIds);
	
	public List<UserRoleMapper> findByUserId(String userId);
	public List<UserRoleMapper> findByUserIdAndStatus(String userId, StatusCode status);
	public default List<UserRoleMapper> findAllActiveByUserId(String userId)
	{
		return findByUserIdAndStatus(userId, StatusCode.Active);
	}
	
	public List<UserRoleMapper> findByRoleId(String roleId);
	public List<UserRoleMapper> findByRoleIdAndStatus(String roleId, StatusCode status);
	public default List<UserRoleMapper> findAllActiveByRoleId(String roleId)
	{
		return findByRoleIdAndStatus(roleId, StatusCode.Active);
	}
	
	public List<UserRoleMapper> removeByUserId(String userId);
}