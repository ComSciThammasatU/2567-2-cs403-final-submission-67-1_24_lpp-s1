package com.otc.survey.modules.survey.endpoint.form_document.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.survey.domain.service.form_document.FormDocumentService;
import com.otc.survey.modules.survey.domain.service.form_document.dto.FormDocumentSubmitDTO;
import com.otc.survey.modules.survey.domain.service.form_document.exception.FormDocumentSubmitException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form-document/user/submit")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_USER
	}
)
public class FormDocumentUserSubmitAPI extends AbstractJsonBodyAPI<FormDocumentUserSubmitAPI.RequestMessage, FormDocumentUserSubmitAPI.BodyResponseMessage>
{
	@Autowired
	protected FormDocumentService formDocumentService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if (requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		FormDocumentSubmitDTO.RequestPayload requestPayload = requestMessage.getRequestPayload();
		
		if (requestPayload == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'requestPayload' Couldn't Be Null");
		}
		
		if(requestPayload.getUserId() == null || requestPayload.getUserId().isBlank()) {
			requestPayload.setUserId(getUserProfile(request).getUser().getId());
		}
		
		try {
			
			FormDocumentSubmitDTO.ResponsePayload responsePayload = formDocumentService.submitDocument(req -> {
				setupServiceRequest(request, req);
				req.setRequestPayload(requestPayload);
			}).getResponsePayload();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.responsePayload(responsePayload)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
			
		} catch(FormDocumentSubmitException ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "400199", ex.getErrorTitle(), ex.getErrorMessage());
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "500999", ex.getClass().getSimpleName(), ex.getMessage());
		}
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected FormDocumentSubmitDTO.RequestPayload requestPayload;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected FormDocumentSubmitDTO.ResponsePayload responsePayload;
	}
}