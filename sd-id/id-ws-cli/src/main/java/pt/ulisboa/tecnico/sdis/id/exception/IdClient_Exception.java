package pt.ulisboa.tecnico.sdis.id.exception;

public class IdClient_Exception extends Exception {

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	public IdClient_Exception() {
    }
    
	public IdClient_Exception(String message) {
		super(message);
	}
	
    public IdClient_Exception(Throwable cause) {
        super(cause);
    }

    public IdClient_Exception(String message, Throwable cause) {
        super(message, cause);
    }
}