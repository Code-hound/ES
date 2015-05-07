package pt.tecnico.bubbledocs.integration;

//the needed import declarations

import org.jdom2.output.XMLOutputter;

import java.io.UnsupportedEncodingException;

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
 * BubbleDocs Exporting Integrator
 * 
 * Description: It receives a SpreadSheet and a User Token
 * and exports it to XML if the User has access permissions.
 * 
 * @author: Luis Ribeiro Gomes
 * 
 */

public class ExportDocumentIntegrator extends BubbleDocsIntegrator {

	private static XMLOutputter xml = new XMLOutputter();
	
    // the tokens
	private String userToken;
	private int sheetId;
	private byte[] result;

	public ExportDocumentIntegrator(String userToken, int sheetId) {
		this.userToken = userToken;
		this.sheetId = sheetId;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		StoreRemoteServices service  = new StoreRemoteServices();
		String              username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (username == null)
			throw new UserNotInSessionException(username);

		//throws InvalidAccessException
		if (!getSpreadSheet(sheetId).getOwnerUsername().equals(username) &&
		     getSpreadSheet(sheetId).getUserPermissionLevel(username) == 0)
			throw new InvalidAccessException(username, sheetId, "Read or Write");

		org.jdom2.Document jdomDoc  = new org.jdom2.Document();
		jdomDoc.setRootElement(getSpreadSheet(sheetId).exportToXML());

		//throws ExportDocumentException
		try {
			result = xml.outputString(jdomDoc).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ExportDocumentException();
		}

		//throws UnavailableServiceException
		try {
			service.storeDocument(username, getSpreadSheet(sheetId).getSpreadSheetName(), result);
		} catch(RemoteInvocationException e) {
			throw new UnavailableServiceException();
		}
	}

	public byte[] getResult () {
		return this.result;
	}
}
