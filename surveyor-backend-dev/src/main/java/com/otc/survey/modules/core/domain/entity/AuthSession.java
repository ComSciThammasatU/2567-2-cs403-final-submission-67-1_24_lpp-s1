package com.otc.survey.modules.core.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otc.survey.modules.common.domain.constant.AppPlatform;
import com.otc.survey.modules.common.domain.constant.ApplicationConfigConst;
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
@Table(name = "core_t_auth_session")
@Data
public class AuthSession extends BaseEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "client_app_name")
	protected String clientAppName;
	
	@Column(name = "client_app_version")
	protected String clientAppVersion;
	
	@Column(name = "client_app_platform")
	@Enumerated(EnumType.STRING)
	protected AppPlatform clientAppPlatform;
	
	@Column(name = "client_user_agent")
	protected String clientUserAgent;
	
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "access_token")
	protected String accessToken;
	
	@Column(name = "session_status")
	@Enumerated(EnumType.STRING)
	protected SessionStatus sessionStatus;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date createdAt;
	
	@Column(name = "last_verified_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date lastVerifiedAt;
	
	@Column(name = "last_accessed_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date lastAccessedAt;
	
	@Column(name = "expired_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = ApplicationConfigConst.DEFAULT_DATETIME_FORMAT, timezone = ApplicationConfigConst.DEFAULT_TIMEZONE)
	protected Date expiredAt;
	
	
	public static enum SessionStatus
	{
		Pending, Activated, Blocked, Closed
	}
}