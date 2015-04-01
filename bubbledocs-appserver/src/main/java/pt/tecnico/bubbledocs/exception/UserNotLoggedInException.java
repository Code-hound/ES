package pt.tecnico.bubbledocs.exception;

public class UserNotLoggedInException extends BubbleDocsException {

	/*
	 * USER NOT LOGGED IN EXCEPTION
	 * 
	 * Excepcao caso o User nao esteja em sessao
	 * 
	 * @author: Aline Caliente
	 */

	private static final long serialVersionUID = 1L;

	private String userOff; // userName

	public UserNotLoggedInException(String userToken) {
		this.userOff = userToken;
	}

	public String getUserOff() {
		return this.userOff;
	}

	@Override
	public String getMessage() {
		return "User" + this.getUserOff() + "not logged in.";
	}
}
