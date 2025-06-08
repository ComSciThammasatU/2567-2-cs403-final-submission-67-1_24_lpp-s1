package com.otc.survey.modules.survey.domain.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otc.survey.modules.common.domain.constant.ApplicationConfigConst;
import com.otc.survey.modules.common.domain.constant.StatusCode;
import com.otc.survey.modules.common.domain.entity.BaseEntity;
import com.otc.survey.modules.common.domain.util.JsonUtil;

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
@Table(name = "sv_m_form_template_element")
@Data
public class FormTemplateElement extends BaseEntity 
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "template_id")
	protected String templateId;
	
	@Column(name = "element_code")
	protected String elementCode;
	
	@Column(name = "element_type_id")
	protected String elementTypeId;
	
	@Column(name = "order_no")
	protected int orderNo;
	
	@Column(name = "title")
	protected String title;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "property")
	protected String property;
	
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
	
	
	public ElementData getElementData()
	{
		if(property == null || property.isBlank()) {
			return null;
		}
		
		try {
			return JsonUtil.parse(property, ElementData.class);
		} catch(Exception ex) {
			return null;
		}
	}
	
	
	public static class ElementData extends HashMap<String, Object>
	{
		
	}
}