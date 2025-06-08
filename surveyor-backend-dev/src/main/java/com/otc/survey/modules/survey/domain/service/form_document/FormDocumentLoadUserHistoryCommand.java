package com.otc.survey.modules.survey.domain.service.form_document;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.survey.domain.constant.FormDocumentStatus;
import com.otc.survey.modules.survey.domain.entity.FormDocument;
import com.otc.survey.modules.survey.domain.model.FormDocumentInfo;
import com.otc.survey.modules.survey.domain.repository.FormDocumentRepository;

import lombok.Data;

@ServiceCommand
public class FormDocumentLoadUserHistoryCommand extends BaseServiceCommand
{
	@Autowired
	protected FormDocumentService formDocumentService;
	
	@Autowired
	protected FormDocumentRepository formDocumentRepository;
	
	
	public Response loadUserHistoryDocument(Request request)
	{
		logger.info("### {}.loadUserHistoryDocument ###", getClass().getSimpleName());
		
		if(request.getUserId() == null || request.getUserId().isBlank()) {
			throw new IllegalArgumentException("Param 'userId' couldn't be null or empty");
		}
		
		List<FormDocument> formDocuments = formDocumentRepository.findBySubmittedUserIdAndDocumentStatus(request.getUserId(), FormDocumentStatus.Completed);
		
		List<FormDocumentInfo> formDocumentInfos = formDocumentService.buildDocumentInfo(req -> {
			req.copyFrom(request);
			req.setFormDocuments(formDocuments);
		}).getFormDocumentInfos();
		
		Response response = new Response();
		response.setFormDocumentInfos(formDocumentInfos);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String userId;
		protected List<String> templateIds;
		protected List<FormDocumentStatus> documentStatuses;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected List<FormDocumentInfo> formDocumentInfos;
	}
}