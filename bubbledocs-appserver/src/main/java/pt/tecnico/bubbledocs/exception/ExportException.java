package pt.tecnico.bubbledocs.exception;

public class ExportException extends ConversionException
{

	/*
	 *	EXPORT EXCEPTION
	 *
	 *	Excepcao caso algum attributo da SpreadSheet estiver vazio.
	 *
	 *	@author: Luís Ribeiro Gomes
	 */

	public ExportException(String element, String attribute)
	{
		super(element, attribute);
	}

	public ExportException(String element)
	{
		super(element);
	}

	@Override
	public String getMessage()
	{
		return "Export exception : " + getException().getMessage();
	}
	
}
 
