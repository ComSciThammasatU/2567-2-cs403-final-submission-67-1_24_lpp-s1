package com.otc.survey.modules.core.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.entity.UserGroupMapper;

@Repository
public interface UserGroupMapperRepository extends SurveyJpaRepository<UserGroupMapper, String>
{
	public UserGroupMapper findByUserIdAndGroupId(String userId, String groupId);
	
	public List<UserGroupMapper> findByUserIdIn(Collection<String> userIds);
	
	public List<UserGroupMapper> findByUserId(String userId);
	public List<UserGroupMapper> findByUserIdAndStatus(String userId, StatusCode status);
	public default List<UserGroupMapper> findAllActiveByUserId(String userId)
	{
		return findByUserIdAndStatus(userId, StatusCode.Active);
	}
	
	public List<UserGroupMapper> findByGroupId(String groupId);
	public List<UserGroupMapper> findByGroupIdAndStatus(String groupId, StatusCode status);
	public default List<UserGroupMapper> findAllActiveByGroupId(String groupId)
	{
		return findByGroupIdAndStatus(groupId, StatusCode.Active);
	}
	
	
	public List<UserGroupMapper> findByGroupIdIn(Collection<String> groupIds);
	public List<UserGroupMapper> findByGroupIdInAndStatus(Collection<String> groupIds, StatusCode status);
	public default List<UserGroupMapper> findAllActiveByGroupIds(Collection<String> groupIds)
	{
		return findByGroupIdInAndStatus(groupIds, StatusCode.Active);
	}
	
	
	public List<UserGroupMapper> removeByUserId(String userId);
}