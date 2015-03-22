package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.UserDoesNotExistException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;

public class DeleteUser extends BubbleDocsService {
	
	private String userToken;
	private String toDeleteUsername;

    public DeleteUser(String userToken, String toDeleteUsername) {
    	// add code here
    	this.userToken = userToken;
    	this.toDeleteUsername = toDeleteUsername;
    }

    @Override
    protected void dispatch() throws BubbleDocsException {
    	// add code here
    	//User u = get_username(userToken); 			<--TODO
    	//User tdun = u.get_username(toDeleteUsername); <--TODO
    	
    	/*
    	if(tdun == null)
    	{
    		throw new UserDoesNotExistException(this.toDeleteUsername);
    	}
    	*/
    	
    	//tdun.delete(); TODO
    }

}
