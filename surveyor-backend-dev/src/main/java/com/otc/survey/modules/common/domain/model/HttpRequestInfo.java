package com.otc.survey.modules.common.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.otc.survey.modules.common.domain.constant.EndpointType;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.model.UserProfile;

import lombok.Data;

@Data
public class HttpRequestInfo 
{
	protected String traceId;
	protected String requestId;
	protected Date requestedAt;
	protected String userAgent;
	protected EndpointType endpointType;
	protected List<String> executedInterceptors = new ArrayList<>();
	protected String targetController;
	protected String executedController;
	protected AuthSession authSession;
	protected UserProfile userProfile;
	protected Boolean authenticated;
	protected Boolean authorized;
}