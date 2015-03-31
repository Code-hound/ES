package pt.tecnico.bubbledocs.exception;

public class ImportException extends XMLException
{

	/*
	 *	IMPORT EXCEPTION
	 *
	 *	Excepcao caso algum attributo do XML estiver vazio.
	 *
	 *	@author: Luis Ribeiro Gomes
	 */

	public ImportException(String element, String attribute)
	{
		super(element, attribute);
	}

	public ImportException(String element)
	{
		super(element);
	}

	@Override
	public String getMessage()
	{
		return "Import exception : " + super.getMessage();
	}
	
}
