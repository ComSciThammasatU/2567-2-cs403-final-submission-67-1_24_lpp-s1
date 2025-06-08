package com.otc.survey.modules.core.endpoint.interceptor;

import java.io.IOException;

import org.springframework.core.annotation.Order;

import com.otc.survey.modules.common.domain.annotation.Interceptor;
import com.otc.survey.modules.common.endpoint.interceptor.BaseInterceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(Integer.MAX_VALUE)
@Interceptor(interceptPaths = {".*/api/.*"})
public class PreDispatchInterceptor extends BaseInterceptor
{
	@Override
	protected void intercept(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		if(getHandlerMethod(request) != null) {
			getRequestInfo(request).setExecutedController(getHandlerBean(request).getClass().getName() + "::" + getHandlerMethod(request).getMethod().getName());
		}
		
		filterChain.doFilter(request, response);
	}
}