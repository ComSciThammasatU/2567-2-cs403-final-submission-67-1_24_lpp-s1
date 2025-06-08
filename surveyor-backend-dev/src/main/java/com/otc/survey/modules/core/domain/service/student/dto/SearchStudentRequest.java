package com.otc.survey.modules.core.domain.service.student.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchStudentRequest 
{
	protected Criteria criteria;
	
	
	@Data
	public static class Criteria
	{
		protected List<String> studentCodes;
		protected String studentName;
	}
}