package com.otc.survey.modules.core.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.entity.AuthSession;

@Repository
public interface AuthSessionRepository extends SurveyJpaRepository<AuthSession, String>
{
	public List<AuthSession> findByUserId(String userId);
	
	public AuthSession findByAccessToken(String accessToken);
}