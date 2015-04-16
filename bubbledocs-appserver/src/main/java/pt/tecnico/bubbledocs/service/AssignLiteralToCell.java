
package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.Literal;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;
import pt.tecnico.bubbledocs.exception.UserCantWriteException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class AssignLiteralToCell extends BubbleDocsService {
	
	private int docId;
	private String cellId;
	private String literal;
	private String tokenUser;
	private SpreadSheet sheet;

	private String result;
	private String username;
	
	public AssignLiteralToCell(String tokenUser, int docId, String cellId, 
			String literal) {

		this.docId = docId;
		this.cellId = cellId;
		this.literal = literal;
		this.tokenUser = tokenUser;
	}

	@Override
	protected void dispatch() { // throws BubbleDocsException {
		//BubbleDocs bd = getBubbleDocs();
		User user = getBubbleDocs().getUserLoggedInByToken(tokenUser);
		
		if (user==null)
			throw new UserNotInSessionException(tokenUser);
		
		String username = user.getUsername();
		resetUserLastAccess(user);
		
		this.sheet = getSpreadSheet(docId);
		if(sheet.canBeWrittenBy(username)){
			String[] rowAndColumn = cellId.split(";");
			int row = Integer.parseInt(rowAndColumn[0]);
			int column = Integer.parseInt(rowAndColumn[1]);
			Cell cell = sheet.getCell(row, column);
			
			if (cell.getProtect())
				throw new ProtectedCellException(row, column);
			else {
				sheet.addContent(new Literal
						(Integer.parseInt(literal)),row, column);
				result = literal;
			}
			
		}
		else throw new UserCantWriteException(username, docId);
	}

	public String getResult() {
		return result;
	}

}
