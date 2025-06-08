package com.otc.survey.modules.core.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.survey.modules.common.domain.repository.SurveyJpaRepository;
import com.otc.survey.modules.core.domain.constant.UserTypeConst;
import com.otc.survey.modules.core.domain.entity.User;

@Repository
public interface UserRepository extends SurveyJpaRepository<User, String>
{
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
	
	public List<User> findByUserTypeId(String userTypeId);
	
	public default List<User> findAllProfessors() {
		return findByUserTypeId(UserTypeConst.ID_PROFESSOR);
	}

	public default List<User> findAllStaffs() {
		return findByUserTypeId(UserTypeConst.ID_STAFF);
	}
	
	public default List<User> findAllStudents() {
		return findByUserTypeId(UserTypeConst.ID_STUDENT);
	}
}