package com.otc.survey.modules.core.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.survey.modules.common.domain.annotation.APIController;
import com.otc.survey.modules.common.domain.annotation.Auth;
import com.otc.survey.modules.common.domain.model.HttpRequestInfo;
import com.otc.survey.modules.common.endpoint.controller.BaseAPI;

import jakarta.servlet.http.HttpServletRequest;

@APIController
@RequestMapping("/api/core/health-check")
@Auth(authen = true)
public class HealthCheckAPI extends BaseAPI
{	
	@GetMapping
	public HttpRequestInfo healthCheck(HttpServletRequest request) {
		return getRequestInfo(request);
	}
	
	@GetMapping("/v2")
	@Auth(authen = false)
	public HttpRequestInfo healthCheckV2(HttpServletRequest request) {
		return getRequestInfo(request);
	}
	
	@GetMapping("/v3")
	@Auth(
		authen = true,
		authorize = true,
		grantedRoleIds = {
			"ADMIN_SYSTEM",
			"ADMIN_USER"
		}
	)
	public HttpRequestInfo healthCheckV3(HttpServletRequest request) {
		return getRequestInfo(request);
	}
}