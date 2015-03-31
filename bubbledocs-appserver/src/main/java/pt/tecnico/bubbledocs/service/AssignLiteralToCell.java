//FALTA: 
//-->tokens e permissoes
//-->tirar o * no import, para ficar menos abrangente
//-->confirmar se e' precisso importar a funcao split


package pt.tecnico.bubbledocs.service;

//redefinir para ficar apenas os importes necessarios necessarios
//import pt.tecnico.bubbledocs.*;
import pt.tecnico.bubbledocs.domain.Literal;
//import pt.tecnico.bubbledocs.service.*;


// add needed import declarations

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
    	getBubbleDocs().getSpreadSheetById(docIdString).addContent(new Literal(Integer.parseInt(literal)), row, column);
    	result = literal;
    	
    }

    public String getResult() {
        return result;
    }
 
}
