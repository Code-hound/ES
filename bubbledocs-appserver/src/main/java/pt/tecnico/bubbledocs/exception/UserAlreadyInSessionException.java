package pt.tecnico.bubbledocs.exception;

public class UserAlreadyInSessionException extends BubbleDocsException {
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	public UserAlreadyInSessionException(String username) {
		this.username = username;
	}
	
	public String getUsername () {
		return this.username;
	}
	
	@Override
	public String getMessage () {
		return "UserAlreadyInSessionException: User" + username + " is already logged in.";
	}
}