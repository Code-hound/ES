package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class RenewPasswordService extends BubbleDocsService {
	
	private String userToken;

	private String username;

	public RenewPasswordService (String userToken) {
		this.userToken = userToken;
	}

	public String getUserToken() {
		return this.userToken;
	}

	public String getUsername() {
		return this.username;
	}

	private boolean userIsNotValid (String username) {
		return (username == null);
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		String username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (userIsNotValid(username)) {
			throw new UserNotInSessionException(username);
		}

	}
}
