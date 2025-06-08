package com.otc.survey.application;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "survey")
public class SurveyApplicationProperties 
{
	protected App app;
	protected Server server;
	protected Datasource datasource;
	protected List<String> activeProfiles;
	
	
	@Data
	public static class App
	{
		protected String name;
		protected String version;
		protected String env;
	}
	
	@Data
	public static class Server
	{
		protected String port;
		protected String resourcesPathPattern;
		protected String resourcesLocation;
	}
	
	
	@Data
	public static class Datasource
	{
		protected String host;
		protected String port;
		protected String schema;
		protected String options;
		protected String username;
		protected String password;
	}
}