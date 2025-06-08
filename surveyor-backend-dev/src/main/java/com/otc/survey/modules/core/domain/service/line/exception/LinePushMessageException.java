package com.otc.survey.modules.core.domain.service.line.exception;

import com.otc.survey.modules.core.domain.model.line.messaging.message.LinePushMessageResponse;

public class LinePushMessageException extends RuntimeException
{
	protected LinePushMessageResponse.Error errorResponse;
	
	public LinePushMessageException(LinePushMessageResponse.Error errorResponse) {
		super(errorResponse.getErrorMessage());
		this.errorResponse = errorResponse;
	}
	
	public LinePushMessageResponse.Error getErrorResponse() {
		return errorResponse;
	}
}