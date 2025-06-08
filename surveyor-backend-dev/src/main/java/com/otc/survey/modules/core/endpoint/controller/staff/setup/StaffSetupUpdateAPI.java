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
import com.otc.survey.modules.core.domain.service.staff.dto.UpdateStaffRequest;
import com.otc.survey.modules.core.domain.service.staff.dto.UpdateStaffResponse;
import com.otc.survey.modules.core.domain.service.staff.exception.UpdateStaffException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/staff/setup/update")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_USER
	}
)
public class StaffSetupUpdateAPI extends AbstractJsonBodyAPI<StaffSetupUpdateAPI.RequestMessage , StaffSetupUpdateAPI.BodyResponseMessage>
{
	@Autowired
	protected StaffService staffService;
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		UpdateStaffRequest updateStaffRequest = requestMessage.getUpdateStaffRequest();
		
		if(updateStaffRequest == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'updateStaffRequest' Couldn't Be Null");
		}
		
		try {
			UpdateStaffResponse updateStaffResponse = staffService.updateStaff(req -> {
				setupServiceRequest(request, req);
				req.setUpdateStaffRequest(updateStaffRequest);
			}).getUpdateStaffResponse();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.updateStaffResponse(updateStaffResponse)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
		} catch (UpdateStaffException ex) {
			logger.error(ex.getMessage(), ex);
			
			if(ex instanceof UpdateStaffException.UserNotFound) {
				return replyError(request, "400103", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof UpdateStaffException.DuplicateUsername) {
				return replyError(request, "400104", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof UpdateStaffException.DuplicateEmail) {
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
		protected UpdateStaffRequest updateStaffRequest;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected UpdateStaffResponse updateStaffResponse;
	}
}
