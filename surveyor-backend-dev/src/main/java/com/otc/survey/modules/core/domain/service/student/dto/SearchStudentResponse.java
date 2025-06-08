package com.otc.survey.modules.core.domain.service.student.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchStudentResponse 
{
	protected List<StudentInfo> studentInfos;
}