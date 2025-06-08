package com.otc.survey.modules.survey.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.survey.domain.entity.FormDocumentElement;

@Repository
public interface FormDocumentElementRepository extends SurveyJpaRepository<FormDocumentElement, String>
{
	public FormDocumentElement findByDocumentIdAndTemplateElementId(String documentId, String templateElementId);
	
	public List<FormDocumentElement> findByDocumentId(String documentId);
	
	public List<FormDocumentElement> findByDocumentIdIn(Collection<String> documentIds);
	
	public List<FormDocumentElement> findByDocumentIdAndStatus(String documentId, StatusCode status);
	
	public default List<FormDocumentElement> findAllActiveByDocumentId(String documentId)
	{
		return findByDocumentIdAndStatus(documentId, StatusCode.Active);
	}
	
	public List<FormDocumentElement> findByTemplateId(String templateId);
	
	public List<FormDocumentElement> findByTemplateIdAndStatus(String templateId, StatusCode status);
	
	public default List<FormDocumentElement> findAllActiveByTemplateId(String templateId)
	{
		return findByTemplateIdAndStatus(templateId, StatusCode.Active);
	}
}