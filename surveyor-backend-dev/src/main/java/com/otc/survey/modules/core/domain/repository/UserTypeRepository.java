package com.otc.survey.modules.core.domain.repository;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.entity.UserType;

@Repository
public interface UserTypeRepository extends SurveyJpaRepository<UserType, String>
{

}