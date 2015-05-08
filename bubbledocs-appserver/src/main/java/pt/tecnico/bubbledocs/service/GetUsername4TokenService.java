package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class GetUsername4TokenService extends BubbleDocsService {

	private String userToken;
	private String userName;
	
	public GetUsername4TokenService(String userToken) {
		this.userToken = userToken;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {
		BubbleDocs bd = getBubbleDocs();
		
		if(!bd.hasUserLoggedInByToken(userToken))
			throw new UserNotInSessionException(userToken);
		userName = bd.getUserLoggedInByToken(userToken).getUsername();
		
		returnUsername();
	}

	private String returnUsername() {
		return userName;
	}

}