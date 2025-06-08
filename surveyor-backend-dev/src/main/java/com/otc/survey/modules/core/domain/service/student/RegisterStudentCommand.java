package com.otc.survey.modules.core.domain.service.student;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.LineAccount;
import com.otc.survey.modules.core.domain.model.line.LineUserProfile;
import com.otc.survey.modules.core.domain.repository.LineAccountRepository;
import com.otc.survey.modules.core.domain.repository.StudentRepository;
import com.otc.survey.modules.core.domain.repository.UserRepository;
import com.otc.survey.modules.core.domain.service.StudentConfigService;
import com.otc.survey.modules.core.domain.service.student.dto.CreateStudentRequest;
import com.otc.survey.modules.core.domain.service.student.dto.CreateStudentResponse;
import com.otc.survey.modules.core.domain.service.student.dto.RegisterStudentRequest;
import com.otc.survey.modules.core.domain.service.student.dto.RegisterStudentResponse;
import com.otc.survey.modules.core.domain.service.student.exception.CreateStudentException;
import com.otc.survey.modules.core.domain.service.student.exception.RegisterStudentException;

import lombok.Data;

@ServiceCommand
public class RegisterStudentCommand extends BaseServiceCommand
{
	@Autowired
	protected StudentRepository studentRepository;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected StudentConfigService studentConfigService;
	
	@Autowired
	protected LineAccountRepository lineAccountRepository;
	
	
	public Response registerStudent(Request request) throws RegisterStudentException
	{
		logger.info("### {}.registerStudent ###", getClass().getSimpleName());
		
		validate(request);
		
		CreateStudentResponse createStudentResponse = createStudent(request);
		
		LineAccount lineAccount = null;
		if(request.getRegisterStudentRequest().getLineUserProfile() != null) {
			LineUserProfile lineUserProfile = request.getRegisterStudentRequest().getLineUserProfile();
			
			lineAccount = new LineAccount();
			lineAccount.setId(UUID.randomUUID().toString());
			lineAccount.setUserId(createStudentResponse.getStudentInfo().getUser().getId());
			lineAccount.setLineUserId(lineUserProfile.getUserId());
			lineAccount.setLineAccountName(lineUserProfile.getDisplayName());
			lineAccount.setStatus(StatusCode.Active);
			lineAccount.setCreatedBy(createStudentResponse.getStudentInfo().getUser().getId());
			lineAccount.setCreatedAt(request.getPerformedAt());
			
			lineAccountRepository.save(lineAccount);
		}
		
		RegisterStudentResponse registerStudentResponse = createStudentResponse.toRegisterStudentResponse();
		
		Response response = new Response();
		response.setRegisterStudentResponse(registerStudentResponse);
		
		return response;
	}
	
	protected boolean validate(Request request) throws RegisterStudentException
	{
		RegisterStudentRequest registerStudentRequest = request.getRegisterStudentRequest();
		
		if(registerStudentRequest == null) {
			throw new IllegalArgumentException("Param 'registerStudentRequest' Couldn't Be Null");
		}
		
		if(registerStudentRequest.getStudentCode() == null || registerStudentRequest.getStudentCode().isBlank()) {
			throw new IllegalArgumentException("Param 'registerStudentRequest.studentCode' Couldn't Be Null OR Empty");
		}
		
		if(registerStudentRequest.getAccountName() == null || registerStudentRequest.getAccountName().isBlank()) {
			throw new IllegalArgumentException("Param 'registerStudentRequest.accountName' Couldn't Be Null OR Empty");
		}
		
		if(registerStudentRequest.getEmail() == null || registerStudentRequest.getEmail().isBlank()) {
			throw new IllegalArgumentException("Param 'registerStudentRequest.email' Couldn't Be Null OR Empty");
		}
		
		if(registerStudentRequest.getUsername() == null || registerStudentRequest.getUsername().isBlank()) {
			throw new IllegalArgumentException("Param 'registerStudentRequest.username' Couldn't Be Null OR Empty");
		}
		
		if(registerStudentRequest.getPassword() == null || registerStudentRequest.getPassword().isBlank()) {
			throw new IllegalArgumentException("Param 'registerStudentRequest.password' Couldn't Be Null OR Empty");
		}
		
		if(registerStudentRequest.getStudyDegreeId() == null || registerStudentRequest.getStudyDegreeId().isBlank()) {
			throw new IllegalArgumentException("Param 'registerStudentRequest.studyDegreeId' Couldn't Be Null OR Empty");
		}
		
		if(registerStudentRequest.getStudyClassLevel() == null || registerStudentRequest.getStudyClassLevel() <= 0) {
			throw new IllegalArgumentException("Param 'registerStudentRequest.studyClassLevel' Couldn't Be Null OR Empty");
		}
		
		if(studentRepository.existsByStudentCode(registerStudentRequest.getStudentCode())) {
			throw new RegisterStudentException.DuplicateStudentCode(registerStudentRequest.getStudentCode());
		}
		
		if(userRepository.existsByUsername(registerStudentRequest.getUsername())) {
			throw new RegisterStudentException.DuplicateUsername(registerStudentRequest.getUsername());
		}
		
		if(userRepository.existsByEmail(registerStudentRequest.getUsername())) {
			throw new RegisterStudentException.DuplicateEmail(registerStudentRequest.getEmail());
		}
		
		return true;
	}
	
	protected CreateStudentResponse createStudent(Request request) throws RegisterStudentException
	{
		logger.info("### {}.createStudent ###", getClass().getSimpleName());
		
		CreateStudentRequest createStudentRequest = request.getRegisterStudentRequest().toCreateStudentRequest();
		
		try {
			return studentService.createStudent(req -> {
				req.copyFrom(request);
				req.setCreateStudentRequest(createStudentRequest);
			}).getCreateStudentResponse();
		} catch(CreateStudentException ex) {
			if(ex instanceof CreateStudentException.DuplicateStudentCode error) {
				throw new RegisterStudentException.DuplicateStudentCode(error.getStudentCode());
			}
			
			if(ex instanceof CreateStudentException.DuplicateUsername error) {
				throw new RegisterStudentException.DuplicateUsername(error.getUsername());
			}
			
			if(ex instanceof CreateStudentException.DuplicateEmail error) {
				throw new RegisterStudentException.DuplicateUsername(error.getEmail());
			}
			
			throw new RegisterStudentException(ex.getCause(), ex.getErrorCode(), ex.getErrorTitle(), ex.getErrorMessage());
		}
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected RegisterStudentRequest registerStudentRequest;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected RegisterStudentResponse registerStudentResponse;
	}
}