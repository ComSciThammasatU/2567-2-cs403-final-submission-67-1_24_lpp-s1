package com.otc.survey.modules.core.endpoint.interceptor;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import com.otc.survey.modules.common.domain.annotation.Interceptor;
import com.otc.survey.modules.common.domain.constant.EndpointType;
import com.otc.survey.modules.common.endpoint.interceptor.BaseInterceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(2)
@Interceptor
public class DefineEndpointInterceptor extends BaseInterceptor
{
	@Override
	protected void intercept(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		EndpointType endpointType = null;
		
		if(matchesAPIEndpoint(request)) {
			endpointType = EndpointType.API;
		} else if(matchesResourcesEndpoint(request)) {
			endpointType = EndpointType.RESOURCES;
		}
		
		logger.debug("endpointType => {}", endpointType);
		
		if(endpointType == null) {
			logger.error("Couldn't define endpoint type, Rejected request");
			
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.BAD_REQUEST, 
					"400001", 
					"Request Deined", 
					"This endpoint is not allow to access"
				);
			
			return;
		}
		
		if(endpointType == EndpointType.API) {
			if(getHandlerMethod(request) == null) {
				replyErrorMessage(
						request, 
						response, 
						HttpStatus.NOT_FOUND, 
						"404001", 
						"Endpoint Handler Not Found", 
						"No Controller Registered To Handle This Endpoint"
					);
			}
		}
		
		getRequestInfo(request).setEndpointType(endpointType);
		filterChain.doFilter(request, response);
	}
}