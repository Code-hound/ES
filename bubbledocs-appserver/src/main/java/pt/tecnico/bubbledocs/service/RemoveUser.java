package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.SpreadSheet;

import pt.tecnico.bubbledocs.exception.UserDoesNotExistException;
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
	protected void dispatch() throws UserDoesNotExistException,
			BubbleDocsException {
		/*
		if (userToken == "root") {
			User userToDelete = getBubbleDocs().getUserByUsername(toDeleteUsername);

			if (userToDelete != null) {
				getBubbleDocs().getUserLoggedInByToken(userToken).removeUser(userToDelete);
			} else {
				throw new UserDoesNotExistException(this.toDeleteUsername);
			}
		}
	}
	*/
		BubbleDocs bd = getBubbleDocs();
		try {
			if (bd.checkIfRoot(userToken)) {
				User user = getUser(toDeleteUsername);
				bd.removeUsers(user);
				
				for (SpreadSheet s : bd.getSpreadSheetByOwner(user)) {
					bd.removeDocs(s);
				}
			}
		}
		catch (UserDoesNotExistException | UnauthorizedOperationException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
