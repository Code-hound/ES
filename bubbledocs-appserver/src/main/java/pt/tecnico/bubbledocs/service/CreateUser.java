package pt.tecnico.bubbledocs.service;

// the needed import declarations

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.UserDoesNotExistException;

public class CreateUser extends BubbleDocsService {
	
	private String userToken;
	private String newUsername;
	private String password;
	private String name;

    public CreateUser(String userToken, String newUsername,
            String password, String name) {
    	// add code here
    	this.userToken = userToken;
    	this.newUsername = newUserName;
    	this.password = password;
    	this.name = name;
    }

    @Override
    protected void dispatch() throws BubbleDocsException {
    	// add code here
    	getBubbleDocs().addUser(new User(newUserName, name, password));
    }
}
