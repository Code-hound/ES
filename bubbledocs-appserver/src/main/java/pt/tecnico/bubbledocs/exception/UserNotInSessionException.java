package pt.tecnico.bubbledocs.exception;

public class UserNotInSessionException extends BubbleDocsException {

	/*
	 * USER NOT LOGGED IN EXCEPTION
	 * 
	 * Excepcao caso o User nao esteja em sessao
	 * 
	 * @author: Aline Caliente
	 */

	private static final long serialVersionUID = 1L;

	private String userOff; // userName

	public UserNotInSessionException(String userToken) {
		this.userOff = userToken;
	}

	public String getUserOff() {
		return this.userOff;
	}

	@Override
	public String getMessage() {
		return "UserNotInSessionException: User " + this.getUserOff() + " is not logged in.";
	}
}
