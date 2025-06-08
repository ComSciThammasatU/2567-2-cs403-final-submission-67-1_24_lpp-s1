package com.otc.survey.modules.core.domain.model.line.messaging.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineStickerMessage extends LineMessage
{
	protected String packageId;
	protected String stickerId;
	
	
	@Override
	public MessageType getType() {
		return MessageType.sticker;
	}
}