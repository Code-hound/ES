package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.Access;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

/*
 * DELETE USER
 * 
 * Esta classe permite ao user se caso este for
 * o "root" pode entao apagar o user.
 * 
 * @author: Francisco Maria Calisto
 * 
 */

public class RemoveUser extends BubbleDocsService {

	private String userToken;
	private String toDeleteUsername;

	public RemoveUser(String userToken, String toDeleteUsername) {
		this.userToken = userToken;
		this.toDeleteUsername = toDeleteUsername;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		IDRemoteServices service = new IDRemoteServices();
		resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		//throws UnavailableServiceException
		if (checkIfRoot(userToken)){
			try {
				service.removeUser(toDeleteUsername);
			} catch (RemoteInvocationException e) {
				throw new UnavailableServiceException();
			}
			
			//throws LoginBubbleDocsException
			if (getUser(toDeleteUsername)==null)
				throw new LoginBubbleDocsException(toDeleteUsername);
	
			for (Access access : getUser(toDeleteUsername).getAccessSet()) {
				access.getDocument().removeDocAccess(access);
				access = null;
			}
			BubbleDocs bd = getBubbleDocs();
			bd.removeUserFromSession(getUser(toDeleteUsername));
			bd.removeUsers(getUser(toDeleteUsername));
		}
	}
}
