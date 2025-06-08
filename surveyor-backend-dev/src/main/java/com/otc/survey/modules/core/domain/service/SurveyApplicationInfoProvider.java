package com.otc.survey.modules.core.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.otc.survey.modules.common.domain.constant.AppEnv;

@Service
public class SurveyApplicationInfoProvider 
{
	@Autowired
	protected Environment environment;
	
	public String getAppName() {
		return environment.getProperty("survey.app.name");
	}
	
	public String getAppVersion() {
		return environment.getProperty("survey.app.version");
	}
	
	public AppEnv getAppEnv() {
		return AppEnv.valueOf(environment.getProperty("survey.app.env"));
	}
}