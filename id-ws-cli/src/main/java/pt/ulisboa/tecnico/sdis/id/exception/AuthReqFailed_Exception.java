package pt.ulisboa.tecnico.sdis.id.exception;

public class AuthReqFailed_Exception extends Exception {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	public AuthReqFailed_Exception(String userId) {
		this.userId = userId;	
	}
	
	public String getMessage() {
		return "The authentication for the user " + this.userId + " has failed.";
	}
}