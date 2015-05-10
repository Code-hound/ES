package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.service.CreateUserService;
import pt.tecnico.bubbledocs.service.RemoveUserService;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.InvalidUsernameException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistsException;

import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;

/*
 * CREATE USER
 * 
 * Cria um novo utilizador.
 * 
 * 1) O campo password passa a nao existir.
 * 
 * @author: Francisco Silveira
 * @author: Francisco Maria Calisto
 * 
 */

public class CreateUserIntegrator extends BubbleDocsIntegrator {

	private String userToken;
	private String newUsername;
	private String email;
	private String name;

	public CreateUserIntegrator(String userToken, String newUsername, String email, String name) {
		this.userToken = userToken;
		this.newUsername = newUsername;
		this.email = email;
		this.name = name;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		CreateUserService createUserService = new CreateUserService(this.userToken, this.newUsername, this.email , this.name);
		RemoveUserService removeUserService = new RemoveUserService(this.userToken, this.newUsername);
		IDRemoteServices  localService      = new IDRemoteServices();
		
		//throws UnauthorizedOperationException
		//throws UserAlreadyExistsException
		createUserService.execute();
		
		//throws UnavailableServiceException
		try {
			localService.createUser(this.newUsername, this.email);
		} catch (RemoteInvocationException e) {
			//throws UserNotInSessionException
			//throws UnavailableServiceException
			//throws LoginBubbleDocsException
			removeUserService.execute();
			throw new UnavailableServiceException();
		} catch (LoginBubbleDocsException e) {
			//throws UserNotInSessionException
			//throws UnavailableServiceException
			//throws LoginBubbleDocsException
			removeUserService.execute();
			throw new LoginBubbleDocsException(this.newUsername);
		}

	}
	
	public String getUsername () {
		return this.newUsername;
	}

}
