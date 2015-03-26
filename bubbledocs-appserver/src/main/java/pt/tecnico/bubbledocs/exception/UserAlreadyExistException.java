package pt.tecnico.bubbledocs.exception;

public class UserAlreadyExistException extends BubbleDocsException
{

	/*
	 *	USER ALREADY EXIST EXCEPTION
	 *
	 *	Excepcao da varificacao se user ja existe.
	 *
	 *	@author: Francisco Maria Calisto
	 */
	
	private static final long serialVersionUID = 1L;

	private String userName;

	public UserAlreadyExistException(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
}