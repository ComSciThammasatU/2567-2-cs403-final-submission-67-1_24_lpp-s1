package com.otc.survey.modules.core.domain.service.staff;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.service.staff.dto.StaffInfo;

import lombok.Data;

@ServiceCommand
public class NotifyStaffRegistrationCommand extends BaseServiceCommand
{
	public Response notifyRegistration(Request request)
	{
		logger.info("### {}.notifyRegistration ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected StaffInfo staffInfo;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		
	}
}