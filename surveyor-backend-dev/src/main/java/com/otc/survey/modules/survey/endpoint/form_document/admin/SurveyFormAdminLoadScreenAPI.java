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
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateGroup;
import com.otc.survey.modules.survey.domain.repository.FormTemplateGroupRepository;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form/admin/load-screen")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_FORM
	}
)
public class SurveyFormAdminLoadScreenAPI extends AbstractJsonBodyAPI<SurveyFormAdminLoadScreenAPI.RequestMessage, SurveyFormAdminLoadScreenAPI.BodyResponseMessage>
{
	@Autowired
	protected FormTemplateGroupRepository formTemplateGroupRepository;
	
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		List<FormTemplateGroup> formTemplateGroups = formTemplateGroupRepository.findAll();
		List<FormTemplate> formTemplates = formTemplateRepository.findAll();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.formTemplateGroups(formTemplateGroups)
				.formTemplates(formTemplates)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected List<FormTemplateGroup> formTemplateGroups;
		protected List<FormTemplate> formTemplates;
	}
}