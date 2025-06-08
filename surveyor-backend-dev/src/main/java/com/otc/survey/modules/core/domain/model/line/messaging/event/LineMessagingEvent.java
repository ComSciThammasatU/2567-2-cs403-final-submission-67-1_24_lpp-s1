package com.otc.survey.modules.core.domain.model.line.messaging.event;

import lombok.Data;

@Data
public class LineMessagingEvent 
{
	protected String webhookEventId;
	protected Long timestamp;
	protected EventType type;
	protected Source source;
	protected Mode mode;
	protected DeliveryContext deliveryContext;
	
	protected String replyToken;
	protected LineMessagingEventFollowPayload follow;
	protected LineMessagingEventMessagePayload message;
	
	
	@Data
	public static class Source
	{
		protected EventSourceType type;
		protected String userId;
		protected String groupId;
		protected String roomId;
	}
	
	@Data
	public static class DeliveryContext
	{
		protected boolean redelivery;
	}
	
	
	public static enum EventType
	{
		message, follow, unfollow, join, leave, memberJoined, memberLeft, postback, beacon, accountLink, things
	}
	
	public static enum EventSourceType
	{
		user, group, room
	}
	
	public static enum Mode
	{
		active, standby
	}
}