package id.cli;

public class IdClientException extends Exception {

    public IdClientException() {
    }
    
	public IdClientException(String message) {
		super(message);
	}
	
    public IdClientException(Throwable cause) {
        super(cause);
    }

    public IdClientException(String message, Throwable cause) {
        super(message, cause);
    }
}