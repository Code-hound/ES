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
	
	public LoginUserIntegrator (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {

		LoginUserService     localService  = new LoginUserService(this.username, this.password);
		IDRemoteServices     remoteService = new IDRemoteServices();

		try {
			
			//catches RemoteInvocationException
			//catches LoginBubbleDocsException
			remoteService.loginUser(this.username, this.password);


		} catch ( RemoteInvocationException | LoginBubbleDocsException e ) {

			//throws InvalidUserException
			//throws UnavailableServiceException
			localService.execute();

		}

	}
}
