package com.otc.survey.modules.survey.domain.service.form_template;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.survey.modules.common.domain.service.BaseService;
import com.otc.survey.modules.survey.domain.service.form_template.exception.FormTemplateCreateException;
import com.otc.survey.modules.survey.domain.service.form_template.exception.FormTemplateElementSettingException;
import com.otc.survey.modules.survey.domain.service.form_template.exception.FormTemplateUpdateException;

@Service
public class FormTemplateService extends BaseService
{
	@Autowired
	protected FormTemplateSearchCommand searchFormTemplateCommand;
	
	@Autowired
	protected FormTemplateLoadToDoListCommand formTemplateLoadToDoListCommand;
	
	@Autowired
	protected FormTemplateLoadInfoCommand formTemplateLoadInfoCommand;
	
	@Autowired
	protected FormTemplateCreateCommand formTemplateCreateCommand;
	
	@Autowired
	protected FormTemplateUpdateCommand formTemplateUpdateCommand;
	
	@Autowired
	protected FormTemplateElementSettingCommand formTemplateElementSettingCommand;
	
	@Autowired
	protected FormTemplateNotificationCommand formTemplateNotificationCommand;
	
	
	// ****************************** searchFormTemplate ****************************** //
	public FormTemplateSearchCommand.Response searchFormTemplate(Consumer<FormTemplateSearchCommand.Request> consumer)
	{
		FormTemplateSearchCommand.Request request = new FormTemplateSearchCommand.Request();
		consumer.accept(request);
		return searchFormTemplate(request);
	}
	
	public FormTemplateSearchCommand.Response searchFormTemplate(FormTemplateSearchCommand.Request request)
	{
		logger.info("### {}.searchFormTemplate ###", getClass().getSimpleName());
		return searchFormTemplateCommand.searchFormTemplate(request);
	}
	// ****************************** searchFormTemplate ****************************** //
	
	
	// ****************************** loadToDoListTemplate ****************************** //
	public FormTemplateLoadToDoListCommand.Response loadToDoListTemplate(Consumer<FormTemplateLoadToDoListCommand.Request> consumer)
	{
		FormTemplateLoadToDoListCommand.Request request = new FormTemplateLoadToDoListCommand.Request();
		consumer.accept(request);
		return loadToDoListTemplate(request);
	}
	
	public FormTemplateLoadToDoListCommand.Response loadToDoListTemplate(FormTemplateLoadToDoListCommand.Request request)
	{
		logger.info("### {}.loadToDoListTemplate ###", getClass().getSimpleName());
		return formTemplateLoadToDoListCommand.loadToDoList(request);
	}
	// ****************************** loadToDoListTemplate ****************************** //
	
	
	// ****************************** loadFormTemplateInfo ****************************** //
	public FormTemplateLoadInfoCommand.Response loadFormTemplateInfo(Consumer<FormTemplateLoadInfoCommand.Request> consumer)
	{
		FormTemplateLoadInfoCommand.Request request = new FormTemplateLoadInfoCommand.Request();
		consumer.accept(request);
		return loadFormTemplateInfo(request);
	}
	
	public FormTemplateLoadInfoCommand.Response loadFormTemplateInfo(FormTemplateLoadInfoCommand.Request request)
	{
		logger.info("### {}.loadFormTemplateInfo ###", getClass().getSimpleName());
		return formTemplateLoadInfoCommand.loadTemplateInfo(request);
	}
	// ****************************** loadFormTemplateInfo ****************************** //
	
	
	// ****************************** createFormTemplate ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public FormTemplateCreateCommand.Response createFormTemplate(Consumer<FormTemplateCreateCommand.Request> consumer) throws FormTemplateCreateException
	{
		FormTemplateCreateCommand.Request request = new FormTemplateCreateCommand.Request();
		consumer.accept(request);
		return createFormTemplate(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public FormTemplateCreateCommand.Response createFormTemplate(FormTemplateCreateCommand.Request request) throws FormTemplateCreateException
	{
		logger.info("### {}.createFormTemplate ###", getClass().getSimpleName());
		return formTemplateCreateCommand.createFormTemplate(request);
	}
	// ****************************** createFormTemplate ****************************** //
	
	
	// ****************************** updateFormTemplate ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public FormTemplateUpdateCommand.Response updateFormTemplate(Consumer<FormTemplateUpdateCommand.Request> consumer) throws FormTemplateUpdateException
	{
		FormTemplateUpdateCommand.Request request = new FormTemplateUpdateCommand.Request();
		consumer.accept(request);
		return updateFormTemplate(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public FormTemplateUpdateCommand.Response updateFormTemplate(FormTemplateUpdateCommand.Request request) throws FormTemplateUpdateException
	{
		logger.info("### {}.updateFormTemplate ###", getClass().getSimpleName());
		return formTemplateUpdateCommand.updateFormTemplate(request);
	}
	// ****************************** updateFormTemplate ****************************** //
	
	
	// ****************************** setElement ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public FormTemplateElementSettingCommand.Response setElement(Consumer<FormTemplateElementSettingCommand.Request> consumer) throws FormTemplateElementSettingException
	{
		FormTemplateElementSettingCommand.Request request = new FormTemplateElementSettingCommand.Request();
		consumer.accept(request);
		return setElement(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public FormTemplateElementSettingCommand.Response setElement(FormTemplateElementSettingCommand.Request request) throws FormTemplateElementSettingException
	{
		logger.info("### {}.setElement ###", getClass().getSimpleName());
		return formTemplateElementSettingCommand.setElement(request);
	}
	// ****************************** setElement ****************************** //
	
	
	// ****************************** sendNotification ****************************** //
	public FormTemplateNotificationCommand.Response sendNotification(Consumer<FormTemplateNotificationCommand.Request> consumer)
	{
		FormTemplateNotificationCommand.Request request = new FormTemplateNotificationCommand.Request();
		consumer.accept(request);
		return sendNotification(request);
	}
	
	public FormTemplateNotificationCommand.Response sendNotification(FormTemplateNotificationCommand.Request request)
	{
		logger.info("### {}.sendNotification ###", getClass().getSimpleName());
		return formTemplateNotificationCommand.sendNotification(request);
	}
	// ****************************** sendNotification ****************************** //
}