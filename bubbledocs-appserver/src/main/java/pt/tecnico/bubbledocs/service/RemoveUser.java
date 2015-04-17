package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.Access;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
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
		BubbleDocs bd = getBubbleDocs();

		//throws UserNotInSessionException and UnauthorizedOperationException
		bd.checkIfRoot(userToken);

		//throws UnavailableServiceException
		try {
			service.removeUser(toDeleteUsername);
		} catch (RemoteInvocationException e) {
			throw new UnavailableServiceException();
		}

		//throws LoginBubbleDocsException
		User user = getUser(toDeleteUsername);
		if (user==null)
			throw new LoginBubbleDocsException(toDeleteUsername);

		for (Access access : user.getAccessSet()) {
			access.getDocument().removeDocAccess(access);
			user.removeAccess(access);
			access = null;
		}
		bd.removeUserFromSession(user);
		bd.removeUsers(user);
	}
}
