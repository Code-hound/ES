package pt.tecnico.bubbledocs.exception;

public class InvalidAccessException extends BubbleDocsException {

	/*
	 * ACCESS EXCEPTION
	 * 
	 * Excepcao caso o utilizador nao tenha permissoes no Documento.
	 * 
	 * @author: Luis Ribeiro Gomes
	 */

	private static final long serialVersionUID = 1L;

	private String username;       //userToken
	private int sheetId; //spreadSheetName

	public InvalidAccessException(String username, int sheetId)
	{
		this.username       = username;
		this.sheetId = sheetId;
	}
	
	public String getUsername ()
	{
		return this.username;
	}
	
	public int getSheetId ()
	{
		return this.sheetId;
	}

	@Override
	public String getMessage()
	{
		return "Access exception : the User \"" + this.username + "\" has no access to Document \"" + this.sheetId + "\".";
	}

}
