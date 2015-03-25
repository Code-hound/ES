//FALTA: 
//-->tokens e permissoes
//-->tirar o * no import, para ficar menos abrangente
//-->confirmar se e' precisso importar a funcao split

//
//package pt.tecnico.bubbledocs.service;
//
////redefinir para ficar apenas os importes necessarios necessarios!!!!!
//import pt.tecnico.bubbledocs.*;
////split e preciso importar?????
//
//// add needed import declarations
//
//public class AssignLiteralCell extends BubbleDocsService {
//    private String result;
//
//    public AssignLiteralCell(String accessUsername, int docId, String cellId,
//            String literal) {
//    	
//    	this.accessUsername = acceddUsername;
//    	this.docId = docId; 
//    	this.cellId = cellId;
//    	this.literal = literal;
//    
//    }
//
//    @Override
//    protected void dispatch(){ //throws BubbleDocsException {
//	// add code here
//    	
//    	String[] rowAndColumn = cellId.split(";");
//    	String row = rowAndColumn[0]; 
//    	String collumn = rowAndColumn[1];
//    	
//    	getBubbleDocs().getSpreadSheetById(docId).addContent(literal, row, column);
//    	result = literal;
//    	
//    }
//
//    public String getResult() {
//        return result;
//    }
//
//}
