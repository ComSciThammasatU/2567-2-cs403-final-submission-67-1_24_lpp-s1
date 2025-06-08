package com.otc.survey.application.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.otc.survey.modules.core.domain.repository.ConfigRepository;

//@Component
public class ConfigRepositoryTestRunner implements CommandLineRunner
{
	@Autowired
	protected ConfigRepository configRepository;
	
	
	@Override
	public void run(String... args) throws Exception 
	{
		System.out.println(configRepository.findAll());
	}
}