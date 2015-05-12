package pt.tecnico.bubbledocs.integration.component;

import pt.tecnico.bubbledocs.service.ExportDocumentService;
import pt.tecnico.bubbledocs.service.GetUsername4TokenService;
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
	
	public byte[] getResult() {
		return this.result;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		GetUsername4TokenService getUsernameService    = new GetUsername4TokenService(this.userToken);
		ExportDocumentService    exportDocumentService = new ExportDocumentService(this.userToken, this.docId);
		
		//throws UserNotInSessionException
		//throws InvalidAccessException
		//throws ExportDocumentException
		getUsernameService.execute();
		exportDocumentService.execute();

		String username = getUsernameService.getUsername();
		byte[] result   = exportDocumentService.getResult();
		StoreRemoteServices remoteService = new StoreRemoteServices();
		
		//throws CannotStoreDocumentException
		//throws UnavailableServiceException
		try {
			
			//catches RemoteInvocationException
			//catches LoginBubbleDocsException
			remoteService.storeDocument(username, exportDocumentService.getDocname(), result);

		} catch (RemoteInvocationException e) {

			throw new UnavailableServiceException();

		}
		
		this.result = result;

	}

}
