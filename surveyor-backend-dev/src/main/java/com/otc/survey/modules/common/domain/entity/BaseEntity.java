package com.otc.survey.modules.common.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEntity
{

}