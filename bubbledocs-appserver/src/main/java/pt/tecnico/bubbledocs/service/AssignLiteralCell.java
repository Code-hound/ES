package pt.tecnico.bubbledocs.service;

//redefinir para ficar apenas os importes necessários necessários!!!!!
import pt.tecnico.bubbledocs.*;
//split e preciso importar?????

// add needed import declarations



public class AssignLiteralCell extends BubbleDocsService {
    private String result;

    public AssignLiteralCell(String accessUsername, int docId, String cellId,
            String literal) {
    	
    	this.accessUsername = acceddUsername;
    	this.docId = docId; 
    	this.cellId = cellId;
    	this.literal = literal;
    	
    	
    	
    }

    @Override
    protected void dispatch() throws BubbleDocsException {
	// add code here
    	
    	String[] cellRow = cellId.split(";");
    	String row = cellRow[0]; 
    	String collumn = cellRow[1];
    	
    	row = parse.int(cellId);
    	//columns = 
    	getBubbleDocs().getSpreadSheetById(docId).addContent(literal, row, column);
    	result = literal;
    	
    }

    public String getResult() {
        return result;
    }

}
