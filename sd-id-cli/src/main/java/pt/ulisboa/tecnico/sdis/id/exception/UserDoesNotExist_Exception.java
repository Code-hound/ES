package pt.ulisboa.tecnico.sdis.id.exception;

public class UserDoesNotExist_Exception extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String userId;

	public UserDoesNotExist_Exception(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userId;
	}

	public String getMessage() {
		return "This username " + this.userId + " does not exist.";
	}
}