package com.otc.survey.modules.core.domain.repository;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.entity.Student;

@Repository
public interface StudentRepository extends SurveyJpaRepository<Student, String>
{
	public Student findByUserId(String userId);
	
	public Student findByStudentCode(String studentCode);
	
	public boolean existsByStudentCode(String studentCode);
}