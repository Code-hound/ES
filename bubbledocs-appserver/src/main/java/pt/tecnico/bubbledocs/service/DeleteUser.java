package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.UserDoesNotExistException;

public class DeleteUser extends BubbleDocsService {
	
	private String userToken;
	private String toDeleteUsername;

    public DeleteUser(String userToken, String toDeleteUsername) {
    	// add code here
    	this.userToken = userToken;
    	this.toDeleteUserName = toDeleteUserName;
    }

    @Override
    protected void dispatch() throws BubbleDocsException {
    	// add code here
    	User u = getUser(userToken);
    	User tdun = user.getUser(toDeleteUserName);
    	
    	if(tdun == null)
    	{
    		throw new UserDoesNotExistException(this.toDeleteUsername);
    	}
    	
    	tdun.delete();
    }

}
