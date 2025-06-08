package com.otc.survey.modules.core.domain.service.user;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.survey.modules.common.domain.annotation.ServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommand;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandRequest;
import com.otc.survey.modules.common.domain.service.BaseServiceCommandResponse;
import com.otc.survey.modules.core.domain.entity.AuthSession;
import com.otc.survey.modules.core.domain.entity.AuthSession.SessionStatus;
import com.otc.survey.modules.core.domain.entity.User;
import com.otc.survey.modules.core.domain.model.ClientAppInfo;
import com.otc.survey.modules.core.domain.model.UserProfile;
import com.otc.survey.modules.core.domain.repository.AuthSessionRepository;
import com.otc.survey.modules.core.domain.repository.UserRepository;
import com.otc.survey.modules.core.domain.service.AuthConfigService;
import com.otc.survey.modules.core.domain.service.user.exception.LoginException;
import com.otc.survey.modules.core.domain.service.user.exception.UserException;

import lombok.Data;

@ServiceCommand
public class LoginCommand extends BaseServiceCommand
{
	@Autowired
	protected AuthConfigService authConfigService;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected AuthSessionRepository authSessionRepository;
	
	
	public Response login(Request request) throws LoginException, UserException
	{
		logger.info("### {}.login ###", getClass().getSimpleName());
		
		User user = userRepository.findByUsername(request.getUsername());
		
		if(user == null) {
			throw new LoginException.InvalidUsername(request.getUsername());
		}
		
		if(! user.getPassword().equals(request.getPassword())) {
			throw new LoginException.InvalidPassword(request.getPassword());
		}
		
		UserProfile userProfile = userService.buildUserProfile(req -> {
			req.copyFrom(request);
			req.setUser(user);
		}).getUserProfile();
		logger.info("load userProfile => {}", userProfile);
		
		String accessToken = generateAccessToken(request);
		logger.info("generate accessToken => {}", accessToken);
		
		AuthSession authSession = new AuthSession();
		authSession.setId(UUID.randomUUID().toString());
		authSession.setClientAppName(request.getClientAppInfo().getName());
		authSession.setClientAppVersion(request.getClientAppInfo().getVersion());
		authSession.setClientAppPlatform(request.getClientAppInfo().getPlatform());
		authSession.setClientUserAgent(request.getRequestInfo().getUserAgent());
		authSession.setUserId(user.getId());
		authSession.setAccessToken(accessToken);
		authSession.setSessionStatus(SessionStatus.Activated);
		authSession.setCreatedAt(request.getPerformedAt());
		authSession.setLastVerifiedAt(request.getPerformedAt());
		authSession.setLastAccessedAt(request.getPerformedAt());
		authSession.setExpiredAt(new Date(request.getPerformedAt().getTime() + getSessionExpiredDuration(request)));
		authSessionRepository.save(authSession);
		logger.info("Create authSession => {}", authSession);
		
		Response response = new Response();
		response.setAccessToken(accessToken);
		response.setUserProfile(userProfile);
		response.setAuthSession(authSession);
		
		return response;
	}
	
	protected String generateAccessToken(Request request)
	{
		return UUID.randomUUID().toString();
	}
	
	protected long getSessionExpiredDuration(Request request)
	{
		return authConfigService.getSessionExpiredDuration();
	}
	
	
	@Data
	public static class Request extends BaseServiceCommandRequest
	{
		protected ClientAppInfo clientAppInfo;
		protected String username;
		protected String password;
	}
	
	
	@Data
	public static class Response extends BaseServiceCommandResponse
	{
		protected String accessToken;
		protected AuthSession authSession;
		protected UserProfile userProfile;
	}
}