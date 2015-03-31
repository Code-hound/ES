package pt.tecnico.bubbledocs.exception;

public class DividedByZeroException extends ArithmeticException {
	
	/*
	 *	DIVIDED BY ZERO EXCEPTION
	 *
	 *	@author: Luis Ribeiro Gomes
	 */

	private static final long serialVersionUID = 1L;
	
	public DividedByZeroException(){
		super("Argument divided by 0");
	}
}
