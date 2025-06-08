package com.otc.survey.modules.survey.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.survey.domain.constant.FormDocumentStatus;
import com.otc.survey.modules.survey.domain.entity.FormDocument;

@Repository
public interface FormDocumentRepository extends SurveyJpaRepository<FormDocument, String>
{
	public FormDocument findByDocumentNo(String documentNo);
	
	public FormDocument findByTemplateIdAndSubmittedUserId(String templateId, String submittedUserId);
	
	public List<FormDocument> findByTemplateId(String templateId);
	
	public List<FormDocument> findByTemplateIdAndDocumentStatus(String templateId, FormDocumentStatus documentStatus);
	
	public List<FormDocument> findBySubmittedUserId(String submittedUserId);
	
	public List<FormDocument> findBySubmittedUserIdAndDocumentStatus(String submittedUserId, FormDocumentStatus documentStatus);
}