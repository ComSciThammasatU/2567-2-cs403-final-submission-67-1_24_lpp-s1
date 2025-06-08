package com.otc.survey.modules.core.domain.entity;

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
@Table(name = "core_m_line_account")
@Data
public class LineAccount extends BaseEntity 
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "line_user_id")
	protected String lineUserId;
	
	@Column(name = "line_account_name")
	protected String lineAccountName;
	
	@Column(name = "line_email")
	protected String lineEmail;
	
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