package pt.tecnico.bubbledocs.service.remote;

import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;
import pt.tecnico.bubbledocs.exception.CannotLoadDocumentException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;

public class StoreRemoteServices {
	
	/*
	 * ID REMOTE SERVICES
	 * 
	 * Servico remoto que abstrai o servico externo do SD-ID.
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */
	
	public void storeDocument(String username, String docName, byte[] document)
		throws CannotStoreDocumentException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote service
			
		}
	
	public byte[] loadDocument(String username, String docName)
		throws CannotLoadDocumentException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote service
			return null;
			
		}
	
}