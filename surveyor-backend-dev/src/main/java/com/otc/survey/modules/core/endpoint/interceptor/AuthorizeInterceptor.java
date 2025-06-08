package com.otc.survey.modules.core.endpoint.interceptor;

import java.io.IOException;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import com.otc.survey.modules.common.domain.annotation.Interceptor;
import com.otc.survey.modules.common.endpoint.interceptor.BaseInterceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(4)
@Interceptor(interceptPaths = {".*/api/.*"})
public class AuthorizeInterceptor extends BaseInterceptor
{
	@Override
	protected void intercept(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		if(hasAuthorized(request)) {
			getRequestInfo(request).setAuthorized(true);
			filterChain.doFilter(request, response);
		} else {
			logger.error("Request Denied, Requestor not authorized to access this endpoint");
			
			replyErrorMessage(
					request, 
					response, 
					HttpStatus.FORBIDDEN, 
					"403001", 
					"Request Deined", 
					"You are not authorized to access this endpoint"
				);
			
			return;
		}
	}
	
	protected boolean hasAuthorized(HttpServletRequest request) 
	{
		List<String> requestorRoleIds = getRequestorRoleIds(request);
		if(requestorRoleIds == null || requestorRoleIds.isEmpty()) {
			return false;
		}
		
		List<String> grantedRoleIds = getGrantedRoleIds(request);
		if(requestorRoleIds == null || requestorRoleIds.isEmpty()) {
			return false;
		}
		
		for(String requestorRoleId : requestorRoleIds) {
			if(grantedRoleIds.contains(requestorRoleId)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	protected boolean shouldIntercept(HttpServletRequest request) throws ServletException {
		return super.shouldIntercept(request) && shouldAuthen(request) && shouldAuthorize(request);
	}
}