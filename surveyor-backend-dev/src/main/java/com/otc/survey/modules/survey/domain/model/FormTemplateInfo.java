package com.otc.survey.modules.survey.domain.model;

import java.util.List;

import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.entity.FormTemplateAuthority;
import com.otc.survey.modules.survey.domain.entity.FormTemplateElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormTemplateInfo 
{
	protected FormTemplate formTemplate;
	protected List<FormTemplateElement> formTemplateElements;
	protected List<FormTemplateAuthority> formTemplateAuthorities;
}