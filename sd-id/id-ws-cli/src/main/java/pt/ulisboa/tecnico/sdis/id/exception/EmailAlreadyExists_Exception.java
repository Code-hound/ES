package pt.ulisboa.tecnico.sdis.id.exception;

public class EmailAlreadyExists_Exception extends Exception {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String emailAddress;

	public EmailAlreadyExists_Exception(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserEmail() {
		return this.emailAddress;
	}

	public String getMessage() {
		return "This email " + this.emailAddress + " is already in use.";
	}
}