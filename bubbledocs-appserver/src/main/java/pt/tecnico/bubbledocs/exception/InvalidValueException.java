package pt.tecnico.bubbledocs.exception;

import pt.tecnico.bubbledocs.domain.Cell;

public class InvalidValueException extends BubbleDocsException {

	/*
	 * INVALID VALUE EXCEPTION
	 * 
	 * Excepcao caso o valor seja inválido.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;
	private int row;
	private int column;

	public InvalidValueException() {
		super();
		//this.row = cell.getCellRow();
		//this.column = cell.getCellColumn();
		
	}

	@Override
	public String getMessage() {
		return "Invalid value.";
	}

}