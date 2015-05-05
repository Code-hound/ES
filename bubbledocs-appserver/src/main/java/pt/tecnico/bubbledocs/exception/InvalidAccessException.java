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
	private String access;

	public InvalidAccessException(String username, int sheetId, String access)
	{
		this.username       = username;
		this.sheetId = sheetId;
		this.access = access;
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
		return "Access exception : the User \"" + this.username +
				"\" does not have access of type \"" + this.access+
				"\" to Document \"" + this.sheetId + "\".";
	}

}
