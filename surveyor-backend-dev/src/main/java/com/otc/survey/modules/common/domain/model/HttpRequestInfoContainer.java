package com.otc.survey.modules.common.domain.model;

public class HttpRequestInfoContainer 
{
	private static InheritableThreadLocal<HttpRequestInfo> container = new InheritableThreadLocal<>();
	
	private HttpRequestInfoContainer() {}
	
	public static HttpRequestInfo get() {
		return container.get();
	}
	
	public static void set(HttpRequestInfo requestInfo) {
		container.set(requestInfo);
	}
}