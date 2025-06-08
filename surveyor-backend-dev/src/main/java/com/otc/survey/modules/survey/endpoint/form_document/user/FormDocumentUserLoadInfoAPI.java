package com.otc.survey.modules.survey.endpoint.form_document.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.survey.domain.model.FormDocumentInfo;
import com.otc.survey.modules.survey.domain.service.form_document.FormDocumentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form-document/user/load-info")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_USER
	}
)
public class FormDocumentUserLoadInfoAPI extends AbstractJsonBodyAPI<FormDocumentUserLoadInfoAPI.RequestMessage, FormDocumentUserLoadInfoAPI.BodyResponseMessage>
{
	@Autowired
	protected FormDocumentService formDocumentService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if (requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if (requestMessage.getFormDocumentId() == null || requestMessage.getFormDocumentId().isBlank()) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'formDocumentId' Couldn't Be Null OR Empty");
		}
		
		FormDocumentInfo formDocumentInfo = formDocumentService.loadDocumentInfo(req -> {
			setupServiceRequest(request, req);
			req.setFormDocumentId(requestMessage.getFormDocumentId());
		}).getFormDocumentInfo();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.formDocumentInfo(formDocumentInfo)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected String formDocumentId;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected FormDocumentInfo formDocumentInfo;
	}
}