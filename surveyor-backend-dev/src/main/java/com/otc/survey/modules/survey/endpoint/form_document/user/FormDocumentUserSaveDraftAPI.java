package com.otc.survey.modules.survey.endpoint.form_document.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form-document/user/save-draft")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_USER
	}
)
public class FormDocumentUserSaveDraftAPI extends AbstractJsonBodyAPI<FormDocumentUserSaveDraftAPI.RequestMessage, FormDocumentUserSaveDraftAPI.BodyResponseMessage>
{
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		
	}
}