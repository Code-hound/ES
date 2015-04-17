package pt.tecnico.bubbledocs.exception;

public class InvalidValueException extends BubbleDocsException {

	/*
	 * INVALID VALUE EXCEPTION
	 * 
	 * Excepcao caso o valor seja inválido.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	public InvalidValueException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Invalid Value";
	}

}