//FALTA: 
//-->tokens e permissoes

package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.Reference;

//import pt.tecnico.bubbledocs.*;
//import pt.tecnico.bubbledocs.service.*;

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
    	int rowCell = Integer.parseInt(rowAndColumnCell[0]); 
    	int columnCell = Integer.parseInt(rowAndColumnCell[1]);
    	
    	String[] rowAndColumnContent = reference.split(";");
    	int rowCellReference = Integer.parseInt(rowAndColumnContent[0]); 
    	int columnCellReference = Integer.parseInt(rowAndColumnContent[1]);
    	String docIdString = "" + docId;
    	
    	int rowSpreadSheet = getSpreadSheet(docIdString).getNumberColumns();  
    	int columnSpreadSheet = getSpreadSheet(docIdString).getNumberRows();
    	
    	//testa se a celula existe nas dimensoes da spreadsheet
    	if ((rowCellReference >= 0) && (rowCellReference <= rowSpreadSheet))
    	{
    		if ((columnCellReference >= 0) && (columnCellReference <= columnSpreadSheet))
    		{
    			 Reference referenceAux= new Reference (getSpreadSheet(docIdString), rowCellReference, columnCellReference);
    			getSpreadSheet(docIdString).addContent(referenceAux, rowCell, columnCell);
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
     	
    }
     
    public final String getResult() {
        return result;
    }
}
