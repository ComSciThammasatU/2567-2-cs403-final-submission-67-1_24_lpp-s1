package com.otc.survey.modules.common.domain.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.otc.survey.modules.common.domain.entity.BaseEntity;

import jakarta.persistence.EntityManager;

public class DefaultSurveyJpaRepository <E extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements SurveyJpaRepository<E, ID>
{
	protected JpaEntityInformation<E, ?> entityInformation;
	protected EntityManager entityManager;
	
	
	public DefaultSurveyJpaRepository(Class<E> domainClass, EntityManager em) 
	{
		super(domainClass, em);
		this.entityManager = em;
	}

	public DefaultSurveyJpaRepository(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) 
	{
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}
	
	@Override
	public JpaEntityInformation<E, ?> getEntityInformation() {
		return entityInformation;
	}
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void detach(E entity) {
		getEntityManager().detach(entity);
	}
}