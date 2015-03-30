package pt.tecnico.bubbledocs.exception;

public class EmptyUsernameException extends BubbleDocsException
{

	/*
	 *	EMPTY USERNAME EXCEPTION
	 *
	 *	Excepcao caso o User esteja vazio.
	 *
	 *	@author: Francisco Maria Calisto
	 */
	
	private static final long serialVersionUID = 1L;

	private String userName; //userName

	public EmptyUsernameException(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	@Override
	public String getMessage()
	{
		return "User field is empty: " + this.userName;
	}
	
}