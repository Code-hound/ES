package pt.tecnico.bubbledocs.service;

//the needed import declarations

import org.jdom2.output.XMLOutputter;
import org.jdom2.Document;

import java.io.UnsupportedEncodingException;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.ExportDocumentException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;


/*
 * Export Document
 * 
 * BubbleDocs Exporting Service
 * 
 * Description: It receives a SpreadSheet and a User Token
 * and exports it to XML if the User has access permissions.
 * 
 * @author: Luis Ribeiro Gomes
 */

public class ExportDocumentService extends BubbleDocsService {

	private static XMLOutputter xml = new XMLOutputter();
	
    // the tokens
	private String userToken;
	private int docId;
	private byte[] result;

	public ExportDocumentService(String userToken, int docId) {
		this.userToken = userToken;
		this.docId = docId;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		String username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (username == null)
			throw new UserNotInSessionException(username);

		//throws InvalidAccessException
		if (!getSpreadSheet(docId).getOwnerUsername().equals(username) &&
		     getSpreadSheet(docId).getUserPermissionLevel(username) == 0)
			throw new InvalidAccessException(username, docId, "Read or Write");

		Document jdomDoc  = new Document();
		jdomDoc.setRootElement(getSpreadSheet(docId).exportToXML());

		//throws ExportDocumentException
		try {
			result = xml.outputString(jdomDoc).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ExportDocumentException();
		}

	}

	public byte[] getResult () {
		return this.result;
	}
}
