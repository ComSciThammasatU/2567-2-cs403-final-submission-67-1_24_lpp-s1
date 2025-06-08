package com.otc.survey.modules.core.endpoint.controller.student.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.constant.RoleConst;
import com.otc.survey.modules.core.domain.service.student.StudentService;
import com.otc.survey.modules.core.domain.service.student.dto.SearchStudentRequest;
import com.otc.survey.modules.core.domain.service.student.dto.SearchStudentResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/student/setup/search")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_USER
	}
)
public class StudentSetupSearchAPI extends AbstractJsonBodyAPI<StudentSetupSearchAPI.RequestMessage, StudentSetupSearchAPI.BodyResponseMessage>
{
	@Autowired
	protected StudentService studentService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if(requestMessage.getSearchStudentRequest() == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'searchStudentRequest' Couldn't Be Null");
		}
		
		SearchStudentResponse searchStudentResponse = studentService.searchStudent(req -> {
			setupServiceRequest(request, req);
			req.setSearchStudentRequest(requestMessage.getSearchStudentRequest());
		}).getSearchStudentResponse();
		
		BodyResponseMessage bodyResponseMessage = BodyResponseMessage
				.builder()
				.searchStudentResponse(searchStudentResponse)
				.build();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage extends AbstractJsonBodyAPI.BaseRequestMessage
	{
		protected SearchStudentRequest searchStudentRequest;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected SearchStudentResponse searchStudentResponse;
	}
}