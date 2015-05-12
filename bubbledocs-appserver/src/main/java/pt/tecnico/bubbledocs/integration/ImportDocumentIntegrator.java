package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.domain.SpreadSheet;

import pt.tecnico.bubbledocs.service.GetUsername4TokenService;
import pt.tecnico.bubbledocs.service.ImportDocumentService;

import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;
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

public class ImportDocumentIntegrator extends BubbleDocsIntegrator {

    // the tokens
	private String userToken;
	private int    docId;

	private SpreadSheet result;

	public ImportDocumentIntegrator(String userToken, int docId) {
		this.userToken = userToken;
		this.docId     = docId;
	}
	
	public SpreadSheet getResult() {
		return this.result;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		GetUsername4TokenService getUsernameService = new GetUsername4TokenService(this.userToken);
		StoreRemoteServices      remoteService      = new StoreRemoteServices();

		//throws UserNotInSessionException
		getUsernameService.execute();
		
		String username = getUsernameService.getUsername();
		
		//throws CannotStoreDocumentException
		//throws UnavailableServiceException
		try {
			
			//catches RemoteInvocationException
			//catches LoginBubbleDocsException
			byte[] doc = remoteService.loadDocument(username, "" + this.docId);

			//throws DocumentDoesNotExistException
			if (doc == null) {
				throw new DocumentDoesNotExistException(username, "" + this.docId);
			}
			
			ImportDocumentService    importDocumentService = new ImportDocumentService(this.userToken, doc);
			
			//throws UserNotInSessionException
			//throws InvalidAccessException
			//throws ImportDocumentException
			importDocumentService.execute();

			SpreadSheet result   = importDocumentService.getResult();
			
			this.result = result;

		} catch (RemoteInvocationException e) {

			throw new UnavailableServiceException();

		}

	}

}
