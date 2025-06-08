package com.otc.survey.modules.survey.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.survey.domain.entity.FormTemplateGroup;

@Repository
public interface FormTemplateGroupRepository extends SurveyJpaRepository<FormTemplateGroup, String>
{
	public List<FormTemplateGroup> findByStatus(StatusCode status);
	
	public default List<FormTemplateGroup> findAllActive()
	{
		return findByStatus(StatusCode.Active);
	}
}