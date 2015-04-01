package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.User;
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
<<<<<<< HEAD
/*
 * public class ExportDocument extends BubbleDocsService {
 * 
 * // the tokens private String userToken; private int sheetId;
 * 
 * public ExportDocument(String userToken, int sheetId) { this.userToken =
 * userToken; this.sheetId = sheetId; }
 * 
 * @Override protected void dispatch() throws BubbleDocsException { try {
 * 
 * SpreadSheet sheet = getSpreadSheet(sheetId); User user = getUser(userToken);
 * boolean write = sheet.getReadWriteUserOnly().contains(user); boolean read =
 * sheet.getReadOnlyUser().contains(user); boolean owns =
 * sheet.getOwner().getUsername() == userToken; Element xml;
 * 
 * if ( owns || read || write ) xml = sheet.exportToXML(); else throw new
 * AccessException(userToken, sheetId);
 * 
 * } catch (NullPointerException e) { throw new ExportException("SpreadSheet");
 * } }
 * 
 * }
 */
=======

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

			SpreadSheet sheet = getSpreadSheet(sheetId);
			User        user  = getUser(userToken);
			boolean write = sheet.getReadWriteUserOnly().contains(user);
			boolean read  = sheet.getReadOnlyUser().contains(user);
			boolean owns  = sheet.getOwner().getUsername() == userToken;

			if ( owns || read || write )
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
>>>>>>> changed functions
