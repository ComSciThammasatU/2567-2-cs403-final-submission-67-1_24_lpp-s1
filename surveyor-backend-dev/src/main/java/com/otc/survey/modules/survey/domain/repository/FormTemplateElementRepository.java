package com.otc.survey.modules.survey.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.survey.domain.entity.FormTemplateElement;

@Repository
public interface FormTemplateElementRepository extends SurveyJpaRepository<FormTemplateElement, String>
{
	public FormTemplateElement findByTemplateIdAndElementCode(String templateId, String elementCode);
	
	public FormTemplateElement findByTemplateIdAndOrderNo(String templateId, int orderNo);
	
	public List<FormTemplateElement> findByTemplateId(String templateId);
	
	public List<FormTemplateElement> findByTemplateIdIn(Collection<String> templateIds);
	
	public List<FormTemplateElement> findByTemplateIdAndStatus(String templateId, StatusCode status);
	
	public default List<FormTemplateElement> findAllActiveByTemplateId(String templateId)
	{
		return findByTemplateIdAndStatus(templateId, StatusCode.Active);
	}
	
	public List<FormTemplateElement> removeByTemplateId(String templateId);
}