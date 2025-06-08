package com.otc.survey.modules.core.domain.service.professor.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchProfessorResponse 
{
	protected List<ProfessorInfo> professorInfos;
}
