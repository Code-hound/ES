
package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.Literal;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;
import pt.tecnico.bubbledocs.exception.UserCantWriteException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class AssignLiteralToCell extends BubbleDocsService {
	
	private int docId;
	private String cellId;
	private String literal;
	private String userToken;
	private SpreadSheet sheet;
	
	public AssignLiteralToCell(String userToken, int docId, String cellId, String literal) {

		this.docId = docId;
		this.cellId = cellId;
		this.literal = literal;
		this.userToken = userToken;
	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		String username = resetUserLastAccess(userToken);
		
		if (username == null)
			throw new UserNotInSessionException(username);
		
		this.sheet = getSpreadSheet(docId);
		
		if(!canBeWrittenBy(sheet, username)){
			throw new UserCantWriteException(username, docId);
		}
			
		String[] rowAndColumn = cellId.split(";");
		int row = Integer.parseInt(rowAndColumn[0]);
		int column = Integer.parseInt(rowAndColumn[1]);
		Cell cell = sheet.getCell(row, column);
		//System.out.println("Rows:"+row+" Columns:"+column);
		
		if (cell.getProtect())
			throw new ProtectedCellException(row, column);
		else {
			sheet.addContent(new Literal
					(Integer.parseInt(literal)),row, column);
		}
	}

	public String getResult() {
		return this.literal;
	}

}
