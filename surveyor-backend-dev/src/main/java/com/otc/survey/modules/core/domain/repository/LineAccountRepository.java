package com.otc.survey.modules.core.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.entity.LineAccount;

@Repository
public interface LineAccountRepository extends SurveyJpaRepository<LineAccount, String>
{
	public LineAccount findByUserId(String userId);
	public boolean existsByUserId(String userId);
	
	public List<LineAccount> findByUserIdIn(Collection<String> userIds);
	
	public LineAccount findByLineUserId(String lineUserId);
	public boolean existsByLineUserId(String lineUserId);
}