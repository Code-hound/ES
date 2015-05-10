package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.service.LoginUserService;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;

/*
 * Login User Integrator
 * 
 * Recebe o nome de utilizador e a password
 * Cria uma nova sessao para o utilizador caso a autenticacao
 * esteja correcta
 * 
 * @author: Francisco Silveira
 * @author: Aline Caliente
 * @author: Luis Ribeiro Gomes
 * 
 */

public class LoginUserIntegrator extends BubbleDocsIntegrator {
	
	private String username;
	private String password;

	private String userToken;
	
	public LoginUserIntegrator (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUserToken() {
		return this.userToken;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {

		LoginUserService localService = new LoginUserService(this.username, this.password);

		//throws InvalidUserException
		//throws WrongPasswordException
		localService.execute();

		IDRemoteServices remoteService = new IDRemoteServices();

		//throws LoginBubbleDocsException
		//throws UnavailableServiceException
		try {
			remoteService.loginUser(this.username, this.password);
		} catch (LoginBubbleDocsException e) {
			localService.revert();
			throw new LoginBubbleDocsException(this.username);
		} catch (RemoteInvocationException e) {
			localService.revert();
			throw new UnavailableServiceException();
		}

		this.userToken = localService.getUserToken();
	}
}
