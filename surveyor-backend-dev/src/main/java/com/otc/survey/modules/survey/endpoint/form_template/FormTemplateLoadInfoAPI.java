package com.otc.survey.modules.survey.endpoint.form_template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.service.form_template.FormTemplateService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form-template/load-info")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_USER,
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_FORM
	}
)
public class FormTemplateLoadInfoAPI extends AbstractJsonBodyAPI<FormTemplateLoadInfoAPI.RequestMessage, FormTemplateLoadInfoAPI.BodyResponseMessage>
{
	@Autowired
	protected FormTemplateService formTemplateService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if(requestMessage.getFormTemplateId() == null || requestMessage.getFormTemplateId().isBlank()) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'requestPayload.formTemplateId' Couldn't Be Null OR Empty");
		}
		
		FormTemplateInfo formTemplateInfo = formTemplateService.loadFormTemplateInfo(req -> {
			setupServiceRequest(request, req);
			req.setFormTemplateId(requestMessage.getFormTemplateId());
		}).getFormTemplateInfo();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.formTemplateInfo(formTemplateInfo)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected String formTemplateId;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected FormTemplateInfo formTemplateInfo;
	}
}