package com.otc.survey.modules.common.domain.service;

import java.util.Date;

import com.otc.survey.modules.common.domain.constant.AppPlatform;
import com.otc.survey.modules.common.domain.model.HttpRequestInfo;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.entity.Program;
import com.otc.survey.modules.core.domain.model.UserProfile;

import lombok.Data;

@Data
public abstract class BaseServiceCommandRequest 
{
	protected AppPlatform performedPlatform;
	protected String performedAPI;
	protected AuthSession performedAuthSession;
	protected UserProfile performedUserProfile;
	//protected Program performedProgram;
	protected String performedTraceId;
	protected String performedRequestId;
	protected String performedBy;
	protected Date performedAt;
	protected HttpRequestInfo requestInfo;
	
	public void copyFrom(BaseServiceCommandRequest request)
	{
		performedPlatform = request.getPerformedPlatform();
		performedAPI = request.getPerformedAPI();
		performedAuthSession = request.getPerformedAuthSession();
		performedUserProfile = request.getPerformedUserProfile();
		//performedProgram = request.getPerformedProgram();
		performedTraceId = request.getPerformedTraceId();
		performedRequestId = request.getPerformedRequestId();
		performedBy = request.getPerformedBy();
		performedAt = request.getPerformedAt();
		requestInfo = request.getRequestInfo();
	}
}