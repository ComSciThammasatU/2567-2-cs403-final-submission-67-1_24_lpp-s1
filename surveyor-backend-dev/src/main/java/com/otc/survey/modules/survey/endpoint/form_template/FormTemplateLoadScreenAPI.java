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
import com.otc.survey.modules.core.domain.entity.UserGroup;
import com.otc.survey.modules.core.domain.repository.UserGroupRepository;
import com.otc.survey.modules.survey.domain.entity.FormTemplateGroup;
import com.otc.survey.modules.survey.domain.repository.FormTemplateGroupRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/survey/form-template/setup/load-screen")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_FORM
	}
)
public class FormTemplateLoadScreenAPI extends AbstractJsonBodyAPI<FormTemplateLoadScreenAPI.RequestMessage, FormTemplateLoadScreenAPI.BodyResponseMessage>
{
	@Autowired
	protected FormTemplateGroupRepository formTemplateGroupRepository;
	
	@Autowired
	protected UserGroupRepository userGroupRepository;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		List<FormTemplateGroup> formTemplateGroups = formTemplateGroupRepository.findAllActive();
		List<UserGroup> userGroups = userGroupRepository.findAllActive();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.formTemplateGroups(formTemplateGroups)
				.userGroups(userGroups)
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
		protected List<UserGroup> userGroups;
	}
}