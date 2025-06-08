package com.otc.survey.modules.survey.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.constant.Permission;
import com.otc.survey.modules.core.domain.constant.PrincipalType;
import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;

@Repository
public interface FormTemplateAuthorityRepository extends SurveyJpaRepository<FormTemplateAuthority, String>
{
	public FormTemplateAuthority findByTemplateIdAndPrincipalTypeAndPrincipalRefId(String templateId, PrincipalType principalType, String principalRefId);
	
	public List<FormTemplateAuthority> findByTemplateId(String templateId);
	
	public List<FormTemplateAuthority> findByTemplateIdIn(Collection<String> templateIds);
	
	public List<FormTemplateAuthority> findByTemplateIdAndStatus(String templateId, StatusCode status);
	
	public default List<FormTemplateAuthority> findAllActiveByTemplateId(String templateId)
	{
		return findByTemplateIdAndStatus(templateId, StatusCode.Active);
	}
	
	public List<FormTemplateAuthority> findByTemplateIdAndPermissionAndStatus(String templateId, Permission permission, StatusCode status);
	
	public default List<FormTemplateAuthority> findAllGrantedActiveByTemplateId(String templateId)
	{
		return findByTemplateIdAndPermissionAndStatus(templateId, Permission.Granted, StatusCode.Active);
	}
	
	@Query("FROM FormTemplateAuthority e WHERE e.principalType = 'UserGroup' AND e.principalRefId in (:groupIds) AND e.status = 'Active'")
	public List<FormTemplateAuthority> findAllActiveByGroupIds(@Param("groupIds") Collection<String> groupIds);
}