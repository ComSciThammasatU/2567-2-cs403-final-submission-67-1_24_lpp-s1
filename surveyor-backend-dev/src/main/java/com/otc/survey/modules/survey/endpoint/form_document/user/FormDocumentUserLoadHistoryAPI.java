package com.otc.survey.modules.survey.endpoint.form_document.user;

import java.util.List;

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
@RequestMapping("/api/survey/form-document/user/history")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_USER
	}
)
public class FormDocumentUserLoadHistoryAPI extends AbstractJsonBodyAPI<FormDocumentUserLoadHistoryAPI.RequestMessage, FormDocumentUserLoadHistoryAPI.BodyResponseMessage>
{
	@Autowired
	protected FormDocumentService formDocumentService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if (requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if(requestMessage.getUserId() == null || requestMessage.getUserId().isBlank()) {
			requestMessage.setUserId(getUserProfile(request).getUser().getId());
		}
		
		List<FormDocumentInfo> formDocumentInfos = formDocumentService.loadUserHistoryDocument(req -> {
			setupServiceRequest(request, req);
			req.setUserId(requestMessage.getUserId());
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
		protected String userId;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected List<FormDocumentInfo> formDocumentInfos;
	}
}