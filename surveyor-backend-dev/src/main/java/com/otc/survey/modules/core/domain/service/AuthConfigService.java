package com.otc.survey.modules.core.domain.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

@Service
public class AuthConfigService 
{
	public long getSessionExpiredDuration()
	{
		return Duration.ofDays(7).toMillis();
	}
}