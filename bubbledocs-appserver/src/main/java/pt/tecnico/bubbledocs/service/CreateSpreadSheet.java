package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.User;

public class CreateSpreadSheet extends BubbleDocsService {

	// private int sheetId; // id of the new sheet
	// UNNECESSARY, SHEET ID IS HANDLED BY BUBBLEDOCS AT THE DOMAIN LEVEL

	private int numRows;
	private int numColumns;
	private String spreadsheetName;
	private String userToken;

	public CreateSpreadSheet(String userToken, String name, int rows,
			int columns) {
		this.numRows = rows;
		this.numColumns = columns;
		this.spreadsheetName = name;
		this.userToken = userToken;
	}

	@Override
	protected void dispatch() {
		User user = getUser(userToken);
		if (user != null) {
			getBubbleDocs().createSpreadSheet(user, spreadsheetName, numRows,
					numColumns);
		}
	}
}
