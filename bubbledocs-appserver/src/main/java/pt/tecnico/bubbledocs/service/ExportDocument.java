package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.SpreadSheet;

import org.jdom2.output.XMLOutputter;
import java.io.UnsupportedEncodingException;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.ExportDocumentException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

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
	private int sheetId;
	private byte[] result;

	public ExportDocument(String userToken, int sheetId) {
		this.userToken = userToken;
		this.sheetId = sheetId;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {
		org.jdom2.Document jdomDoc = new org.jdom2.Document();

		String username = getBubbleDocs().getUsernameLoggedInByToken(userToken);
		if (username == null)
			throw new UserNotInSessionException(userToken);
		//throws DocumentDoesNotExistException
		SpreadSheet sheet = getSpreadSheet(sheetId);

		if ( !sheet.isOwnedBy(username) && !sheet.canBeReadBy(username) )
			throw new InvalidAccessException(username, sheetId);

		jdomDoc.setRootElement(sheet.exportToXML());
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
