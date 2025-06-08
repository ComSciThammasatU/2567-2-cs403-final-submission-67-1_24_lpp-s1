package com.otc.survey.modules.common.domain.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.repository.NoRepositoryBean;

import com.otc.survey.modules.common.domain.entity.BaseEntity;

import jakarta.persistence.EntityManager;

@NoRepositoryBean
public interface SurveyJpaRepository <E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID>
{
	public JpaEntityInformation<E, ?> getEntityInformation();
	
	public EntityManager getEntityManager();
	
	public void detach(E entity);
}