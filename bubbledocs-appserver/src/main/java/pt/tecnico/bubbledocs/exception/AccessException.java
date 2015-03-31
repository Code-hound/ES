package pt.tecnico.bubbledocs.exception;

public class AccessException extends BubbleDocsException
{

	/*
	 *	ACCESS EXCEPTION
	 *
	 *	Excepcao caso o utilizador nao tenha permissoes no Documento.
	 *
	 *	@author: Luis Ribeiro Gomes
	 */

	private static final long serialVersionUID = 1L;
	
	private String userToken;       //userToken
	private String spreadSheetName; //spreadSheetName

	public AccessException(String userToken, String spreadSheetName)
	{
		this.userToken       = userToken;
		this.spreadSheetName = spreadSheetName;
	}
	
	public String getUserToken () {
		return userToken;
	}
	
	public String getSpreadSheetName () {
		return spreadSheetName;
	}

	@Override
	public String getMessage()
	{
		return "Access exception : the User \"" + this.userToken + "\" has no access to Document \"" + this.spreadSheetName + "\".";
	}
	
}
