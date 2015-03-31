package pt.tecnico.bubbledocs.exception;

public class DividedByZeroException extends ArithmeticException {
	
	/*
	 *	DIVIDED BY ZERO EXCEPTION
	 *
	 *	@author: Luis Ribeiro Gomes
	 */
	
	public DividedByZeroException(){
		super("Argument divided by 0");
	}
}
