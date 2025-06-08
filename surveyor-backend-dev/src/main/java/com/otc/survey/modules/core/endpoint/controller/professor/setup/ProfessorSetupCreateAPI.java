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
import com.otc.survey.modules.core.domain.service.professor.dto.CreateProfessorRequest;
import com.otc.survey.modules.core.domain.service.professor.dto.CreateProfessorResponse;
import com.otc.survey.modules.core.domain.service.professor.exception.CreateProfessorException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/professor/setup/create")
@Auth(
	authen = true, 
	authorize = true, 
	grantedRoleIds = { 
		RoleConst.ID_ADMIN_SYSTEM, 
		RoleConst.ID_ADMIN_USER 
	}
)
public class ProfessorSetupCreateAPI extends AbstractJsonBodyAPI<ProfessorSetupCreateAPI.RequestMessage, ProfessorSetupCreateAPI.BodyResponseMessage> 
{
	@Autowired
	protected ProfessorService professorService;

	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) {
		if (requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}

		CreateProfessorRequest createProfessorRequest = requestMessage.getCreateProfessorRequest();

		if (createProfessorRequest == null) {
			return replyError(request, "400002", "Invalid Request Message",
					"Param 'createProfessorRequest' Couldn't Be Null");
		}

		try {
			CreateProfessorResponse createProfessorResponse = professorService.createProfessor(req -> {
				setupServiceRequest(request, req);
				req.setCreateProfessorRequest(createProfessorRequest);
			}).getCreateProfessorResponse();

			BodyResponseMessage bodyResponseMessage = BodyResponseMessage.builder()
					.createProfessorResponse(createProfessorResponse).build();

			return replySuccess(request, bodyResponseMessage);
		} catch (CreateProfessorException ex) {
			logger.error(ex.getMessage(), ex);

			if (ex instanceof CreateProfessorException.DuplicateUsername) {
				return replyError(request, "400102", ex.getErrorTitle(), ex.getErrorMessage());
			}

			if (ex instanceof CreateProfessorException.DuplicateEmail) {
				return replyError(request, "400103", ex.getErrorTitle(), ex.getErrorMessage());
			}

			return replyError(request, "400199", ex.getErrorTitle(), ex.getErrorMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return replyError(request, "500999", "Request Processing Failed", ex.getMessage());
		}
	}

	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage {
		protected CreateProfessorRequest createProfessorRequest;
	}	

	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage {
		protected CreateProfessorResponse createProfessorResponse;
	}
}