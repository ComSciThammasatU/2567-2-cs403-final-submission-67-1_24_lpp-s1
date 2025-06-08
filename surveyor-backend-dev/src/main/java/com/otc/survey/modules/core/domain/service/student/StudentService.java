package com.otc.survey.modules.core.domain.service.student;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.survey.modules.common.domain.service.BaseService;
import com.otc.survey.modules.core.domain.service.student.exception.CreateStudentException;
import com.otc.survey.modules.core.domain.service.student.exception.LoadStudentInfoException;
import com.otc.survey.modules.core.domain.service.student.exception.RegisterStudentException;
import com.otc.survey.modules.core.domain.service.student.exception.UpdateStudentException;

@Service
public class StudentService extends BaseService
{
	@Autowired
	protected RegisterStudentCommand registerStudentCommand;
	
	@Autowired
	protected SearchStudentCommand searchStudentCommand;
	
	@Autowired
	protected LoadStudentInfoCommand loadStudentInfoCommand;
	
	@Autowired
	protected CreateStudentCommand createStudentCommand;
	
	@Autowired
	protected UpdateStudentCommand updateStudentCommand;
	
	
	// ****************************** registerStudent ****************************** //
	@Transactional(noRollbackFor = Throwable.class)
	public RegisterStudentCommand.Response registerStudent(Consumer<RegisterStudentCommand.Request> consumer) throws RegisterStudentException
	{
		RegisterStudentCommand.Request request = new RegisterStudentCommand.Request();
		consumer.accept(request);
		return registerStudent(request);
	}
	
	@Transactional(noRollbackFor = Throwable.class)
	public RegisterStudentCommand.Response registerStudent(RegisterStudentCommand.Request request) throws RegisterStudentException
	{
		logger.info("### {}.registerStudent ###", getClass().getSimpleName());
		return registerStudentCommand.registerStudent(request);
	}
	// ****************************** registerStudent ****************************** //
	
	
	// ****************************** searchStudent ****************************** //
	public SearchStudentCommand.Response searchStudent(Consumer<SearchStudentCommand.Request> consumer) 
	{
		SearchStudentCommand.Request request = new SearchStudentCommand.Request();
		consumer.accept(request);
		return searchStudent(request);
	}
	
	public SearchStudentCommand.Response searchStudent(SearchStudentCommand.Request request) 
	{
		logger.info("### {}.searchStudent ###", getClass().getSimpleName());
		return searchStudentCommand.searchStudent(request);
	}
	// ****************************** searchStudent ****************************** //
	
	
	// ****************************** loadStudentInfo ****************************** //
	public LoadStudentInfoCommand.Response loadStudentInfo(Consumer<LoadStudentInfoCommand.Request> consumer) throws LoadStudentInfoException
	{
		LoadStudentInfoCommand.Request request = new LoadStudentInfoCommand.Request();
		consumer.accept(request);
		return loadStudentInfo(request);
	}
	
	public LoadStudentInfoCommand.Response loadStudentInfo(LoadStudentInfoCommand.Request request) throws LoadStudentInfoException
	{
		logger.info("### {}.loadStudentInfo ###", getClass().getSimpleName());
		return loadStudentInfoCommand.loadStudentInfo(request);
	}
	// ****************************** loadStudentInfo ****************************** //
	
	
	// ****************************** createStudent ****************************** //
	@Transactional(noRollbackFor = Throwable.class)
	public CreateStudentCommand.Response createStudent(Consumer<CreateStudentCommand.Request> consumer) throws CreateStudentException
	{
		CreateStudentCommand.Request request = new CreateStudentCommand.Request();
		consumer.accept(request);
		return createStudent(request);
	}
	
	@Transactional(noRollbackFor = Throwable.class)
	public CreateStudentCommand.Response createStudent(CreateStudentCommand.Request request) throws CreateStudentException
	{
		logger.info("### {}.createStudent ###", getClass().getSimpleName());
		return createStudentCommand.createStudent(request);
	}
	// ****************************** createStudent ****************************** //
	
	
	// ****************************** updateStudent ****************************** //
	@Transactional(noRollbackFor = Throwable.class)
	public UpdateStudentCommand.Response updateStudent(Consumer<UpdateStudentCommand.Request> consumer) throws UpdateStudentException
	{
		UpdateStudentCommand.Request request = new UpdateStudentCommand.Request();
		consumer.accept(request);
		return updateStudent(request);
	}
	
	@Transactional(noRollbackFor = Throwable.class)
	public UpdateStudentCommand.Response updateStudent(UpdateStudentCommand.Request request) throws UpdateStudentException
	{
		logger.info("### {}.updateStudent ###", getClass().getSimpleName());
		return updateStudentCommand.updateStudent(request);
	}
	// ****************************** updateStudent ****************************** //
}