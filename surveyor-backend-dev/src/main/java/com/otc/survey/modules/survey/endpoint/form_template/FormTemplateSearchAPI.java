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
import com.otc.survey.modules.survey.domain.service.form_template.dto.FormTemplateSearchDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form-template/setup/search")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_FORM
	}
)
public class FormTemplateSearchAPI extends AbstractJsonBodyAPI<FormTemplateSearchAPI.RequestMessage, FormTemplateSearchAPI.BodyResponseMessage>
{
	@Autowired
	protected FormTemplateService formTemplateService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if(requestMessage.getRequestPayload() == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'requestPayload' Couldn't Be Null");
		}
		
		try {
			FormTemplateSearchDTO.ResponsePayload formTemplateSearchResponse = formTemplateService.searchFormTemplate(req -> {
				setupServiceRequest(request, req);
				req.setRequestPayload(requestMessage.getRequestPayload());
			}).getResponsePayload();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.responsePayload(formTemplateSearchResponse)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "500999", ex.getClass().getSimpleName(), ex.getMessage());
		}
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected FormTemplateSearchDTO.RequestPayload requestPayload;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected FormTemplateSearchDTO.ResponsePayload responsePayload;
	}
}