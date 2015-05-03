package pt.tecnico.bubbledocs.integration.remote;

import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;
import pt.tecnico.bubbledocs.exception.CannotLoadDocumentException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;

public class StoreRemoteIntegrators {
	
	/*
	 * ID REMOTE INTEGRATOR
	 * 
	 * Servico remoto que abstrai o servico externo do SD-ID.
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */
	
	public void storeDocument(String username, String docName, byte[] document)
		throws CannotStoreDocumentException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote integration
			
		}
	
	public byte[] loadDocument(String username, String docName)
		throws CannotLoadDocumentException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote integration
			return null;
			
		}
	
}
