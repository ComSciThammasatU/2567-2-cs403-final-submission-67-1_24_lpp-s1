package com.otc.survey.modules.core.endpoint.interceptor;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.core.annotation.Order;

import com.otc.survey.modules.common.domain.annotation.Interceptor;
import com.otc.survey.modules.common.endpoint.interceptor.BaseInterceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(1)
@Interceptor
public class LoggingInterceptor extends BaseInterceptor
{
	@Override
	protected void intercept(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		logger.info("Incoming Request {} : {}", request.getMethod(), request.getRequestURI());
		
		getRequestInfo(request).setTraceId(extractTraceId(request));
		getRequestInfo(request).setRequestId(UUID.randomUUID().toString());
		getRequestInfo(request).setRequestedAt(new Date());
		getRequestInfo(request).setUserAgent(extractUserAgent(request));
		
		if(getHandlerMethod(request) != null) {
			getRequestInfo(request).setTargetController(getHandlerBean(request).getClass().getName() + "::" + getHandlerMethod(request).getMethod().getName());
		}
		
		filterChain.doFilter(request, response);
		
		logger.info("Outgoing Request {} : {}", request.getMethod(), request.getRequestURI());
	}
	
	@Override
	protected String extractTraceId(HttpServletRequest request) {
		String traceId = super.extractTraceId(request);
		return (traceId != null && !traceId.isBlank()) ? traceId : UUID.randomUUID().toString();
	}
}