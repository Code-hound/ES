package pt.tecnico.bubbledocs.exception;

public class ImportException extends ConversionException
{

	/*
	 *	IMPORT EXCEPTION
	 *
	 *	Excepcao caso algum attributo do XML estiver vazio.
	 *
	 *	@author: Luís Ribeiro Gomes
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
		return "Import exception : " + getException().getMessage();
	}
	
}
