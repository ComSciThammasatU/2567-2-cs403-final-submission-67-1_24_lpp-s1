package com.otc.survey.modules.survey.domain.repository;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.survey.domain.entity.FormElementType;

@Repository
public interface FormElementTypeRepository extends SurveyJpaRepository<FormElementType, String>
{

}