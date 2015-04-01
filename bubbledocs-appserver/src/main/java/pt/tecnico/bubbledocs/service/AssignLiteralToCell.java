//FALTA: 
//-->Excepssion's: [user can't write] [cell protected] [cell not in spreadsheet]

package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.Literal;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;

public class AssignLiteralToCell extends BubbleDocsService {
	
	private int docId;
	private String cellId;
	private String literal;
	private String tokenUser;

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
		if(getSpreadSheet(docId).canBeWrittenBy(username)){
			String[] rowAndColumn = cellId.split(";");
			String rowAux = rowAndColumn[0];
			String columnAux = rowAndColumn[1];
	
			int row = Integer.parseInt(rowAux);
			int column = Integer.parseInt(columnAux);
	
			for (Cell cell : getSpreadSheet(docId).getCellsSet()) {
				if (cell.getCellRow() == row && cell.getCellColumn() == column) {
					if (cell.getProtect()) {
						throw new ProtectedCellException(row, column);
					} else {
						getSpreadSheet(docId)
								.addContent(new Literal(Integer.parseInt(literal)),
										row, column);
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
