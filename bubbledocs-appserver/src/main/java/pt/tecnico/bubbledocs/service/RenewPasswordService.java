package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class RenewPasswordService extends BubbleDocsService {
	
	private String userToken;

	public RenewPasswordService (String userToken) {
		this.userToken = userToken;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		String username = resetUserLastAccess(this.userToken);

		//throws UserNotInSessionException
		if (userIsNotValid(username)) {
			throw new UserNotInSessionException(username);
		}

	}
}
