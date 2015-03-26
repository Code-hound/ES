package pt.tecnico.bubbledocs.exception;

public class UserDoesNotExistException extends BubbleDocsException
{

	/*
	 *	USER DOES NOT EXIST EXCEPTION
	 *
	 *	Ficheiro "copiado" do exemplo phonebook
	 *
	 *	@author: Francisco Maria Calisto
	 */
	
	private static final long serialVersionUID = 1L;

	private String userToken; //userName

	public UserDoesNotExistException(String userToken)
	{
		this.userToken = userToken;
	}
	
	public String getUserToken()
	{
		return this.userToken;
	}
	
}