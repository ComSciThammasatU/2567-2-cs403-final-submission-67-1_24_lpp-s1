package com.otc.survey.modules.survey.endpoint.form_template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.survey.domain.service.form_template.FormTemplateService;
import com.otc.survey.modules.survey.domain.service.form_template.dto.FormTemplateCreateDTO;
import com.otc.survey.modules.survey.domain.service.form_template.exception.FormTemplateCreateException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form-template/setup/create")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_FORM
	}
)
public class FormTemplateCreateAPI extends AbstractJsonBodyAPI<FormTemplateCreateAPI.RequestMessage, FormTemplateCreateAPI.BodyResponseMessage>
{
	@Autowired
	protected FormTemplateService formTemplateService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if (requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}

		FormTemplateCreateDTO.RequestPayload requestPayload = requestMessage.getRequestPayload();

		if (requestPayload == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'requestPayload' Couldn't Be Null");
		}
		
		try {
			FormTemplateCreateDTO.ResponsePayload responsePayload = formTemplateService.createFormTemplate(req -> {
				setupServiceRequest(request, req);
				req.setRequestPayload(requestPayload);
			}).getResponsePayload();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.responsePayload(responsePayload)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
		} catch (FormTemplateCreateException ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "400199", ex.getErrorTitle(), ex.getErrorMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "500999", ex.getClass().getSimpleName(), ex.getMessage());
		}
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected FormTemplateCreateDTO.RequestPayload requestPayload;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected FormTemplateCreateDTO.ResponsePayload responsePayload;
	}
}