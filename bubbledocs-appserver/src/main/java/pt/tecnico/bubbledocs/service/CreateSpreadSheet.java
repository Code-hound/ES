package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.UserAlreadyHasThisDocumentException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

/*
 * CREATE SPREADSHEET
 * 
 * Cria um novo documento do tipo SpreadSheet
 * Recebe o token do utilizador que quer criar a spreadsheet,
 * o nome do novo documento, o numero de linhas e o numero de colunas
 * 
 * @author: Francisco Silveira
 * 
 */

public class CreateSpreadSheet extends BubbleDocsService {

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
		BubbleDocs bd = getBubbleDocs();
		
		try {
			User user = bd.getUserLoggedInByToken(userToken);
			if (user != null) {
				bd.createSpreadSheet(user, spreadsheetName, numRows,
						numColumns);
			}
		}
		catch (UserNotInSessionException | UserAlreadyHasThisDocumentException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
