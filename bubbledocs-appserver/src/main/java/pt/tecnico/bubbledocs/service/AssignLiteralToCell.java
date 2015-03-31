//FALTA: 
//-->tokens e permissoes

package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.Literal;
//import pt.tecnico.bubbledocs.service.*;
//import pt.tecnico.bubbledocs.*;

public class AssignLiteralToCell extends BubbleDocsService {
    
	private String result;
	private String accessUsername;
	private int docId;
	private String cellId;
	private String literal;

    public AssignLiteralToCell(String accessUsername, int docId, String cellId, String literal) {
    
    	this.accessUsername = accessUsername;
    	this.docId = docId; 
    	this.cellId = cellId;
    	this.literal = literal;
    
    }

    @Override
    protected void dispatch(){ //throws BubbleDocsException {
	   	
       	String[] rowAndColumn = cellId.split(";");
    	String rowAux = rowAndColumn[0]; 
    	String columnAux = rowAndColumn[1];
    	
    	int row = Integer.parseInt(rowAux);
    	int column = Integer.parseInt(columnAux);
    	
    	String docIdString = "" + docId;
    	getSpreadSheet(docIdString).addContent(new Literal(Integer.parseInt(literal)), row, column);
    	result = literal;
    	
    }

    public String getResult() {
        return result;
    }
 
}
