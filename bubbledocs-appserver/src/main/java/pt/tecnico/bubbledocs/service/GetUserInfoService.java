package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class GetUserInfoService extends BubbleDocsService {
	
	private String userName;
	private User user;
	
	public GetUserInfoService(String userName) {
		this.userName = userName;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {
		
		BubbleDocs bd = getBubbleDocs();
		
		if(!bd.hasUserByUsername(userName))
			throw new UserNotInSessionException(userName);
		user = bd.getUserByUsername(userName);
		
		returnUser();
	}

	private User returnUser() {
		return user;		
	}
	
}