package pt.tecnico.bubbledocs.exception;

public abstract class XMLException extends BubbleDocsException
{

	/*
	 *	EMPTY CONTENT EXCEPTION
	 *
	 *	Excepcao caso o attributo do contento esteja vazio.
	 *
	 *	@author: Luis Ribeiro Gomes
	 */

	private static final long serialVersionUID = 1L;
	
	private String element;   //element
	private String attribute; //attribute

	public XMLException( String element, String attribute)
	{
		this(element);
		this.attribute = attribute;
	}

	public XMLException (String element)
	{
		this.element = element;
	}
	
	public String getElement ()
	{
		return this.element;
	}
	
	public String getAttribute ()
	{
		return this.attribute;
	}
	
	@Override
	public String getMessage()
	{
		if (this.attribute == null)
			return "Cannot convert " + this.element;
		else
			return "Cannot convert " + this.attribute + " from " + this.element;
	}
	
}
