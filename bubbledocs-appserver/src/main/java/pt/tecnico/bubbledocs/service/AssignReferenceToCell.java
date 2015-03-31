//FALTA: 
//-->tokens e permissoes
//-->tirar o * no import, para ficar menos abrangente
//-->confirmar se e' precisso importar a funcao split

package pt.tecnico.bubbledocs.service;

////redefinir para ficar apenas os importes necessarios necessarios
import pt.tecnico.bubbledocs.*;
import pt.tecnico.bubbledocs.service.*;

//// add needed import declarations

/*
 * ASSIGN REFERENCE CELL
 * 
 * Atribui referencia a uma celula.
 * 
 * @author: Joao Pedro Zeferino
 * @author: Francisco Maria Calisto
 * 
 */

public class AssignReferenceToCell extends BubbleDocsService {
	
	/*
	 * Added by Calisto
	 */
    
	private String result;
    private String tokenUser;
    private int docId;
    private String cellId;
    private String reference;

    public AssignReferenceToCell(String tokenUser, int docId, String cellId, String reference)
    {
    
    	this.tokenUser = tokenUser;
    	this.docId = docId;
    	this.cellId = cellId;
    	this.reference = reference;
	
    }
    
    @Override
    protected void dispatch(){ //throws BubbleDocsException {
	
    	String[] rowAndColumnCell = cellId.split(";");
    	String rowCell = rowAndColumnCell[0]; 
    	String collumnCell = rowAndColumnCell[1];
    	
    	String[] rowAndColumnContent = reference.split(";");
    	String rowContent = rowAndColumnContent[0]; 
    	String collumnContent = rowAndColumnContent[1];
    	
    	String docIdString = "" + docId;
    	//dimensoes da spreadsheet
    	
    	// descomentar
    	
    /*	String rowSpreadSheet = getBubbleDocs().getSpreadSheetById(docIdString).getNumberColumns();  
    	String collumnSpreadSheet = getBubbleDocs().getSpreadSheetById(docIdString).getNumberRows();
    	
    	//se entrar nos dois if a reference aponta 
    	//para uma cell existente nas dimensoes da spreadsheet
    	if ((rowContent >= 0) && (rowContent <= rowSpreadSheet))
    	{
    		if ((columnContent >= 0) && (columnContent <= columnSpreadSheet))
    		{
    			getBubbleDocs().getSpreadSheetById(docId).addContent(reference, row, column);
    		}
    		else 
    		{
    			//throw new BubbleDocsException();
    		}
    	}
    	else 
    	{
    		//throw new BubbleDocsException();
    	}
    	*/ 
    	
    	// descomentar
    	
    }
     
    public final String getResult() {
        return result;
    }
}
