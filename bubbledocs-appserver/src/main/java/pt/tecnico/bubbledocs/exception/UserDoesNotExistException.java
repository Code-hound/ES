package pt.tecnico.bubbledocs.exception;

public class UserDoesNotExistException extends BubbleDocsException {

	/*
	 * USER DOES NOT EXIST EXCEPTION
	 * 
	 * Ficheiro "copiado" do exemplo phonebook
	 * 
	 * @author: Francisco Maria Calisto
	 */

	private static final long serialVersionUID = 1L;

	private String userName; // userName

	public UserDoesNotExistException(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public String getMessage() {
		return "User " + this.userName + " does not exist";
	}

}