package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistsException;

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

public class CreateUserService extends BubbleDocsService {

	private String userToken;
	private String newUsername;
	private String email;
	private String name;

	public CreateUserService(String userToken, String newUsername, String email, String name) {
		this.userToken = userToken;
		this.newUsername = newUsername;
		this.email = email;
		this.name = name;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		String username = resetUserLastAccess(userToken);
		
		//throws UnauthorizedOperationException
		//throws UserNotInSessionException
		checkIfRoot(userToken);

		//throws UserAlreadyExistsException
		//throws EmptyEmailException
		//throws InvalidUsernameException
		getBubbleDocs().createUser(this.newUsername, this.email, this.name, this.email);



	}
	
	public String getUsername () {
		return this.newUsername;
	}

}
