package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.Access;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.UnknownBubbleDocsUserException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;

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
	protected void dispatch() throws UnknownBubbleDocsUserException,
			BubbleDocsException {
		BubbleDocs bd = getBubbleDocs();
		if (bd.checkIfRoot(userToken)) {
			User root = getBubbleDocs().getUserLoggedInByToken(userToken);
			
			resetUserLastAccess(root);
			
			User user = getUser(toDeleteUsername); //throws UnknownBubbleDocsUserException
			if (user==null)
				throw new UnknownBubbleDocsUserException(toDeleteUsername);
			for (Access access : user.getAccessSet()) {
				access.getDocument().removeDocAccess(access);
				user.removeAccess(access);
				access = null;
			}
			bd.removeUserFromSession(user);
			bd.removeUsers(user);
		}
	}
}
