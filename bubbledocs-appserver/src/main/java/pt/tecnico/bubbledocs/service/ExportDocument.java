package pt.tecnico.bubbledocs.service;

//the needed import declarations

import org.jdom2.output.XMLOutputter;

import java.io.UnsupportedEncodingException;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.ExportDocumentException;
import pt.tecnico.bubbledocs.exception.UserCantWriteException;
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
 * 
 */

public class ExportDocument extends BubbleDocsService {

	private static XMLOutputter xml = new XMLOutputter();
	
    // the tokens
	private String userToken;
	private int docId;
	private byte[] result;

	public ExportDocument(String userToken, int sheetId) {
		this.userToken = userToken;
		this.docId = sheetId;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		StoreRemoteServices service = new StoreRemoteServices();
		org.jdom2.Document jdomDoc = new org.jdom2.Document();
		String username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (username == null) {
			throw new UserNotInSessionException(username);
		}
		
		SpreadSheet sheet = getSpreadSheet(docId);
		if (!canBeWrittenBy(sheet, username) && !isOwnedBy(sheet, username)) {
			throw new InvalidAccessException(username, docId);
		}
		/*
		//throws InvalidAccessException
		if (!getSpreadSheet(sheetId).isOwnedBy(username) && !getSpreadSheet(sheetId).canBeReadBy(username) )
			throw new InvalidAccessException(username, sheetId);
		 */
		jdomDoc.setRootElement(getSpreadSheet(docId).exportToXML());
		//throws ExportDocumentException
		try {
			result = xml.outputString(jdomDoc).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ExportDocumentException();
		}

		//throws UnavailableServiceException
		try {
			service.storeDocument(username, getSpreadSheet(docId).getSpreadSheetName(), result);
		} catch(RemoteInvocationException e) {
			throw new UnavailableServiceException();
		}
	}

	public byte[] getResult () {
		return this.result;
	}
}
