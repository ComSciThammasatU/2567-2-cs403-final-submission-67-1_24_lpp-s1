package com.otc.survey.modules.core.domain.model.line.messaging.event;

import java.util.List;

import com.otc.survey.modules.core.domain.model.line.messaging.message.LineMessage.MessageType;
import com.otc.survey.modules.core.domain.model.line.messaging.message.LineMessage.StickerResourceType;

import lombok.Data;

@Data
public class LineMessagingEventMessagePayload
{
	protected String id;
	protected MessageType type;
	protected String quoteToken;
	
	protected String text;
	
	protected String stickerId;
	protected String packageId;
	protected StickerResourceType stickerResourceType;
	protected List<String> keywords;
}