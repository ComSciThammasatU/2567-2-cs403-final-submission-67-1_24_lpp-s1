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
import com.otc.survey.modules.core.domain.service.professor.dto.ProfessorInfo;
import com.otc.survey.modules.core.domain.service.professor.exception.LoadProfessorInfoException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/professor/setup/load-info")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_USER
	}
)
public class ProfessorSetupLoadInfoAPI extends AbstractJsonBodyAPI<ProfessorSetupLoadInfoAPI.RequestMessage, ProfessorSetupLoadInfoAPI.BodyResponseMessage>
{
	@Autowired
	protected ProfessorService professorService;
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if(requestMessage.getProfessorId() == null || requestMessage.getProfessorId().isBlank()) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'studentId' Couldn't Be Null OR Empty");
		}
		
		try {
			ProfessorInfo professorInfo = professorService.loadProfessorInfo(req -> {
				setupServiceRequest(request, req);
				req.setProfessorId(requestMessage.getProfessorId().trim());
			}).getProfessorInfo();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.professorInfo(professorInfo)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
		} catch (LoadProfessorInfoException ex) {
			logger.error(ex.getMessage(), ex);
			
			if(ex instanceof LoadProfessorInfoException.UserNotFound) {
				return replyError(request, "400102", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			return replyError(request, "400199", ex.getErrorTitle(), ex.getErrorMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "500999", "Request Processing Failed", ex.getMessage());
		}
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected String professorId;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected ProfessorInfo professorInfo;
	}
}
