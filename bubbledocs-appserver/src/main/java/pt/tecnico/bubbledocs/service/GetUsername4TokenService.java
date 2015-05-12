package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.Session;
import pt.tecnico.bubbledocs.domain.BubbleDocs;

import java.lang.NullPointerException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class GetUsername4TokenService extends BubbleDocsService {

	private String userToken;

	private String username;
	
	public GetUsername4TokenService(String userToken) {
		this.userToken = userToken;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {
		
		BubbleDocs bd = getBubbleDocs();
		String username;

		// throws UserNotInSessionException
		try {

			// catches NullPointerException
			username = bd.getUserLoggedInByToken(this.userToken).getUsername();

		} catch (NullPointerException e) {

			throw new UserNotInSessionException(this.userToken);

		}
		
		this.username = username;

	}

}