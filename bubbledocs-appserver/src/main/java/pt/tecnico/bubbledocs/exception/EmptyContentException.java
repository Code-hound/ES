package pt.tecnico.bubbledocs.exception;

public class EmptyContentException extends BubbleDocsException
{

	/*
	 *	EMPTY CONTENT EXCEPTION
	 *
	 *	Excepcao caso o attributo do contento esteja vazio.
	 *
	 *	@author: Luís Ribeiro Gomes
	 */

	private String content; //content

	public EmptyContentException(String content)
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return this.content;
	}
	
	@Override
	public String getMessage()
	{
		return "Content " + this.content + " is empty.";
	}

}
