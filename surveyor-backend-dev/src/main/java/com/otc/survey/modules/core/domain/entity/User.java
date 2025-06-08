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
@Table(name = "core_m_user")
@Data
public class User extends BaseEntity 
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "username")
	protected String username;
	
	@Column(name = "password")
	protected String password;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "account_name")
	protected String accountName;
	
	@Column(name = "user_type_id")
	protected String userTypeId;
	
	@Column(name = "is_account_locked")
	protected boolean accountLocked;
	
	@Column(name = "is_password_locked")
	protected boolean passwordLocked;
	
	@Column(name = "is_force_change_password")
	protected boolean forceChangePassword;
	
	@Column(name = "login_fail_times")
	protected int loginFailTimes;
	
	@Column(name = "account_activated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date accountActivatedAt;
	
	@Column(name = "account_expired_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date accountExpiredAt;
	
	@Column(name = "password_expired_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date passwordExpiredAt;
	
	@Column(name = "profile_image_id")
	protected String profileImageId;
	
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