package com.otc.survey.modules.core.domain.model;

import com.otc.survey.modules.common.domain.constant.AppPlatform;

import lombok.Data;

@Data
public class ClientAppInfo 
{
	protected String name;
	protected String version;
	protected AppPlatform platform;
}