package com.otc.survey.modules.core.endpoint.controller.professor.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.core.domain.service.professor.ProfessorService;
import com.otc.survey.modules.core.domain.service.professor.dto.SearchProfessorRequest;
import com.otc.survey.modules.core.domain.service.professor.dto.SearchProfessorResponse;
import com.otc.survey.modules.core.domain.service.professor.exception.SearchProfessorException;
import com.otc.survey.modules.core.domain.service.student.StudentService;
import com.otc.survey.modules.core.domain.service.student.dto.SearchStudentRequest;
import com.otc.survey.modules.core.domain.service.student.dto.SearchStudentResponse;
import com.otc.survey.modules.core.endpoint.controller.student.setup.StudentSetupSearchAPI;
import com.otc.survey.modules.core.endpoint.controller.student.setup.StudentSetupSearchAPI.BodyResponseMessage;
import com.otc.survey.modules.core.endpoint.controller.student.setup.StudentSetupSearchAPI.RequestMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/professor/setup/search")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_USER
	}
)
public class ProfessorSetupSearchAPI extends AbstractJsonBodyAPI<ProfessorSetupSearchAPI.RequestMessage, ProfessorSetupSearchAPI.BodyResponseMessage>
{
	@Autowired
	protected ProfessorService professorService;
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if(requestMessage.getSearchProfessorRequest() == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'searchStudentRequest' Couldn't Be Null");
		}
		
		SearchProfessorResponse searchProfessorResponse = new SearchProfessorResponse();
		try {
			searchProfessorResponse = professorService.searchProfessor(req -> {
				setupServiceRequest(request, req);
				req.setSearchProfessorRequest(requestMessage.getSearchProfessorRequest());
			}).getSearchProfessorResponse();
		} catch (SearchProfessorException e) {
			e.printStackTrace();
		}
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.searchProfessorResponse(searchProfessorResponse)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected SearchProfessorRequest searchProfessorRequest;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected SearchProfessorResponse searchProfessorResponse;
	}
}