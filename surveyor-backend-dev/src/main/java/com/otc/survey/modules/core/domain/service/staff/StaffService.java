package com.otc.survey.modules.core.domain.service.staff;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.survey.modules.common.domain.service.BaseService;
import com.otc.survey.modules.core.domain.service.staff.exception.CreateStaffException;
import com.otc.survey.modules.core.domain.service.staff.exception.LoadStaffInfoException;
import com.otc.survey.modules.core.domain.service.staff.exception.SearchStaffException;
import com.otc.survey.modules.core.domain.service.staff.exception.UpdateStaffException;

@Service
public class StaffService extends BaseService 
{
	@Autowired
	protected SearchStaffCommand searchStaffCommand;
	
	@Autowired
	protected LoadStaffInfoCommand loadStaffInfoCommand;
	
	@Autowired
	protected CreateStaffCommand createStaffCommand;
	
	@Autowired
	protected UpdateStaffCommand updateStaffCommand;
	
	@Autowired
	protected NotifyStaffRegistrationCommand notifyStaffRegistrationCommand;
	
	
	// ****************************** searchStaff ******************************//
	public SearchStaffCommand.Response searchStaff(Consumer<SearchStaffCommand.Request> consumer) throws SearchStaffException 
	{
		SearchStaffCommand.Request request = new SearchStaffCommand.Request();
		consumer.accept(request);
		return searchStaff(request);
	}

	public SearchStaffCommand.Response searchStaff(SearchStaffCommand.Request request) throws SearchStaffException 
	{
		logger.info("### {}.searchStaff ###", getClass().getSimpleName());
		return searchStaffCommand.searchStaff(request);
	}
	// ****************************** searchStaff ******************************//
	
	
	// ****************************** loadStaffInfo ******************************//
	public LoadStaffInfoCommand.Response loadStaffInfo(Consumer<LoadStaffInfoCommand.Request> consumer) throws LoadStaffInfoException 
	{
		LoadStaffInfoCommand.Request request = new LoadStaffInfoCommand.Request();
		consumer.accept(request);
		return loadStaffInfo(request);
	}

	public LoadStaffInfoCommand.Response loadStaffInfo(LoadStaffInfoCommand.Request request) throws LoadStaffInfoException 
	{
		logger.info("### {}.loadStaffInfo ###", getClass().getSimpleName());
		return loadStaffInfoCommand.loadStaffInfo(request);
	}
	// ****************************** loadStaffInfo ******************************//
	
	
	// ****************************** createStaff ******************************//
	@Transactional(noRollbackFor = Throwable.class)
	public CreateStaffCommand.Response createStaff(Consumer<CreateStaffCommand.Request> consumer) throws CreateStaffException 
	{
		CreateStaffCommand.Request request = new CreateStaffCommand.Request();
		consumer.accept(request);
		return createStaff(request);
	}

	@Transactional(noRollbackFor = Throwable.class)
	public CreateStaffCommand.Response createStaff(CreateStaffCommand.Request request) throws CreateStaffException 
	{
		logger.info("### {}.createStaff ###", getClass().getSimpleName());
		return createStaffCommand.createStaff(request);
	}
	// ****************************** createStaff ******************************//
	
	
	// ****************************** updateStaff ******************************//
	@Transactional(noRollbackFor = Throwable.class)
	public UpdateStaffCommand.Response updateStaff(Consumer<UpdateStaffCommand.Request> consumer) throws UpdateStaffException
	{
		UpdateStaffCommand.Request request = new UpdateStaffCommand.Request();
		consumer.accept(request);
		return updateStaff(request);
	}
	
	@Transactional(noRollbackFor = Throwable.class)
	public UpdateStaffCommand.Response updateStaff(UpdateStaffCommand.Request request) throws UpdateStaffException
	{
		logger.info("### {}.updateStaff ###", getClass().getSimpleName());
		return updateStaffCommand.updateStaff(request);
	}	
	// ****************************** updateStaff ******************************//
	
	
	// ****************************** notifyRegistration ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public NotifyStaffRegistrationCommand.Response notifyRegistration(Consumer<NotifyStaffRegistrationCommand.Request> consumer)
	{
		NotifyStaffRegistrationCommand.Request request = new NotifyStaffRegistrationCommand.Request();
		consumer.accept(request);
		return notifyRegistration(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public NotifyStaffRegistrationCommand.Response notifyRegistration(NotifyStaffRegistrationCommand.Request request)
	{
		logger.info("### {}.notifyRegistration ###", getClass().getSimpleName());
		return notifyStaffRegistrationCommand.notifyRegistration(request);
	}
	// ****************************** notifyRegistration ****************************** //
}