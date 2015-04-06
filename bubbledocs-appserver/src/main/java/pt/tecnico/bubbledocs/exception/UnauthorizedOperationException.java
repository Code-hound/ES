package pt.tecnico.bubbledocs.exception;

public class UnauthorizedOperationException extends BubbleDocsException {

	/*
	 * USER IS NOT ROOT EXCEPTION
	 * 
	 * Excepcao caso o User nao seja o root nao podendo assim criar.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	private String rootUserName; // userName

	public UnauthorizedOperationException(String userName) {
		this.rootUserName = userName;
	}

	public String getUserName() {
		return this.rootUserName;
	}

	@Override
	public String getMessage() {
		return "User " + this.rootUserName + " is not a root user.";
	}

}