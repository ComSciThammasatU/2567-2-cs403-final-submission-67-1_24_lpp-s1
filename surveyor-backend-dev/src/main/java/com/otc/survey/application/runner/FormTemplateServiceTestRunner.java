package com.otc.survey.application.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.survey.domain.entity.FormTemplate;
import com.otc.survey.modules.survey.domain.model.FormTemplateInfo;
import com.otc.survey.modules.survey.domain.repository.FormTemplateRepository;
import com.otc.survey.modules.survey.domain.service.form_template.FormTemplateService;
import com.otc.survey.modules.survey.domain.service.form_template.dto.FormTemplateSearchDTO;

//@Component
public class FormTemplateServiceTestRunner implements CommandLineRunner
{
	@Autowired
	protected FormTemplateRepository formTemplateRepository;
	
	@Autowired
	protected FormTemplateService formTemplateService;
	
	@Override
	public void run(String... args) throws Exception 
	{
		System.out.println("### FormTemplateServiceTestRunner.run ###");
		
		List<FormTemplate> formTemplates = formTemplateRepository.findAll();
		formTemplates.forEach(System.out::println);
		
		//testSearch();
	}
	
	protected void testSearch()
	{
		System.out.println("### FormTemplateServiceTestRunner.testSearch ###");
		
		List<FormTemplateInfo> formTemplateInfos = formTemplateService.searchFormTemplate(req -> {
			FormTemplateSearchDTO.RequestPayload.Criteria criteria = new FormTemplateSearchDTO.RequestPayload.Criteria();
			criteria.setTemplateGroupId("tg-001");
			criteria.setTemplateCode("001");
			criteria.setTemplateName("Form");
			criteria.setStatus(StatusCode.Active.name());
			
			FormTemplateSearchDTO.RequestPayload requestPayload = new FormTemplateSearchDTO.RequestPayload();
			requestPayload.setCriteria(criteria);
			
			req.setRequestPayload(requestPayload);
		}).getResponsePayload().getFormTemplateInfos();
		
		System.out.println("formTemplateInfos => " + formTemplateInfos);
		
		if(formTemplateInfos != null && !formTemplateInfos.isEmpty()) {
			formTemplateInfos.forEach(System.out::println);
		}
	}
}