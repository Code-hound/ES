package pt.tecnico.bubbledocs.exception;

public class EmptyAttributeException extends EmptyContentException
{

	/*
	 *	EMPTY ATTRIBUTE EXCEPTION
	 *
	 *	Excepcao caso o attributo do elemento esteja vazio.
	 *
	 *	@author: Luís Ribeiro Gomes
	 */

	private String attribute; //attribute

	public EmptyAttributeException(String element, String attribute)
	{
		super(element);
		this.attribute = attribute;
	}
	
	public String getAttribute()
	{
		return this.attribute;
	}
	
	@Override
	public String getMessage()
	{
		return "Attribute " + this.attribute + " from element " + getContent() + " is empty.";
	}
	
}
