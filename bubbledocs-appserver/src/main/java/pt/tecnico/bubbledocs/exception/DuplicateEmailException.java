package pt.tecnico.bubbledocs.exception;

public class DuplicateEmailException extends BubbleDocsException {

	/*
	 * DUPLICATE EMAIL EXCEPTION
	 * 
	 * Excepcao da varificacao se email ja existe.
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String userEmail;

	public DuplicateEmailException(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	@Override
	public String getMessage() {
		return "This E-Mail " + this.userEmail + " is already being used";
	}

}