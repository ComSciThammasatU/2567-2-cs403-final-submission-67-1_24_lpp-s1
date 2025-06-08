package com.otc.survey.modules.core.domain.service;

import org.springframework.stereotype.Service;

import com.otc.survey.modules.common.domain.constant.StatusCode;

@Service
public class StaffConfigService {
	public StatusCode getInitialCreateStatus()
	{
		return StatusCode.Active;
	}
	
	
	public StatusCode getInitialRegisterStatus()
	{
		return StatusCode.Active;
	}
}
