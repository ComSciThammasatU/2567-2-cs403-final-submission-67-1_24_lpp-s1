package com.otc.survey.modules.core.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.entity.UserGroup;

@Repository
public interface UserGroupRepository extends SurveyJpaRepository<UserGroup, String>
{
	public List<UserGroup> findByStatus(StatusCode status);
	
	public default List<UserGroup> findAllActive()
	{
		return findByStatus(StatusCode.Active);
	}
}