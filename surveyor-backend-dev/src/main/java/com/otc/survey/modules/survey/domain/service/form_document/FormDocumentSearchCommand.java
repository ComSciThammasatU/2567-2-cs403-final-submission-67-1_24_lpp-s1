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
public class FormDocumentSearchCommand extends BaseServiceCommand
{
	@Autowired
	protected FormDocumentService formDocumentService;
	
	@Autowired
	protected FormDocumentRepository formDocumentRepository;
	
	
	public Response searchDocument(Request request)
	{
		logger.info("### {}.searchDocument ###", getClass().getSimpleName());
		
		if(request.getTemplateId() == null || request.getTemplateId().isBlank()) {
			throw new IllegalArgumentException("Param 'templateId' couldn't be null or empty");
		}
		
		if(request.getDocumentStatus() == null) {
			request.setDocumentStatus(FormDocumentStatus.Completed);
		}
		
		List<FormDocument> formDocuments = formDocumentRepository.findByTemplateIdAndDocumentStatus(request.getTemplateId(), request.getDocumentStatus());
		
		List<FormDocumentInfo> formDocumentInfos = formDocumentService.buildDocumentInfo(req -> {
			req.copyFrom(request);
			req.setFormDocuments(formDocuments);
			req.setFetchSubmittedUser(true);
		}).getFormDocumentInfos();
		
		Response response = new Response();
		response.setFormDocumentInfos(formDocumentInfos);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected String templateId;
		protected FormDocumentStatus documentStatus;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected List<FormDocumentInfo> formDocumentInfos;
	}
}