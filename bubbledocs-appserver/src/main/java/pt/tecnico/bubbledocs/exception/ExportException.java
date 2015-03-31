package pt.tecnico.bubbledocs.exception;

public class ExportException extends XMLException
{

	/*
	 *	EXPORT EXCEPTION
	 *
	 *	Excepcao caso algum attributo do Documento estiver vazio.
	 *
	 *	@author: Luis Ribeiro Gomes
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
		return "Export exception : " + super.getMessage();
	}
	
}
 
