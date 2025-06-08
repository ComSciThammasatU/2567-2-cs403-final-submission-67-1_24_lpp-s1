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
import com.otc.survey.modules.core.domain.service.student.dto.StudentInfo;
import com.otc.survey.modules.core.domain.service.student.exception.LoadStudentInfoException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/student/setup/load-info")
@Auth(
	authen = true,
	authorize = true,
	grantedRoleIds = {
		RoleConst.ID_ADMIN_SYSTEM,
		RoleConst.ID_ADMIN_USER
	}
)
public class StudentSetupLoadInfoAPI extends AbstractJsonBodyAPI<StudentSetupLoadInfoAPI.RequestMessage, StudentSetupLoadInfoAPI.BodyResponseMessage>
{
	@Autowired
	protected StudentService studentService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		if(requestMessage.getStudentId() == null || requestMessage.getStudentId().isBlank()) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'studentId' Couldn't Be Null OR Empty");
		}
		
		try {
			StudentInfo studentInfo = studentService.loadStudentInfo(req -> {
				setupServiceRequest(request, req);
				req.setStudentId(requestMessage.getStudentId().trim());
			}).getStudentInfo();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.studentInfo(studentInfo)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
		} catch (LoadStudentInfoException ex) {
			logger.error(ex.getMessage(), ex);
			
			if(ex instanceof LoadStudentInfoException.StudentNotFound) {
				return replyError(request, "400101", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof LoadStudentInfoException.UserNotFound) {
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
		protected String studentId;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected StudentInfo studentInfo;
	}
}