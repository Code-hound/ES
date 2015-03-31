//FALTA: 
//-->tokens e permissoes

package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.Literal;
//import pt.tecnico.bubbledocs.service.*;
//import pt.tecnico.bubbledocs.*;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;

public class AssignLiteralToCell extends BubbleDocsService {
    
	private String result;
	private String accessUsername;
	private int docId;
	private String cellId;
	private String literal;
	private String tokenUser;

    public AssignLiteralToCell(String tokenUser, String accessUsername, int docId, String cellId, String literal) {
    
    	this.accessUsername = accessUsername;
    	this.docId = docId; 
    	this.cellId = cellId;
    	this.literal = literal;
    	this.tokenUser = tokenUser;
    }

    @Override
    protected void dispatch(){ //throws BubbleDocsException {
	   	
       	String[] rowAndColumn = cellId.split(";");
    	String rowAux = rowAndColumn[0]; 
    	String columnAux = rowAndColumn[1];
    	
    	int row = Integer.parseInt(rowAux);
    	int column = Integer.parseInt(columnAux);
    	
    	String docIdString = "" + docId;
    	
    	for (Cell cell : getSpreadSheet(docIdString).getCellsSet()) {
			if (cell.getCellRow() == row && cell.getCellColumn() == column) {
				if (cell.getProtect())
					{
					throw new ProtectedCellException(row, column);
					}
				else
				{
					getSpreadSheet(docIdString).addContent(new Literal(Integer.parseInt(literal)), row, column);
				}
			}
		}    	
    	
    	
		
    	result = literal;
    	
    }

    public String getResult() {
        return result;
    }
 
}
