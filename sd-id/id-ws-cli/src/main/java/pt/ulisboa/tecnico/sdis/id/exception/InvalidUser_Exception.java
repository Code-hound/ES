package id.exception;

public class InvalidUser_Exception extends Exception {
	
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