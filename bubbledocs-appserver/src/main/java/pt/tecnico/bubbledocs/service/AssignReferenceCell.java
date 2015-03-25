//FALTA: 
//-->tokens e permissoes
//-->tirar o * no import, para ficar menos abrangente
//-->confirmar se e' precisso importar a funcao split

//package pt.tecnico.bubbledocs.service;
//
////redefinir para ficar apenas os importes necessarios necessarios
//import pt.tecnico.bubbledocs.*;
//// add needed import declarations
//
//public class AssignReferenceCell extends BubbleDocsService {
//    private String result;
//
//    public AssignReferenceCell(String tokenUser, int docId, String cellId,
//            String reference) {
//    	
//    /*	this.tokenUser = tokenUser;
//    	this.docId = docId;
//    	this.cellId = cellId;
//    	this.reference = reference;
//    */	
//	// add code here
//    }
//
//    @Override
//    protected void dispatch(){ //throws BubbleDocsException {
//	// add code here
//    	
//    	String[] rowAndColumnCell = cellId.split(";");
//    	String rowCell = rowAndColumnCell[0]; 
//    	String collumnCell = rowAndColumnCell[1];
//    	
//    	String[] rowAndColumnContent = reference.split(";");
//    	String rowContent = rowAndColumnContent[0]; 
//    	String collumnContent = rowAndColumnContent[1];
//    	
//    	int rowSpreadSheet = getBubbleDocs().getSpreadSheetById(docId).get_numberColumns();
//    	int collumnSpreadSheet = getBubbleDocs().getSpreadSheetById(docId).get_numberRows();
//    	
//    	//se entrar nos dois if a reference aponta 
//    	//para uma cell existente nas dimensoes da spreadsheet
//    	if ((rowContent >= 0) && (rowContent <= rowSpreadSheet))
//    	{
//    		if ((columnContent >= 0) && (columnContent <= columnSpreadSheet))
//    		{
//    			getBubbleDocs().getSpreadSheetById(docId).addContent(reference, row, column);
//    		}
//    		else 
//    		{
//    			//throw new BubbleDocsException();
//    		}
//    	}
//    	else 
//    	{
//    		//throw new BubbleDocsException();
//    	}
//    	
//    }
//
//    public final String getResult() {
//        return result;
//    }
//}
