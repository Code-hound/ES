package id.exception;

public class EmailAlreadyExists_Exception extends Exception {
	
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