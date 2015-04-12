package pt.tecnico.bubbledocs.exception;

public class InvalidEmailException extends BubbleDocsException {

	/*
	 * INVALID EMAIL EXCEPTION
	 * 
	 * Excepcao da varificacao se email e invalido.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	private String userEmail;

	public InvalidEmailException(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	@Override
	public String getMessage() {
		return "This E-Mail " + this.userEmail + " is not valid.";
	}

}