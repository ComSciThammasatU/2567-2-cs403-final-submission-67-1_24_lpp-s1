package com.otc.survey.modules.core.domain.service.user;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.survey.modules.common.domain.service.BaseService;
import com.otc.survey.modules.core.domain.service.user.exception.LoginException;
import com.otc.survey.modules.core.domain.service.user.exception.UserException;

@Service
public class UserService extends BaseService
{
	@Autowired
	protected BuildUserProfileCommand buildUserProfileCommand;
	
	@Autowired
	protected LoginCommand loginCommand;
	
	@Autowired
	protected LogoutCommand logoutCommand;
	
	@Autowired
	protected GeneratePasswordCommand generatePasswordCommand;
	
	
	// ****************************** buildUserProfile ****************************** //
	public BuildUserProfileCommand.Response buildUserProfile(Consumer<BuildUserProfileCommand.Request> consumer) throws UserException
	{
		BuildUserProfileCommand.Request request = new BuildUserProfileCommand.Request();
		consumer.accept(request);
		return buildUserProfile(request);
	}
	
	public BuildUserProfileCommand.Response buildUserProfile(BuildUserProfileCommand.Request request) throws UserException
	{
		logger.info("### {}.buildUserProfile ###", getClass().getSimpleName());
		return buildUserProfileCommand.buildUserProfile(request);
	}
	// ****************************** buildUserProfile ****************************** //
	
	
	// ****************************** login ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public LoginCommand.Response login(Consumer<LoginCommand.Request> consumer) throws LoginException, UserException
	{
		LoginCommand.Request request = new LoginCommand.Request();
		consumer.accept(request);
		return login(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public LoginCommand.Response login(LoginCommand.Request request) throws LoginException, UserException
	{
		logger.info("### {}.login ###", getClass().getSimpleName());
		return loginCommand.login(request);
	}
	// ****************************** login ****************************** //
	
	
	// ****************************** logout ****************************** //
	@Transactional(rollbackFor = Throwable.class)
	public LogoutCommand.Response logout(Consumer<LogoutCommand.Request> consumer) 
	{
		LogoutCommand.Request request = new LogoutCommand.Request();
		consumer.accept(request);
		return logout(request);
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public LogoutCommand.Response logout(LogoutCommand.Request request) 
	{
		logger.info("### {}.logout ###", getClass().getSimpleName());
		return logoutCommand.logout(request);
	}
	// ****************************** logout ****************************** //
	
	
	// ****************************** generatePassword ****************************** //
	public GeneratePasswordCommand.Response generatePassword(Consumer<GeneratePasswordCommand.Request> consumer)
	{
		GeneratePasswordCommand.Request request = new GeneratePasswordCommand.Request();
		consumer.accept(request);
		return generatePassword(request);
	}
	
	public GeneratePasswordCommand.Response generatePassword(GeneratePasswordCommand.Request request)
	{
		logger.info("### {}.generatePassword ###", getClass().getSimpleName());
		return generatePasswordCommand.generatePassword();
	}
	// ****************************** generatePassword ****************************** //
	
	
	/*
	public void signup();
	public void signin();
	public void signout();
	
	public void changePassword();
	 
	public void searchUser();
	public void loadUserById();
	public void createUser();
	public void updateUser();
	*/
}