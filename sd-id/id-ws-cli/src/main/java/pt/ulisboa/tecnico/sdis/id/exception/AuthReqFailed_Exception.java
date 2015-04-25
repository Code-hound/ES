package id.exception;

public class AuthReqFailed_Exception extends Exception {
	
	private String userId;
	
	public AuthReqFailed_Exception(String userId) {
		this.userId = userId;	
	}
	
	public String getMessage() {
		return "The authentication for the user " + this.userId + " has failed.";
	}
}