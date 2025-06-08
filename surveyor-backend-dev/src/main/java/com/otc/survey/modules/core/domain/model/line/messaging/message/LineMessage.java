package com.otc.survey.modules.core.domain.model.line.messaging.message;

import lombok.Data;

@Data
public abstract class LineMessage 
{
	public abstract MessageType getType();
	
	
	public static enum MessageType
	{
		text, image, video, audio, file, location, sticker
	}
	
	public static enum StickerResourceType
	{
		STATIC, ANIMATION, SOUND, ANIMATION_SOUND, POPUP, POPUP_SOUND, CUSTOM, MESSAGE, NAME_TEXT, PER_STICKER_TEXT
	}
}