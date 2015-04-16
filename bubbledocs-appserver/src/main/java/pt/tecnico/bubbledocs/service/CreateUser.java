package pt.tecnico.bubbledocs.service;

// the needed import declarations

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.Session;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.exception.UnknownBubbleDocsUserException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistsException;
//import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.InvalidUsernameException;

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

	public CreateUser(String userToken, String newUsername, String email, String name) {
		if (newUsername.length() < 3 || newUsername.length() > 8)
			throw new InvalidUsernameException();
		this.userToken = userToken;
		this.newUsername = newUsername;
		// this.password = password;
		this.email = email;
		this.name = name;
	}

	@Override
	protected void dispatch() {		
		BubbleDocs bd = getBubbleDocs();
		if (bd.checkIfRoot(userToken)) {
			User root = getBubbleDocs().getUserLoggedInByToken(userToken);
			resetUserLastAccess(root);
			
			bd.createUser(newUsername, password, name, email);
		}
	}
	
	
}
