package pt.tecnico.bubbledocs.service;

import java.util.ArrayList;

import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.SpreadSheet;

import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;

public class GetSpreadSheetContentService extends BubbleDocsService {

	private String userToken;
	private int docId;

	private String[][] result;

	public GetSpreadSheetContentService(String userToken, int docId) {
		this.userToken = userToken;
		this.docId = docId;
	}

	public String[][] getResult() {
		return result;
	}

	public void dispatch() {

		String username = resetUserLastAccess(this.userToken);

		//throws UserNotInSessionException
		if ( userIsNotValid(username) )
			throw new UserNotInSessionException(username);
		
		//throws DocumentDoesNotExistException
		SpreadSheet doc = getSpreadSheet(docId);
		String docname = doc.getSpreadSheetName();

		//throws userCannotRead
		if ( userCannotRead(doc, username) ) {
			throw new InvalidAccessException(username, docname, "READ");
		}

		ArrayList<Cell> cells = new ArrayList<Cell>(doc.getCellsSet());
		this.result = new String[doc.getNumberRows()][doc.getNumberColumns()];
		
		for (Cell cell : cells) {

			if (cell.getContent() != null) {
				this.result[cell.getCellRow()-1][cell.getCellColumn()-1] = Integer.toString(cell.getValue());
			}
			else {
				this.result[cell.getCellRow()-1][cell.getCellColumn()-1] = "";
			}

		}

	}

}
