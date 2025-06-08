package com.otc.survey.application.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.otc.survey.modules.core.domain.model.line.messaging.message.LineMessage;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LinePushMessageRequest;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LinePushMessageResponse;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineTextMessage;
import com.otc.survey.modules.core.domain.service.line.LineService;

//@Component
public class LinePushMessageTestRunner implements CommandLineRunner
{
	@Autowired
	protected LineService lineService;
	
	
	@Override
	public void run(String... args) throws Exception 
	{
		System.out.println("### LinePushMessageTestRunner.run ###");
		
		List<? extends LineTextMessage> messages = List.of(
				LineTextMessage.builder().text("Alohaa").build(),
				LineTextMessage.builder().text("Bye").build()
			);
		
		LinePushMessageRequest pushMessage = LinePushMessageRequest
				.builder()
				.to("x")
				.messages(messages)
				.build();
		
		LinePushMessageResponse pushMessageResponse = lineService.pushMessage(req -> {
			req.setPushMessage(pushMessage);
		}).getPushMessageResponse();
		
		System.out.println("pushMessageResponse => " + pushMessageResponse);
	}
}