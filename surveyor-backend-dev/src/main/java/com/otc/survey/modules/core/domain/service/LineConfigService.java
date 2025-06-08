package com.otc.survey.modules.core.domain.service;

import org.springframework.stereotype.Service;

@Service
public class LineConfigService 
{
	public String getLoginChannelId() {
		return "2006591771";
	}
	
	public String getLoginChannelSecret() {
		return "4dcb157d72eedf54159b27dca7b50886";
	}
	
	
	public String getMessagingAccountId() {
		return "@442lwtsv";
	}
	
	public String getMessagingChannelId() {
		return "2006670403";
	}
	
	public String getMessagingChannelSecret() {
		return "4355f45428fa80454daa2e47115a19be";
	}
	
	public String getMessagingChannelAccessToken() {
		return "Q13DGhvgcs5Ng34VFXrf2qMdJBQNFEVRn2XXGlU3SImyg5kKkAh3wwNC1bJDOAlbqhU7b8vypGhSEswtonyvJ9B/+BE1HevprYI4Lw1Pio9jRC+auBZbRZjnQQB0Q/8CYSK53FHJ0SF2vdbgMrffTAdB04t89/1O/w1cDnyilFU=";
	}
	
	
	public String getApiEndpointLoadUserProfile() {
		return "https://api.line.me/v2/bot/profile/${userId}";
	}
	
	public String getApiEndpointReplyMessage() {
		return "https://api.line.me/v2/bot/message/reply";
	}
	
	public String getApiEndpointPushMessage() {
		return "https://api.line.me/v2/bot/message/push";
	}
}