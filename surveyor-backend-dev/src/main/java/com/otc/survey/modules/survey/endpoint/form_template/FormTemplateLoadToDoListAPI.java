package com.otc.survey.modules.survey.endpoint.form_template;

import java.util.List;

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
@RequestMapping("/api/survey/form-template/to-do-list")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_USER,
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_FORM
	}
)
public class FormTemplateLoadToDoListAPI extends AbstractJsonBodyAPI<FormTemplateLoadToDoListAPI.RequestMessage, FormTemplateLoadToDoListAPI.BodyResponseMessage>
{
	@Autowired
	protected FormTemplateService formTemplateService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		/*
		if(requestMessage.getUserId() == null || requestMessage.getUserId().isBlank()) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'requestPayload.userId' Couldn't Be Null OR Empty");
		}
		*/
		
		if(requestMessage.getUserId() == null || requestMessage.getUserId().isBlank()) {
			requestMessage.setUserId(getUserProfile(request).getUser().getId());
		}
		
		List<FormTemplateInfo> formTemplateInfos = formTemplateService.loadToDoListTemplate(req -> {
			setupServiceRequest(request, req);
			req.setUserId(requestMessage.getUserId());
		}).getFormTemplateInfos();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.formTemplateInfos(formTemplateInfos)
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
		protected List<FormTemplateInfo> formTemplateInfos;
	}
}