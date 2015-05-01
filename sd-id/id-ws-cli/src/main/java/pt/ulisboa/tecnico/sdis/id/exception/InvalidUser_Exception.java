package pt.ulisboa.tecnico.sdis.id.exception;

public class InvalidUser_Exception extends Exception {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String userId;

	public InvalidUser_Exception(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userId;
	}

	@Override
	public String getMessage() {
		return "This username " + this.userId + " is not valid.";
	}
}