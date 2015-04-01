package pt.tecnico.bubbledocs.exception;

public class AccessException extends BubbleDocsException {

	/*
	 * ACCESS EXCEPTION
	 * 
	 * Excepcao caso o utilizador nao tenha permissoes no Documento.
	 * 
	 * @author: Luis Ribeiro Gomes
	 */

	private static final long serialVersionUID = 1L;

	private String userToken;       //userToken
	private int sheetId; //spreadSheetName

	public AccessException(String userToken, int sheetId)
	{
		this.userToken       = userToken;
		this.sheetId = sheetId;
	}
	
	public String getUserToken ()
	{
		return this.userToken;
	}
	
	public int getSheetId ()
	{
		return this.sheetId;
	}

	@Override
	public String getMessage()
	{
		return "Access exception : the User \"" + this.userToken + "\" has no access to Document \"" + this.sheetId + "\".";
	}

}
