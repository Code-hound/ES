package pt.tecnico.bubbledocs.exception;

public class UserIsNotRootException extends BubbleDocsException {

	/*
	 * USER IS NOT ROOT EXCEPTION
	 * 
	 * Excepcao caso o User nao seja o root nao podendo assim criar.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	private String rootUserToken; // userName

	public UserIsNotRootException(String userToken) {
		this.rootUserToken = userToken;
	}

	public String getUserToken() {
		return this.rootUserToken;
	}

	@Override
	public String getMessage() {
		return "User " + this.rootUserToken + " is not a root user";
	}

}