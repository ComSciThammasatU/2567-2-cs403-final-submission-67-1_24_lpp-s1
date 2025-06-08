package com.otc.survey.modules.survey.domain.service.form_document;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.survey.modules.common.domain.service.BaseService;
import com.otc.survey.modules.survey.domain.service.form_document.exception.FormDocumentSubmitException;

@Service
public class FormDocumentService extends BaseService
{
	@Autowired
	protected FormDocumentBuildInfoCommand formDocumentBuildInfoCommand;
	
	@Autowired
	protected FormDocumentSearchCommand formDocumentSearchCommand;
	
	@Autowired
	protected FormDocumentLoadUserHistoryCommand formDocumentLoadUserHistoryCommand;
	
	@Autowired
	protected FormDocumentLoadInfoCommand formDocumentLoadInfoCommand;
	
	@Autowired
	protected FormDocumentSubmitCommand formDocumentSubmitCommand;
	
	
	// ****************************** buildDocumentInfo ****************************** //
	public FormDocumentBuildInfoCommand.Response buildDocumentInfo(Consumer<FormDocumentBuildInfoCommand.Request> consumer)
	{
		FormDocumentBuildInfoCommand.Request request = new FormDocumentBuildInfoCommand.Request();
		consumer.accept(request);
		return buildDocumentInfo(request);
	}
	
	public FormDocumentBuildInfoCommand.Response buildDocumentInfo(FormDocumentBuildInfoCommand.Request request)
	{
		logger.info("### {}.buildDocumentInfo ###", getClass().getSimpleName());
		return formDocumentBuildInfoCommand.buildDocumentInfo(request);
	}
	// ****************************** buildDocumentInfo ****************************** //
	
	
	// ****************************** searchDocument ****************************** //
	public FormDocumentSearchCommand.Response searchDocument(Consumer<FormDocumentSearchCommand.Request> consumer)
	{
		FormDocumentSearchCommand.Request request = new FormDocumentSearchCommand.Request();
		consumer.accept(request);
		return searchDocument(request);
	}
	
	public FormDocumentSearchCommand.Response searchDocument(FormDocumentSearchCommand.Request request)
	{
		logger.info("### {}.searchDocument ###", getClass().getSimpleName());
		return formDocumentSearchCommand.searchDocument(request);
	}
	// ****************************** searchDocument ****************************** //
	
	
	// ****************************** loadUserHistoryDocument ****************************** //
	public FormDocumentLoadUserHistoryCommand.Response loadUserHistoryDocument(Consumer<FormDocumentLoadUserHistoryCommand.Request> consumer)
	{
		FormDocumentLoadUserHistoryCommand.Request request = new FormDocumentLoadUserHistoryCommand.Request();
		consumer.accept(request);
		return loadUserHistoryDocument(request);
	}
	
	public FormDocumentLoadUserHistoryCommand.Response loadUserHistoryDocument(FormDocumentLoadUserHistoryCommand.Request request)
	{
		logger.info("### {}.loadUserHistoryDocument ###", getClass().getSimpleName());
		return formDocumentLoadUserHistoryCommand.loadUserHistoryDocument(request);
	}
	// ****************************** loadUserHistoryDocument ****************************** //
	
	
	// ****************************** loadDocumentInfo ****************************** //
	public FormDocumentLoadInfoCommand.Response loadDocumentInfo(Consumer<FormDocumentLoadInfoCommand.Request> consumer)
	{
		FormDocumentLoadInfoCommand.Request request = new FormDocumentLoadInfoCommand.Request();
		consumer.accept(request);
		return loadDocumentInfo(request);
	}
	
	public FormDocumentLoadInfoCommand.Response loadDocumentInfo(FormDocumentLoadInfoCommand.Request request)
	{
		logger.info("### {}.loadDocumentInfo ###", getClass().getSimpleName());
		return formDocumentLoadInfoCommand.loadInfo(request);
	}
	// ****************************** loadDocumentInfo ****************************** //
	
	
	// ****************************** saveDraftDocument ****************************** //
	// ****************************** saveDraftDocument ****************************** //
	
	
	// ****************************** submitDocument ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public FormDocumentSubmitCommand.Response submitDocument(Consumer<FormDocumentSubmitCommand.Request> consumer) throws FormDocumentSubmitException
	{
		FormDocumentSubmitCommand.Request request = new FormDocumentSubmitCommand.Request();
		consumer.accept(request);
		return submitDocument(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public FormDocumentSubmitCommand.Response submitDocument(FormDocumentSubmitCommand.Request request) throws FormDocumentSubmitException
	{
		logger.info("### {}.submitDocument ###", getClass().getSimpleName());
		return formDocumentSubmitCommand.submit(request);
	}
	// ****************************** submitDocument ****************************** //
}