package com.otc.survey.modules.core.domain.model.line.messaging.message;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LinePushMessageResponse 
{
	@Data
	public static class Success extends LinePushMessageResponse
	{
		protected List<SentMessage> sentMessages;
	}
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Error extends LinePushMessageResponse
	{
		protected int statusCode;
		protected String statusMessage;
		protected String errorMessage;
	}
	
	
	@Data
	public static class SentMessage
	{
		protected String id;
		protected String quoteToken;
	}
}