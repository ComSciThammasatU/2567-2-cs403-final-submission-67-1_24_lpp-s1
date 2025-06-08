package com.otc.survey.application.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SurveyAsyncExecutorConfig 
{
	public static final String BEAN_NAME = "surveyAsyncExecutor";
	
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Primary
	@Bean(SurveyAsyncExecutorConfig.BEAN_NAME)
	public ThreadPoolTaskExecutor surveyTaskExecutor()
	{
		logger.info("### {}.surveyTaskExecutor ###", getClass());
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		
		executor.setCorePoolSize(getCorePoolSize());
		executor.setMaxPoolSize(getMaxPoolSize());
		executor.setQueueCapacity(getQueueCapacity());
		executor.setThreadNamePrefix(getThreadNamePrefix());
		executor.setTaskDecorator(new TaskDecorator() {
			@Override
			public Runnable decorate(Runnable runnable) {
				Map<String, String> contextMap = MDC.getCopyOfContextMap();
				//String dbCluster = SpaceDataSourceContextHolder.getCluster();
				
		        return () -> {
		            try {
		            	if(contextMap != null) {
		            		MDC.setContextMap(contextMap);
		            	}
		            	
		                //SpaceDataSourceContextHolder.setCluster(dbCluster);
		                
		                runnable.run();
		            } finally {
		                MDC.clear();
		            }
		        };
			}
		});
		
		executor.initialize();
		
		logger.info("### Initial surveyTaskExecutor ###");
		logger.info("core-pool-size => {}", executor.getCorePoolSize());
		logger.info("max-pool-size => {}", executor.getMaxPoolSize());
		logger.info("queue-capacity => {}", executor.getQueueCapacity());
		logger.info("thread-name-prefix => {}", executor.getThreadNamePrefix());
		
		return executor;
	}
	
	protected int getCorePoolSize() {
		return 5;
	}
	
	public int getMaxPoolSize() {
		return 100;
	}
	
	public int getQueueCapacity() {
		return 20;
	}
	
	public String getThreadNamePrefix() {
		return "surveyor-exec-";
	}
}