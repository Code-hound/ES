package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.InvalidUserException;

public class GetUserInfoService extends BubbleDocsService {
	
	private String userName;
	private User user;
	
	public GetUserInfoService(String userName) {
		this.userName = userName;
	}

	public User getUser() {
		return user;		
	}

	@Override
	protected void dispatch() throws BubbleDocsException {
		
		BubbleDocs bd = getBubbleDocs();
		
		// throws InvalidUserException
		if(!bd.hasUserByUsername(userName)) {
			throw new InvalidUserException(userName);
		}

		user = bd.getUserByUsername(userName);

	}
	
}