package pt.tecnico.bubbledocs.service;

import java.util.ArrayList;

import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.Content;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class GetSpreadSheetContent extends BubbleDocsService {
	private String userToken;
	private int docId;
	private String[][] grid;
	private int rowNumber;
	private int columnNumber;

	public GetSpreadSheetContent(String userToken, int docId) {
		this.userToken = userToken;
		this.docId = docId;
	}
	
	public void dispatch() {
		String username = resetUserLastAccess(userToken);
		if (username == null)
			throw new UserNotInSessionException(username);
		
		SpreadSheet sheet = getSpreadSheet(docId);
		if (!canBeReadBy(sheet, username) && !isOwnedBy(sheet, username)) {
			throw new InvalidAccessException(username, docId, "READ");
		}
		
		//System.out.println(sheet);
		ArrayList<Cell> cells = new ArrayList<Cell>(sheet.getCellsSet());
		grid = new String[sheet.getNumberRows()][sheet.getNumberColumns()];
		int value;
		
		for (Cell cell : cells) {
			if (cell.getContent() != null) {
				grid[cell.getCellRow()-1][cell.getCellColumn()-1] = 
						Integer.toString(cell.getValue());
			}
			else {
				grid[cell.getCellRow()-1][cell.getCellColumn()-1] = "";
			}
		}
	}
	
	public String[][] getResult() {
		return grid;
	}
}
