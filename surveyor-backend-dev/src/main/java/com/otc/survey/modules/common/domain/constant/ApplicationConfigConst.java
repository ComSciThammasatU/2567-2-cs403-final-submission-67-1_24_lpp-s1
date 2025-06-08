package com.otc.survey.modules.common.domain.constant;

public interface ApplicationConfigConst 
{
	public static final String APP_CONFIG_PREFIX = "survey";
	public static final String BASE_PACKAGE_NAME = "com.otc.survey";
	
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_TIMEZONE = "Asia/Bangkok";
	
	public static final String HTTP_HEADER_ACCESS_TOKEN = APP_CONFIG_PREFIX + "-access-token";
	public static final String HTTP_HEADER_TRACE_ID = APP_CONFIG_PREFIX + "-trace-id";
	public static final String HTTP_HEADER_REQUEST_ID = APP_CONFIG_PREFIX + "-request-id";
	
	public static final String HTTP_PARAM_ACCESS_TOKEN = APP_CONFIG_PREFIX + "-access-token";
	
	public static final String HTTP_QS_ACCESS_TOKEN = APP_CONFIG_PREFIX + "-access-token";
	
	public static final String HTTP_REQ_ATTR_ENDPOINT_TYPE = "http.request.attribute.endpoint-type";
	public static final String HTTP_REQ_ATTR_TRACE_ID = "http.request.attribute.traceId";
	public static final String HTTP_REQ_ATTR_REQUEST_ID = "http.request.attribute.requestId";
	public static final String HTTP_REQ_ATTR_ACCESS_TOKEN = "http.request.attribute.accessToken";
	public static final String HTTP_REQ_ATTR_SECRET_TICKET_TYPE = "http.request.attribute.secretTicketType";
	public static final String HTTP_REQ_ATTR_JWT_PAYLOAD = "http.request.attribute.jwtPayload";
	public static final String HTTP_REQ_ATTR_USER_PROFILE = "http.request.attribute.userProfile";
	public static final String HTTP_REQ_ATTR_AUTHEN_METHOD = "http.request.attribute.authenMethod";
	public static final String HTTP_REQ_ATTR_AUTH_SESSION = "http.request.attribute.authSession";
	public static final String HTTP_REQ_ATTR_CURRENT_APP_FEATURE = "http.request.attribute.currentAppFeature";
	public static final String HTTP_REQ_ATTR_CURRENT_PROGRAM = "http.request.attribute.currentProgram";
    public static final String HTTP_REQ_ATTR_CURRENT_API = "http.request.attribute.currentAPI";
    public static final String HTTP_REQ_ATTR_CURRENT_WEB = "http.request.attribute.currentWeb";
    public static final String HTTP_REQ_ATTR_ALL_GRANTED_AUTHORITY = "http.request.attribute.allGrantedAuthority";
    public static final String HTTP_REQ_ATTR_CURRENT_GRANTED_AUTHORITY = "http.request.attribute.currentGrantedAuthority";
	
	public static final String KEY_APP_NAME = APP_CONFIG_PREFIX + ".app.name";
	public static final String KEY_APP_VERSION = APP_CONFIG_PREFIX + ".app.version";
	public static final String KEY_APP_RUNNING_MODE = APP_CONFIG_PREFIX + ".app.running-mode";
	public static final String KEY_APP_STARTUP_ID = APP_CONFIG_PREFIX + ".app.startup-id";
	
	public static final String KEY_CRYPTO_ALGORITHM= APP_CONFIG_PREFIX + ".crypto.algorithm";
	public static final String KEY_CRYPTO_SECRET_KEY = APP_CONFIG_PREFIX + ".crypto.secret-key";
	public static final String KEY_CRYPTO_SECRET_SALT = APP_CONFIG_PREFIX + ".crypto.secret-salt";
	public static final String KEY_CRYPTO_JWT_SECRET = APP_CONFIG_PREFIX + ".crypto.jwt-secret";
	public static final String KEY_CRYPTO_JWT_DEFAULT_EXPIRED_PERIOD = APP_CONFIG_PREFIX + ".crypto.jwt-default-expired-period";
}