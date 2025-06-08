package com.otc.survey.modules.common.endpoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.otc.survey.modules.common.endpoint.ResponseMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AbstractJsonBodyAPI<REQ extends AbstractJsonBodyAPI.BaseRequestMessage, RES extends AbstractJsonBodyAPI.BaseBodyResponseMessage> extends BaseAPI
{
	@PostMapping
	public ResponseEntity<ResponseMessage<RES>> execute(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) REQ requestMessage)
	{
		logger.info("### {}.execute ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		return doExecute(request, response, requestMessage);
	}
	
	protected abstract ResponseEntity<ResponseMessage<RES>> doExecute(HttpServletRequest request, HttpServletResponse response, @RequestBody REQ requestMessage);
	
	
	public abstract static class BaseRequestMessage
	{
		
	}
	
	public abstract static class BaseBodyResponseMessage extends ResponseMessage.BaseBody
	{
		
	}
}