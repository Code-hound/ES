//FALTA: 
//-->Excepssion's: [user can't write] [cell protected] [cell not in spreadsheet]

package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.Literal;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;

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
		username = getBubbleDocs().getUserLoggedInByToken(tokenUser).getName();
		this.sheet = getSpreadSheet(docId);
		
		if(sheet.canBeWrittenBy(username)){
			String[] rowAndColumn = cellId.split(";");
			//String rowAux = rowAndColumn[0];
			//String columnAux = rowAndColumn[1];
	
			int row = Integer.parseInt(rowAndColumn[0]);
			int column = Integer.parseInt(rowAndColumn[1]);
	
			for (Cell cell : sheet.getCellsSet()) {
				if (cell.getCellRow() == row && cell.getCellColumn() == column) {
					if (cell.getProtect()) {
						throw new ProtectedCellException(row, column);
					}
					else {
						sheet.addContent(new Literal(
								Integer.parseInt(literal)),row, column);
					}
				}
			}

			result = literal;
		}
	}

	public String getResult() {
		return result;
	}

}
