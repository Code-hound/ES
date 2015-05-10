package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.service.ExportDocumentService;
import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;

/*
 * Export Document Integrator
 * 
 * BubbleDocs Exporting Integrator
 * 
 * Description: It receives a SpreadSheet and a User Token
 * and exports it to XML if the User has access permissions.
 * 
 * @author: Luis Ribeiro Gomes
 * 
 */

public class ExportDocumentIntegrator extends BubbleDocsIntegrator {

    // the tokens
	private String userToken;
	private int docId;
	private byte[] result;

	public ExportDocumentIntegrator(String userToken, int docId) {
		this.userToken = userToken;
		this.docId = docId;
	}

	public String getUserToken () {
		return this.userToken;
	}
	
	public int getDocId() {
		return this.docId;
	}
	
	public byte[] getResult() {
		return this.result;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		ExportDocumentService localService = new ExportDocumentService(this.userToken, this.docId);

		//throws UserNotInSessionException
		//throws InvalidAccessException
		//throws ExportDocumentException
		localService.execute();

		StoreRemoteServices remoteService = new StoreRemoteServices();
		
		//throws CannotStoreDocumentException
		//throws UnavailableServiceException
		try {
			remoteService.storeDocument(localService.getUsername(), localService.getDocname(), localService.getResult());
		} catch (RemoteInvocationException e) {
			throw new UnavailableServiceException();
		}
		
		this.result = localService.getResult();

	}

}
