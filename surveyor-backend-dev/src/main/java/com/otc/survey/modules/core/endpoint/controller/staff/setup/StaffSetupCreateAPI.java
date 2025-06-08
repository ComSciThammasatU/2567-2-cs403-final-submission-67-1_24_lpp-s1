package com.otc.survey.modules.core.endpoint.controller.staff.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.core.domain.service.staff.StaffService;
import com.otc.survey.modules.core.domain.service.staff.dto.CreateStaffRequest;
import com.otc.survey.modules.core.domain.service.staff.dto.CreateStaffResponse;
import com.otc.survey.modules.core.domain.service.staff.exception.CreateStaffException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/staff/setup/create")
@Auth(
	authen = true, 
	authorize = true, 
	grantedRoleIds = { 
		RoleConst.ID_ADMIN_SYSTEM, 
		RoleConst.ID_ADMIN_USER 
	}
)
public class StaffSetupCreateAPI extends AbstractJsonBodyAPI<StaffSetupCreateAPI.RequestMessage, StaffSetupCreateAPI.BodyResponseMessage>
{
	@Autowired
	protected StaffService staffService;
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) {
		if (requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}

		CreateStaffRequest createStaffRequest = requestMessage.getCreateStaffRequest();

		if (createStaffRequest == null) {
			return replyError(request, "400002", "Invalid Request Message",
					"Param 'createStaffRequest' Couldn't Be Null");
		}

		try {
			CreateStaffResponse createStaffResponse = staffService.createStaff(req -> {
				setupServiceRequest(request, req);
				req.setCreateStaffRequest(createStaffRequest);
			}).getCreateStaffResponse();

			BodyResponseMessage bodyResponseMessage = BodyResponseMessage.builder()
					.createStaffResponse(createStaffResponse).build();

			return replySuccess(request, bodyResponseMessage);
		} catch (CreateStaffException ex) {
			logger.error(ex.getMessage(), ex);

			if (ex instanceof CreateStaffException.DuplicateUsername) {
				return replyError(request, "400102", ex.getErrorTitle(), ex.getErrorMessage());
			}

			if (ex instanceof CreateStaffException.DuplicateEmail) {
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
		protected CreateStaffRequest createStaffRequest;
	}	

	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage {
		protected CreateStaffResponse createStaffResponse;
	}
}
