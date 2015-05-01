package pt.tecnico.bubbledocs.exception;

public class InvalidUserException extends BubbleDocsException {

	/*
	 * INVALID USERNAME EXCEPTION
	 * 
	 * Excepcao da varificacao se user e valido.
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String userName;

	public InvalidUserException(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public String getMessage() {
		return "The user " + this.userName + " does not exist";
	}

}
