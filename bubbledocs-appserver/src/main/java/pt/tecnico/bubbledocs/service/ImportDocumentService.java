package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.SpreadSheet;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.ImportDocumentException;

public class ImportDocumentService extends BubbleDocsService {

    // the tokens
	private String userToken;
	private byte[] docId;

	private SpreadSheet result;

	public ImportDocumentService(String userToken, byte[] doc) {
		this.userToken = userToken;
		this.docId     = docId;
	}
	
	public SpreadSheet getResult() {
		return this.result;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		//throws UserNotInSessionException
		//throws InvalidAccessException
		//throws ImportDocumentException
		this.result = null;

	}

}
