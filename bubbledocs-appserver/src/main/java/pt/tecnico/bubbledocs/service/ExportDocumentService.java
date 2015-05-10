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


/*
 * Export Document Service
 * 
 * BubbleDocs Exporting Service
 * 
 * Description: It receives a SpreadSheet and a User Token
 * and exports it to XML if the User has access permissions.
 * 
 * @author: Luis Ribeiro Gomes
 */

public class ExportDocumentService extends BubbleDocsService {

    // the tokens
	private String userToken;
	private int    docId;

	private String docname;
	private byte[] result;

	public ExportDocumentService(String userToken, int docId) {
		this.userToken = userToken;
		this.docId = docId;
	}

	public String getDocname () {
		return this.docname;
	}

	public byte[] getResult() {
		return this.result;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		String username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (userIsNotValid(username)) {
			throw new UserNotInSessionException(username);
		}
		
		SpreadSheet doc = getSpreadSheet(docId);
		this.docname = doc.getSpreadSheetName();

		//throws InvalidAccessException
		if (userIsNotOwner(doc, username) && userCannotRead(doc, username)) {
			throw new InvalidAccessException(username, this.docname, "Read or Write");
		}





		XMLOutputter xml = new XMLOutputter();
		Document jdomDoc = new Document();

		jdomDoc.setRootElement(doc.exportToXML());

		//throws ExportDocumentException
		try {
			this.result = xml.outputString(jdomDoc).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ExportDocumentException();
		}





	}

}
