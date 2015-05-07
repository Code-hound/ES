package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
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

public class CreateSpreadSheetService extends BubbleDocsService {

	private int numRows;
	private int numColumns;
	private String spreadsheetName;
	private String userToken;
	private int id;

	public CreateSpreadSheetService(String userToken, String name, int rows, int columns) {
		this.numRows = rows;
		this.numColumns = columns;
		this.spreadsheetName = name;
		this.userToken = userToken;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		String username = resetUserLastAccess(userToken);

		//throws UserNotInSessionException
		if (username == null)
			throw new UserNotInSessionException(username);

		SpreadSheet sheet = getBubbleDocs().createSpreadSheet(getUser(username), spreadsheetName, numRows, numColumns);
		
		if (sheet == null)
			throw new UserAlreadyHasThisDocumentException(username, spreadsheetName);
		
		this.id = sheet.getId();
		/*
		}
		catch (UserNotInSessionException | UserAlreadyHasThisDocumentException ex) {
			System.out.println(ex.getMessage());
		}
		*/
	}

	public int getId() {
		return this.id;
	}
}
