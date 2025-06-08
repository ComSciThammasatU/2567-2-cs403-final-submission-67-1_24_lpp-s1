package com.otc.survey.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.otc.survey.modules.common.domain.constant.ApplicationConfigConst;
import com.otc.survey.modules.common.domain.repository.DefaultSurveyJpaRepository;

@Configuration
@EnableJpaRepositories(
	basePackages = { ApplicationConfigConst.BASE_PACKAGE_NAME }, 
	repositoryBaseClass = DefaultSurveyJpaRepository.class
)
public class SurveyRepositoryConfig 
{

}