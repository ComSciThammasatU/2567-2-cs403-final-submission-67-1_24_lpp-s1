package com.otc.survey.modules.core.endpoint.controller.line;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.model.line.messaging.event.LineMessagingEvent;
import com.otc.survey.modules.core.domain.model.line.messaging.event.LineMessagingEvent.EventType;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineMessage;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineMessage.MessageType;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineReplyMessageRequest;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineStickerMessage;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineTextMessage;
import com.otc.survey.modules.core.domain.repository.LineAccountRepository;
import com.otc.survey.modules.core.domain.service.line.LineService;

import lombok.Data;

@ServiceCommand
public class ProcessLineMessagingEventCommand extends BaseServiceCommand
{
	@Autowired
	protected LineService lineService;
	
	@Autowired
	protected LineAccountRepository lineAccountRepository;
	
	@Autowired
	protected ThreadPoolTaskExecutor taskExecutor;
	
	
	public Response processMessagingEvent(Request request)
	{
		logger.info("### {}.processMessagingEvent ###", getClass().getSimpleName());
		
		if(request.getEvents() == null || request.getEvents().isEmpty()) {
			throw new IllegalArgumentException("Param 'events' couldn't be null or empty");
		}
		
		taskExecutor.submit(() -> executePersistLineAccount(request));
		
		for(LineMessagingEvent event : request.getEvents()) {
			
			if(event.getType() == EventType.message) {
				
				switch (event.getMessage().getType()) {
					case MessageType.text -> {
						taskExecutor.submit(() -> {
							List<? extends LineMessage> messages = List.of(
									LineTextMessage
										.builder()
										.text(event.getMessage().getText() + " : " + request.getPerformedAt())
										.build()
								);
							
							replyMessage(request, event, messages);
						});
					}
					
					case MessageType.sticker -> {
						taskExecutor.submit(() -> {
							List<? extends LineMessage> messages = List.of(
									LineStickerMessage
										.builder()
										.packageId(event.getMessage().getPackageId())
										.stickerId(event.getMessage().getStickerId())
										.build()
								);
							
							replyMessage(request, event, messages);
						});
					}
					
					default -> {
						taskExecutor.submit(() -> {
							List<? extends LineMessage> messages = List.of(
									LineTextMessage
										.builder()
										.text("Hello : " + request.getPerformedAt())
										.build()
								);
							
							replyMessage(request, event, messages);
						});
					}
				}
			}
		}
		
		Response response = new Response();
		
		return response;
	}
	
	protected void replyMessage(Request request, LineMessagingEvent event, List<? extends LineMessage> messages)
	{
		lineService.replyMessage(req -> {
			req.copyFrom(request);
			
			LineReplyMessageRequest replyMessage = LineReplyMessageRequest
					.builder()
					.replyToken(event.getReplyToken())
					.messages(messages)
					.build();
			
			req.setReplyMessage(replyMessage);
		});
	}
	
	protected void executePersistLineAccount(Request request)
	{
		logger.info("### {}.executePersistLineAccount ###", getClass().getSimpleName());
		
		for(LineMessagingEvent event : request.getEvents()) {
			String lineUserId = event.getSource().getUserId();
			
			if(lineUserId == null || lineUserId.isBlank()) {
				continue;
			}
			
			if(! lineAccountRepository.existsByLineUserId(lineUserId)) {
				lineService.createLineAccount(req -> {
					req.copyFrom(request);
					req.setLineUserId(lineUserId);
				});
			}
		}
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected List<? extends LineMessagingEvent> events;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		
	}
}