package id.exception;

public class UserAlreadyExists_Exception extends Exception {

	private String userId;

	public UserAlreadyExists_Exception(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userId;
	}

	public String getMessage() {
		return "This username " + this.userId + " is already in use.";
	}
}