package com.otc.survey.modules.common.endpoint;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otc.survey.modules.common.domain.model.HttpRequestInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage <B extends ResponseMessage.Body>
{
	protected Head head;
	protected B body;
	
	
	public void setHeadMessage(Consumer<ResponseMessage.Head> consumer)
    {
		ResponseMessage.Head head = new ResponseMessage.Head();
        consumer.accept(head);
        setHead(head);
    }
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Head
	{
		protected String traceId;
		protected String requestId;
		protected Status status;
		protected HttpRequestInfo requestInfo;
		protected List<Metadata> metadatas;
		protected Map<String, Object> payload;
		
		
		@Data
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class Status
		{
			protected Type type;
			protected String code;
			protected String title;
			protected String message;
			
			public static enum Type
		    {
		    	Success, Error
		    }
		}
		
		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Metadata
		{
			protected String attributeName;
			protected Object attributeValue;
		}
	}
	
	
	public static interface Body
	{
		
	}
	
	public static abstract class BaseBody implements Body
	{
		
	}
}