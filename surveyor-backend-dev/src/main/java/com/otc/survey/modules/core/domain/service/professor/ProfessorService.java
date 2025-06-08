package com.otc.survey.modules.core.domain.service.professor;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.survey.modules.common.domain.service.BaseService;
import com.otc.survey.modules.core.domain.service.professor.exception.CreateProfessorException;
import com.otc.survey.modules.core.domain.service.professor.exception.LoadProfessorInfoException;
import com.otc.survey.modules.core.domain.service.professor.exception.SearchProfessorException;
import com.otc.survey.modules.core.domain.service.professor.exception.UpdateProfessorException;

@Service
public class ProfessorService extends BaseService
{
	@Autowired
	protected CreateProfessorCommand createProfessorCommand;
	
	@Autowired
	protected SearchProfessorCommand searchProfessorCommand;
	
	@Autowired
	protected UpdateProfessorCommand updateProfessorCommand;
	
	@Autowired
	protected LoadProfessorInfoCommand loadProfessorInfoCommand;

	// ****************************** createProfessor ******************************//
	@Transactional(noRollbackFor = Throwable.class)
	public CreateProfessorCommand.Response createProfessor(Consumer<CreateProfessorCommand.Request> consumer)
			throws CreateProfessorException {
		CreateProfessorCommand.Request request = new CreateProfessorCommand.Request();
		consumer.accept(request);
		return createProfessor(request);
	}

	@Transactional(noRollbackFor = Throwable.class)
	public CreateProfessorCommand.Response createProfessor(CreateProfessorCommand.Request request)
			throws CreateProfessorException {
		logger.info("### {}.createProfessor ###", getClass().getSimpleName());
		return createProfessorCommand.createProfessor(request);
	}
	// ****************************** createProfessor ******************************//
	
	
	// ****************************** searchProfessor ******************************//
	public SearchProfessorCommand.Response searchProfessor(Consumer<SearchProfessorCommand.Request> consumer) throws SearchProfessorException 
	{
		SearchProfessorCommand.Request request = new SearchProfessorCommand.Request();
		consumer.accept(request);
		return searchProfessor(request);
	}
	
	public SearchProfessorCommand.Response searchProfessor(SearchProfessorCommand.Request request) throws SearchProfessorException 
	{
		logger.info("### {}.searchProfessor ###", getClass().getSimpleName());
		return searchProfessorCommand.searchProfessor(request);
	}
	// ****************************** searchProfessor ******************************//
	
	
	// ****************************** updateProfessor ******************************//
	@Transactional(noRollbackFor = Throwable.class)
	public UpdateProfessorCommand.Response updateProfessor(Consumer<UpdateProfessorCommand.Request> consumer) throws UpdateProfessorException
	{
		UpdateProfessorCommand.Request request = new UpdateProfessorCommand.Request();
		consumer.accept(request);
		return updateProfessor(request);
	}
	
	@Transactional(noRollbackFor = Throwable.class)
	public UpdateProfessorCommand.Response updateProfessor(UpdateProfessorCommand.Request request) throws UpdateProfessorException
	{
		logger.info("### {}.updateProfessor ###", getClass().getSimpleName());
		return updateProfessorCommand.updateProfessor(request);
	}
	// ****************************** updateProfessor ******************************//
	
	
	// ****************************** loadInfoProfessor ******************************//
	public LoadProfessorInfoCommand.Response loadProfessorInfo(Consumer<LoadProfessorInfoCommand.Request> consumer) throws LoadProfessorInfoException
	{
		LoadProfessorInfoCommand.Request request = new LoadProfessorInfoCommand.Request();
		consumer.accept(request);
		return loadProfessorInfo(request);
	}
	
	public LoadProfessorInfoCommand.Response loadProfessorInfo(LoadProfessorInfoCommand.Request request) throws LoadProfessorInfoException
	{
		logger.info("### {}.loadProfessorInfo ###", getClass().getSimpleName());
		return loadProfessorInfoCommand.loadProfessorInfo(request);
	}
	// ****************************** loadInfoProfessor ******************************//
}
