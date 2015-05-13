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
	private String password;

	public CreateUserIntegrator(String userToken, String newUsername, String email, String name) {
		this.userToken = userToken;
		this.newUsername = newUsername;
		this.email = email;
		this.name = name;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		CreateUserService createUserService = new CreateUserService(this.userToken, this.newUsername, this.email , this.name);
		RemoveUserService rollbackUserService = new RemoveUserService(this.userToken, this.newUsername);
		IDRemoteServices  remoteService      = new IDRemoteServices();
		
		//throws UserNotInSessionException
		//throws UnauthorizedOperationException
		//throws UserAlreadyExistsException
		//throws EmptyEmailException
		//throws InvalidUsernameException
		createUserService.execute();
		
		//throws UnavailableServiceException
		//throws LoginBubbleDocsException
		try {
			
			//catches RemoteInvocationException
			//catches LoginBubbleDocsException
			this.password = remoteService.createUser(this.newUsername, this.email);

		} catch (LoginBubbleDocsException e) {

		//doesn't throw UserNotInSessionException
		//doesn't throw UnauthorizedOperationException
			rollbackUserService.execute();
			throw new LoginBubbleDocsException(this.newUsername);
		
		} catch (Exception e) {

			//doesn't throw UserNotInSessionException
			//doesn't throw UnauthorizedOperationException
				rollbackUserService.execute();
				throw new UnavailableServiceException();
		}

	}
	
	public String getUsername () {
		return this.newUsername;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

}
