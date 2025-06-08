package com.otc.survey.modules.core.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.entity.Role;

@Repository
public interface RoleRepository extends SurveyJpaRepository<Role, String>
{
	public List<Role> findByStatus(StatusCode status);
	
	public default List<Role> findAllActive()
	{
		return findByStatus(StatusCode.Active);
	}
}