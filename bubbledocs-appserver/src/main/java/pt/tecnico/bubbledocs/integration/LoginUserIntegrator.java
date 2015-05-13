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

		IDRemoteServices     remoteService = new IDRemoteServices();
		boolean save   = true;
		boolean verify = false;

		try {

			//catches RemoteInvocationException
			//catches LoginBubbleDocsException
			try {
			remoteService.loginUser(this.username, this.password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			LoginUserService     localService  = new LoginUserService(this.username, this.password, save);

			//throws InvalidUserException
			localService.execute();

			this.userToken = localService.getUserToken();

		} catch ( RemoteInvocationException | LoginBubbleDocsException e ) {

			LoginUserService     localService  = new LoginUserService(this.username, this.password, verify);

			//throws InvalidUserException
			//throws UnavailableServiceException
			localService.execute();

			this.userToken = localService.getUserToken();

		}
	
	}

}
