package com.otc.survey.modules.core.domain.service.line;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.survey.modules.common.domain.service.BaseService;
import com.otc.survey.modules.core.endpoint.controller.line.ProcessLineMessagingEventCommand;

@Service
public class LineService extends BaseService
{
	@Autowired
	protected LoadLineUserProfileCommand loadLineUserProfileCommand;
	
	@Autowired
	protected CreateLineAccountCommand createLineAccountCommand;
	
	@Autowired
	protected ProcessLineMessagingEventCommand processMessagingEventCommand;
	
	@Autowired
	protected LineReplyMessageCommand replyMessageCommand;
	
	@Autowired
	protected LinePushMessageCommand pushMessageCommand;
	
	
	// ****************************** loadUserProfile ****************************** //
	public LoadLineUserProfileCommand.Response loadUserProfile(Consumer<LoadLineUserProfileCommand.Request> consumer)
	{
		LoadLineUserProfileCommand.Request request = new LoadLineUserProfileCommand.Request();
		consumer.accept(request);
		return loadUserProfile(request);
	}
	
	public LoadLineUserProfileCommand.Response loadUserProfile(LoadLineUserProfileCommand.Request request)
	{
		logger.info("### {}.loadUserProfile ###", getClass().getSimpleName());
		return loadLineUserProfileCommand.loadUserProfile(request);
	}
	// ****************************** loadUserProfile ****************************** //
	
	
	// ****************************** createLineAccount ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public CreateLineAccountCommand.Response createLineAccount(Consumer<CreateLineAccountCommand.Request> consumer)
	{
		CreateLineAccountCommand.Request request = new CreateLineAccountCommand.Request();
		consumer.accept(request);
		return createLineAccount(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public CreateLineAccountCommand.Response createLineAccount(CreateLineAccountCommand.Request request)
	{
		logger.info("### {}.createLineAccount ###", getClass().getSimpleName());
		return createLineAccountCommand.createLineAccount(request);
	}
	// ****************************** createLineAccount ****************************** //
	
	
	// ****************************** processMessagingEvent ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public ProcessLineMessagingEventCommand.Response processMessagingEvent(Consumer<ProcessLineMessagingEventCommand.Request> consumer)
	{
		ProcessLineMessagingEventCommand.Request request = new ProcessLineMessagingEventCommand.Request();
		consumer.accept(request);
		return processMessagingEvent(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public ProcessLineMessagingEventCommand.Response processMessagingEvent(ProcessLineMessagingEventCommand.Request request)
	{
		logger.info("### {}.processMessagingEvent ###", getClass().getSimpleName());
		return processMessagingEventCommand.processMessagingEvent(request);
	}
	// ****************************** processMessagingEvent ****************************** //
	
	
	// ****************************** replyMessage ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public LineReplyMessageCommand.Response replyMessage(Consumer<LineReplyMessageCommand.Request> consumer)
	{
		LineReplyMessageCommand.Request request = new LineReplyMessageCommand.Request();
		consumer.accept(request);
		return replyMessage(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public LineReplyMessageCommand.Response replyMessage(LineReplyMessageCommand.Request request)
	{
		logger.info("### {}.replyMessage ###", getClass().getSimpleName());
		return replyMessageCommand.replyMessage(request);
	}
	// ****************************** replyMessage ****************************** //
	
	
	// ****************************** pushMessage ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public LinePushMessageCommand.Response pushMessage(Consumer<LinePushMessageCommand.Request> consumer)
	{
		LinePushMessageCommand.Request request = new LinePushMessageCommand.Request();
		consumer.accept(request);
		return pushMessage(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public LinePushMessageCommand.Response pushMessage(LinePushMessageCommand.Request request)
	{
		logger.info("### {}.pushMessage ###", getClass().getSimpleName());
		return pushMessageCommand.pushMessage(request);
	}
	// ****************************** pushMessage ****************************** //
}