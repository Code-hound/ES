package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.AccessException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;

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

    // the tokens
	private String userToken;
	private int sheetId;
	private Element xml;

	public ExportDocument(String userToken, int sheetId) {
		this.userToken = userToken;
		this.sheetId = sheetId;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {
		BubbleDocs bd = getBubbleDocs();

		//throws UserNotInSessionException
		String username = bd.getUsernameLoggedInByToken(userToken);
		//throws DocumentDoesNotExistException
		SpreadSheet sheet = getSpreadSheet(sheetId);
		//throws AccessException
		if ( !sheet.isOwnedBy(username) && !sheet.canBeReadBy(username) )
			throw new AccessException(username, sheetId);
		//throws ExportException
		xml = sheet.exportToXML();  //modified by Calisto
	}

	public Element getResult () {
		return this.xml;
	}
}
