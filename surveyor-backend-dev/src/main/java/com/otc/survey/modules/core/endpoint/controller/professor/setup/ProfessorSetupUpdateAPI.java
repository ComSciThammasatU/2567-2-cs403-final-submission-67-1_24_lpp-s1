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
import com.otc.survey.modules.core.domain.service.professor.dto.UpdateProfessorRequest;
import com.otc.survey.modules.core.domain.service.professor.dto.UpdateProfessorResponse;
import com.otc.survey.modules.core.domain.service.professor.exception.UpdateProfessorException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/professor/setup/update")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_USER
	}
)
public class ProfessorSetupUpdateAPI extends AbstractJsonBodyAPI<ProfessorSetupUpdateAPI.RequestMessage, ProfessorSetupUpdateAPI.BodyResponseMessage>
{
	@Autowired
	protected ProfessorService professorService;
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		UpdateProfessorRequest updateProfessorRequest = requestMessage.getUpdateProfessorRequest();
		
		if(updateProfessorRequest == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'updateProfessorRequest' Couldn't Be Null");
		}
		
		try {
			UpdateProfessorResponse updateProfessorResponse = professorService.updateProfessor(req -> {
				setupServiceRequest(request, req);
				req.setUpdateProfessorRequest(updateProfessorRequest);
			}).getUpdateProfessorResponse();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.updateProfessorResponse(updateProfessorResponse)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
		} catch (UpdateProfessorException ex) {
			logger.error(ex.getMessage(), ex);
			
			if(ex instanceof UpdateProfessorException.UserNotFound) {
				return replyError(request, "400103", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof UpdateProfessorException.DuplicateUsername) {
				return replyError(request, "400104", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof UpdateProfessorException.DuplicateEmail) {
				return replyError(request, "400105", ex.getErrorTitle(), ex.getErrorMessage());
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
		protected UpdateProfessorRequest updateProfessorRequest;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected UpdateProfessorResponse updateProfessorResponse;
	}
}
