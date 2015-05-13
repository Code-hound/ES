package pt.tecnico.bubbledocs.service.remote;

import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;
import pt.tecnico.bubbledocs.exception.CannotLoadDocumentException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.ulisboa.tecnico.sdis.id.cli.IdClient;
import store.cli.StoreClient;

public class StoreRemoteServices {
	
	private StoreClient client;
	
	public void storeDocument(String username, String docName, byte[] document)
		throws Exception {
			client.store(username, docName, document);
		}
	
	public byte[] loadDocument(String username, String docName)
		throws Exception {
			return client.load(username, docName);
		}
	
}