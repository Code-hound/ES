package pt.tecnico.bubbledocs.service;

// the needed import declarations

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.UserDoesNotExistException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;

/*
 * CREATE USER
 * 
 * Cria o utilizador passando-lhe como argumento um
 * Token como seu "nome".
 * 
 * @author: Francisco Maria Calisto
 * 
 */

public class CreateUser extends BubbleDocsService {
	
	private String userToken;
	private String newUsername;
	private String password;
	private String name;

    public CreateUser(String userToken, String newUsername,
            String password, String name) {
    	this.userToken = userToken;
    	this.newUsername = newUsername;
    	this.password = password;
    	this.name = name;
    }

    @Override
    protected void dispatch() throws BubbleDocsException {
    	getBubbleDocs().addUser(new User(newUsername, name, password));
    }
}
