package com.otc.survey.modules.core.domain.model.line.messaging.message;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinePushMessageRequest 
{
	protected String to;
	//protected List<? extends LineMessage> messages;
	protected List<? extends LineTextMessage> messages;
	protected boolean notificationDisabled;
}