package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.InvalidUserException;

public class GetUserInfoService extends BubbleDocsService {
	
	private String username;

	private String password;
	private String name;
	private String email;
	
	public GetUserInfoService(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {
		
		User user = getUser(this.username);
		
		// throws InvalidUserException
		if(userIsNotValid(user)) {
			throw new InvalidUserException(this.username);
		}
		
		this.password = user.getPassword();
		this.name     = user.getName();
		this.email    = user.getEmail();

	}
	
}