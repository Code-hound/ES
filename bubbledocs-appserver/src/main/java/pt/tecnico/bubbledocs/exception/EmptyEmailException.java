package pt.tecnico.bubbledocs.exception;

public class EmptyEmailException extends BubbleDocsException
{

	/*
	 * EMPTY EMAIL EXCEPTION
	 * 
	 * Excepcao caso o E-Mail esteja vazio.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	public EmptyEmailException()
	{
		super();
	}

	@Override
	public String getMessage()
	{
		return "E-Mail field is empty.";
	}

}