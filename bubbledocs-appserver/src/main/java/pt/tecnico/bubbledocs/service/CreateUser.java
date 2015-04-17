package pt.tecnico.bubbledocs.service;

// the needed import declarations

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.InvalidUsernameException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
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

public class CreateUser extends BubbleDocsService {

	private String userToken;
	private String newUsername;
	private String password;
	private String email;
	private String name;
	
	private String username;

	public CreateUser(String userToken, String newUsername, String email, String name) {
		if (newUsername.length() < 3 || newUsername.length() > 8)
			throw new InvalidUsernameException();
		this.userToken = userToken;
		this.newUsername = newUsername;
		this.email = email;
		this.name = name;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		String username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (username == null)
			throw new UserNotInSessionException(username);
		
		//throws UnauthorizedOperationException
		if (!username.equals("root"))
			throw new UnauthorizedOperationException(username);
		
		User user = getBubbleDocs().createUser(newUsername, password, name, email);
		
		if (user == null)
			throw new UserAlreadyExistsException(username);
		
		this.username = user.getUsername();
	}
	
	public String getUsername () {
		return this.username;
	}
}
