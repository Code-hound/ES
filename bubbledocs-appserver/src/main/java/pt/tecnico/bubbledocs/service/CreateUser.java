package pt.tecnico.bubbledocs.service;

// the needed import declarations

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.InvalidUsernameException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistsException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

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

public class CreateUser extends BubbleDocsService {

	private String userToken;
	private String newUsername;
	private String email;
	private String name;

	public CreateUser(String userToken, String newUsername, String email, String name) {
		this.userToken = userToken;
		this.newUsername = newUsername;
		this.email = email;
		this.name = name;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		IDRemoteServices service = new IDRemoteServices();
		String username = resetUserLastAccess(userToken);
		
		//throws UnauthorizedOperationException
		if (checkIfRoot(userToken)){
		
		//throws UnavailableServiceException
			try {
				service.createUser(this.newUsername, this.email);
			} catch (RemoteInvocationException e) {
				throw new UnavailableServiceException();
			}
	
			getBubbleDocs().createUser(this.newUsername, this.email, this.name, this.email);
	
			//throws UserAlreadyExistsException
			if (getUser(this.newUsername) == null)
				throw new UserAlreadyExistsException(this.newUsername);
		}
	}
	
	public String getUsername () {
		return this.newUsername;
	}
}
