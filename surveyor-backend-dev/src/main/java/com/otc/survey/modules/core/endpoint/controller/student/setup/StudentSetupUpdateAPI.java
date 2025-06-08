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
import com.otc.survey.modules.core.domain.service.student.dto.UpdateStudentRequest;
import com.otc.survey.modules.core.domain.service.student.dto.UpdateStudentResponse;
import com.otc.survey.modules.core.domain.service.student.exception.UpdateStudentException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/student/setup/update")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_USER
	}
)
public class StudentSetupUpdateAPI extends AbstractJsonBodyAPI<StudentSetupUpdateAPI.RequestMessage, StudentSetupUpdateAPI.BodyResponseMessage>
{
	@Autowired
	protected StudentService studentService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		UpdateStudentRequest updateStudentRequest = requestMessage.getUpdateStudentRequest();
		
		if(updateStudentRequest == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'updateStudentRequest' Couldn't Be Null");
		}
		
		try {
			UpdateStudentResponse updateStudentResponse = studentService.updateStudent(req -> {
				setupServiceRequest(request, req);
				req.setUpdateStudentRequest(updateStudentRequest);
			}).getUpdateStudentResponse();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.updateStudentResponse(updateStudentResponse)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
		} catch (UpdateStudentException ex) {
			logger.error(ex.getMessage(), ex);
			
			if(ex instanceof UpdateStudentException.StudentNotFound) {
				return replyError(request, "400101", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof UpdateStudentException.DuplicateStudentCode) {
				return replyError(request, "400102", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof UpdateStudentException.UserNotFound) {
				return replyError(request, "400103", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof UpdateStudentException.DuplicateUsername) {
				return replyError(request, "400104", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof UpdateStudentException.DuplicateEmail) {
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
		protected UpdateStudentRequest updateStudentRequest;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected UpdateStudentResponse updateStudentResponse;
	}
}