package com.otc.survey.modules.core.domain.model.line.messaging.message;

import java.util.List;

import lombok.Data;

@Data
public class LineReplyMessageResponse 
{
	protected List<SentMessage> sentMessages;
	
	
	@Data
	public static class SentMessage
	{
		protected String id;
		protected String quoteToken;
	}
}