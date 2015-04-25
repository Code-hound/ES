package id.exception;

public class InvalidEmail_Exception extends Exception {
	
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