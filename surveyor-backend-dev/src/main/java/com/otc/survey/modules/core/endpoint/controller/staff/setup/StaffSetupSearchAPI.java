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
import com.otc.survey.modules.core.domain.service.staff.dto.SearchStaffRequest;
import com.otc.survey.modules.core.domain.service.staff.dto.SearchStaffResponse;
import com.otc.survey.modules.core.domain.service.staff.exception.SearchStaffException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/staff/setup/search")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_USER
	}
)
public class StaffSetupSearchAPI extends AbstractJsonBodyAPI<StaffSetupSearchAPI.RequestMessage, StaffSetupSearchAPI.BodyResponseMessage>
{
	@Autowired
	protected StaffService staffService;
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if(requestMessage.getSearchStaffRequest() == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'searchStudentRequest' Couldn't Be Null");
		}
		
		SearchStaffResponse searchStaffResponse = new SearchStaffResponse();
		try {
			searchStaffResponse = staffService.searchStaff(req -> {
				setupServiceRequest(request, req);
				req.setSearchStaffRequest(requestMessage.getSearchStaffRequest());
			}).getSearchStaffResponse();
		} catch (SearchStaffException ex) {
			return replyError(request, "500999", ex.getClass().getSimpleName(), ex.getMessage());
		}
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.searchStaffResponse(searchStaffResponse)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected SearchStaffRequest searchStaffRequest;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected SearchStaffResponse searchStaffResponse;
	}
}
