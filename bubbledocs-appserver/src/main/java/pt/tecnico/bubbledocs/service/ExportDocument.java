package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import org.jdom2.Element;

import java.lang.NullPointerException;

import pt.tecnico.bubbledocs.exception.AccessException;
import pt.tecnico.bubbledocs.exception.ExportException;
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
		try {

			SpreadSheet sheet    = getSpreadSheet(sheetId);
			String      username = getUser(userToken).getUsername();

			if ( sheet.getOwnerUsername() == username || sheet.canBeReadBy(username) )
				this.xml = sheet.exportToXML();
			else
				throw new AccessException(userToken, sheetId);

		} catch (NullPointerException e) {
			throw new ExportException("SpreadSheet");
		}
	}

	public Element getResult () {
		return this.xml;
	}
}
