package pt.ulisboa.tecnico.sdis.id.exception;

public class InvalidEmail_Exception extends Exception {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String emailAddress;

	public InvalidEmail_Exception(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserEmail() {
		return this.emailAddress;
	}

	@Override
	public String getMessage() {
		return "This email " + this.emailAddress + " is not valid.";
	}
}