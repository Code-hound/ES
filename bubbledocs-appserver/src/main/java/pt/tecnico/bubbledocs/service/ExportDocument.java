package pt.tecnico.bubbledocs.service;

//the needed import declarations

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.User;
import org.jdom2.Element;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;

/*
 * Export Document
 * 
 * BubbleDocs Exporting Service
 * 
 * Description: It receives a SpreadSheet and a User Token
 * and exports it to XML if the User has access permissions.
 * 
 * @author: Luís Ribeiro Gomes
 * 
 */

public class ExportDocument extends BubbleDocsService {

	private String userToken;
	private String sheetId;

	public ExportDocument(String userToken, String sheetId) {
		this.userToken = userToken;
		this.sheetId = sheetId;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {
		SpreadSheet sheet = getBubbleDocs().getSpreadSheetById(sheetId);
		User user         = getBubbleDocs().getUserByUserName(userToken);
		//FIXME: if ( userToken has reading or writting or owning permissions )
			Element xml = sheet.exportToXML();
	}

}
