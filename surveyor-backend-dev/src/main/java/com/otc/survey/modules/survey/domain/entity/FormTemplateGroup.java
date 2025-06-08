package com.otc.survey.modules.survey.domain.entity;

import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sv_m_form_template_group")
@Data
public class FormTemplateGroup extends BaseEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected StatusCode status;
}