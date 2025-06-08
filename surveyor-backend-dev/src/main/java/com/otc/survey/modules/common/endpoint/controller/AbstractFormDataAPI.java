package com.otc.survey.modules.common.endpoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.otc.survey.modules.common.endpoint.ResponseMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AbstractFormDataAPI<REQ extends AbstractFormDataAPI.BaseRequestMessage, RES extends AbstractFormDataAPI.BaseBodyResponseMessage> extends BaseAPI
{
	public ResponseEntity<ResponseMessage<RES>> execute(HttpServletRequest request, HttpServletResponse response, @ModelAttribute REQ requestMessage)
	{
		logger.info("### {}.execute ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		return doExecute(request, response, requestMessage);
	}
	
	protected abstract ResponseEntity<ResponseMessage<RES>> doExecute(HttpServletRequest request, HttpServletResponse response, @ModelAttribute REQ requestMessage);
	
	
	public abstract static class BaseRequestMessage
	{
		
	}
	
	public abstract static class BaseBodyResponseMessage extends ResponseMessage.BaseBody
	{
		
	}
}