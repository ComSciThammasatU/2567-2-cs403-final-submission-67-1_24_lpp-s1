package com.otc.survey.modules.survey.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otc.survey.modules.common.domain.constant.ApplicationConfigConst;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "sv_t_form_document_element")
@Data
public class FormDocumentElement extends BaseEntity 
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "document_id")
	protected String documentId;
	
	@Column(name = "template_id")
	protected String templateId;
	
	@Column(name = "template_element_id")
	protected String templateElementId;
	
	@Column(name = "element_type_id")
	protected String elementTypeId;
	
	@Column(name = "element_value")
	protected String elementValue;
	
	@Column(name = "order_no")
	protected int orderNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected StatusCode status;
	
	@Column(name = "created_by")
	protected String createdBy;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date createdAt;
	
	@Column(name = "last_modified_by")
	protected String lastModifiedBy;
	
	@Column(name = "last_modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date lastModifiedAt;
}