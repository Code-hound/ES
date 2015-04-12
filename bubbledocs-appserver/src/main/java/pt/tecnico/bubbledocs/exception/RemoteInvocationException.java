package pt.tecnico.bubbledocs.exception;

import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

public class RemoteInvocationException extends BubbleDocsException {

	/*
	 * REMOTE INVOCATION EXCEPTION
	 * 
	 * Lanca excepcao remota.
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public RemoteInvocationException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Remote invocation error";
	}
	
	
	// TODO
	
	public void dispatch() {
		IDRemoteServices remote;
		
		// TODO - code here
		
		try {
			// invoke some ethod on remote
		} catch (RemoteInvocationException rie) {
			throw new UnavailableServiceException();
		}
		
		// TODO - code here
		
	}

}