package com.otc.survey.modules.core.domain.model.line;

import lombok.Data;

@Data
public class LineUserProfile 
{
	protected String userId;
	protected String email; // optional
	protected String displayName;
	protected String pictureUrl;
	protected String statusMessage;
	protected String language;
}