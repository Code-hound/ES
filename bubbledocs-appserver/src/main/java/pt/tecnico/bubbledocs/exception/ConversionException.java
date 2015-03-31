package pt.tecnico.bubbledocs.exception;

public abstract class ConversionException extends BubbleDocsException
{

	/*
	 *	EMPTY CONTENT EXCEPTION
	 *
	 *	Excepcao caso o attributo do contento esteja vazio.
	 *
	 *	@author: Luís Ribeiro Gomes
	 */

	private EmptyContentException exception; //content

	public ConversionException(String element, String attribute)
	{
		this.exception = new EmptyAttributeException (element, attribute);
	}

	public ConversionException(String element)
	{
		this.exception = new EmptyContentException (element);
	}
	
	public EmptyContentException getException()
	{
		return this.exception;
	}
	
	@Override
	public abstract String getMessage();
	
}
