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

	private String username; //username
	private String docname;  //docname
	private String access;   //access

	public InvalidAccessException(String username, String docname, String access)
	{
		this.username = username;
		this.docname  = docname;
		this.access   = access;
	}
	
	public String getUsername ()
	{
		return this.username;
	}
	
	public String getDocname ()
	{
		return this.docname;
	}

	@Override
	public String getMessage()
	{
		return "Access exception : the User \"" + this.username +
				"\" does not have access of type \"" + this.access+
				"\" to Document \"" + this.docname + "\".";
	}

}
