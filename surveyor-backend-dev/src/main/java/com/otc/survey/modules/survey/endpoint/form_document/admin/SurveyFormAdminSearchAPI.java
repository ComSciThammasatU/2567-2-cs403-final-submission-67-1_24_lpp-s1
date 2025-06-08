package com.otc.survey.modules.survey.endpoint.form_document.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.survey.domain.constant.FormDocumentStatus;
import com.otc.survey.modules.survey.domain.model.FormDocumentInfo;
import com.otc.survey.modules.survey.domain.service.form_document.FormDocumentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form/admin/search")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_FORM
	}
)
public class SurveyFormAdminSearchAPI extends AbstractJsonBodyAPI<SurveyFormAdminSearchAPI.RequestMessage, SurveyFormAdminSearchAPI.BodyResponseMessage>
{
	@Autowired
	protected FormDocumentService formDocumentService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if (requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if (requestMessage.getTemplateId() == null || requestMessage.getTemplateId().isBlank()) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'templateId' Couldn't Be Null OR Empty");
		}
		
		List<FormDocumentInfo> formDocumentInfos = formDocumentService.searchDocument(req -> {
			setupServiceRequest(request, req);
			req.setTemplateId(requestMessage.getTemplateId());
			req.setDocumentStatus(requestMessage.getDocumentStatus());
		}).getFormDocumentInfos();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.formDocumentInfos(formDocumentInfos)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected String templateId;
		protected FormDocumentStatus documentStatus;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected List<FormDocumentInfo> formDocumentInfos;
	}
}