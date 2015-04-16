package pt.tecnico.bubbledocs.exception;

public class InvalidUsernameException extends BubbleDocsException {

	/*
	 * EMPTY USERNAME EXCEPTION
	 * 
	 * Excepcao caso o User esteja vazio.
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	//private String userName; // userName

	public InvalidUsernameException() {
		super();
	}
	
	/*
	public String getUserName() {
		return this.userName;
	}
	*/

	@Override
	public String getMessage() {
		return "Username must be no shorter than 3 characters and no longer than 8.";
	}

}