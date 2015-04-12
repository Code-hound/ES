package pt.tecnico.bubbledocs.exception;

public class DuplicateUsernameException extends BubbleDocsException {

	/*
	 * DUPLICATE USERNAME EXCEPTION
	 * 
	 * Excepcao da varificacao se user e duplicado.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	private String userName;

	public DuplicateUsernameException(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public String getMessage() {
		return "This username " + this.userName + " is duplicate";
	}

}