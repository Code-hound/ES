package pt.tecnico.bubbledocs.exception;

public class LoginBubbleDocsException extends BubbleDocsException {

	/*
	 * INVALID USERNAME EXCEPTION
	 * 
	 * Excepcao do Login
	 * 
	 * @author: Luis Ribeiro Gomes
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String username;

	public LoginBubbleDocsException(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public String getMessage() {
		return "The login " + this.username + " has made failed.";
	}

}
