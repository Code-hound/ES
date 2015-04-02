package pt.tecnico.bubbledocs.exception;

public class WrongPasswordException extends BubbleDocsException {
	
	private String username;
	
	public WrongPasswordException(String username) {
		this.username = username;
	}
	
	public String getUsername () {
		return this.username;
	}
	
	@Override
	public String getMessage () {
		return "WrongPasswordException: Password does not match for user " + username +".";
	}
}
