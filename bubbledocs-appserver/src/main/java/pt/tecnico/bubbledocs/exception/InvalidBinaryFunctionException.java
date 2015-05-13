package pt.tecnico.bubbledocs.exception;

import pt.tecnico.bubbledocs.domain.Cell;

public class InvalidBinaryFunctionException extends BubbleDocsException {

	/*
	 * INVALID VALUE EXCEPTION
	 * 
	 * Excepcao caso o valor seja inválido.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	public InvalidBinaryFunctionException() {
		super();

	}

	@Override
	public String getMessage() {
		return "Invalid Binary Function.";
	}

}