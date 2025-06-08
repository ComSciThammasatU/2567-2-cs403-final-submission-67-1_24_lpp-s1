package com.otc.survey.modules.core.endpoint.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.endpoint.ResponseMessage;
import com.otc.survey.modules.common.endpoint.controller.AbstractJsonBodyAPI;
import com.otc.survey.modules.core.domain.service.student.StudentService;
import com.otc.survey.modules.core.domain.service.student.dto.RegisterStudentRequest;
import com.otc.survey.modules.core.domain.service.student.dto.RegisterStudentResponse;
import com.otc.survey.modules.core.domain.service.student.exception.RegisterStudentException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

@APIController
@RequestMapping("/api/core/student/register/submit")
public class StudentRegisterSubmitAPI extends AbstractJsonBodyAPI<StudentRegisterSubmitAPI.RequestMessage, StudentRegisterSubmitAPI.BodyResponseMessage>
{
	@Autowired
	protected StudentService studentService;
	
	
	@Override
	protected ResponseEntity<ResponseMessage<BodyResponseMessage>> doExecute(HttpServletRequest request, HttpServletResponse response, RequestMessage requestMessage) 
	{
		if(requestMessage == null) {
			return replyError(request, "400001", "Invalid Request Message", "Request Message Couldn't Be Null");
		}
		
		RegisterStudentRequest registerStudentRequest = requestMessage.getRegisterStudentRequest();
		
		if(registerStudentRequest == null) {
			return replyError(request, "400002", "Invalid Request Message", "Param 'registerStudentRequest' Couldn't Be Null");
		}
		
		try {
			RegisterStudentResponse registerStudentResponse = studentService.registerStudent(req -> {
				setupServiceRequest(request, req);
				req.setRegisterStudentRequest(registerStudentRequest);
			}).getRegisterStudentResponse();
			
			BodyResponseMessage bodyResponseMessage = BodyResponseMessage
					.builder()
					.registerStudentResponse(registerStudentResponse)
					.build();
			
			return replySuccess(request, bodyResponseMessage);
		} catch (RegisterStudentException ex) {
			logger.error(ex.getMessage(), ex);
			
			if(ex instanceof RegisterStudentException.DuplicateStudentCode) {
				return replyError(request, "400101", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof RegisterStudentException.DuplicateUsername) {
				return replyError(request, "400102", ex.getErrorTitle(), ex.getErrorMessage());
			}
			
			if(ex instanceof RegisterStudentException.DuplicateEmail) {
				return replyError(request, "400103", ex.getErrorTitle(), ex.getErrorMessage());
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
		protected RegisterStudentRequest registerStudentRequest;
	}
	
	
	@Data
	@Builder
	public static class BodyResponseMessage extends AbstractJsonBodyAPI.BaseBodyResponseMessage
	{
		protected RegisterStudentResponse registerStudentResponse;
	}
}