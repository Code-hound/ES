
package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.Literal;
import pt.tecnico.bubbledocs.domain.Reference;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.CellNotInSpreadSheetException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

/*
 * ASSIGN REFERENCE CELL
 * 
 * Atribui referencia a uma celula.
 * 
 * @author: Joao Pedro Zeferino
 * @author: Francisco Maria Calisto
 * 
 */

public class AssignBinaryFunctionToCellService extends BubbleDocsService {

	private String userToken;
	private int docId;
	private String cellId;
	private String auxBinaryFunction;
	private String docname;


	public AssignBinaryFunctionToCellService(String userToken, int docId, 
			String cellId, String auxBinaryFunction) {
		this.userToken = userToken;
		this.docId = docId;
		this.cellId = cellId;
		this.auxBinaryFunction = auxBinaryFunction;
	}

	@Override
	protected void dispatch() {

		String username = resetUserLastAccess(userToken);
		
		if (username == null)
			throw new UserNotInSessionException(username);
		
		SpreadSheet sheet = getSpreadSheet(docId);
		this.docname = sheet.getSpreadSheetName();

		if (!canBeWrittenBy(sheet, username)) {
			throw new InvalidAccessException(username, this.docname, "WRITE");
		}
		
		//cell = celula cujo conteudo vai passar a ter a funcao binaria
		String[] rowAndColumnCell = cellId.split(";");
		int rowCell    = Integer.parseInt(rowAndColumnCell[0]);
		int columnCell = Integer.parseInt(rowAndColumnCell[1]);
		
//Example:=ADD(2,3;1) 
//binaryFuntion=ADD
		String binaryFunction = auxBinaryFunction.substring(1,4);
				
//auxOperator=2,3;1)
		String auxOperator = auxBinaryFunction.substring(5);

		//getting operator
		String delims = "[,;)]+";
		String[] parseOpRef = auxOperator.split(delims);
		int operator   = Integer.parseInt(parseOpRef[0]);
		//cellReference = celula referenciada dentro da funcao binaria
		int rowCellReference    = Integer.parseInt(parseOpRef[1]);
		int columnCellReference = Integer.parseInt(parseOpRef[2]);
				
		//getting 
		int rowSpreadSheet    = sheet.getNumberRows();
		int columnSpreadSheet = sheet.getNumberColumns();
	
		// testa se a celula referenciada existe nas dimensoes da spreadsheet
		if (!(rowCellReference    >= 0) ||
			!(rowCellReference    <= rowSpreadSheet) ||
			!(columnCellReference >= 0) ||
			!(columnCellReference <= columnSpreadSheet))
			throw new CellNotInSpreadSheetException(rowCellReference, columnCellReference, docId);

		Reference referenceAux = new Reference
				(sheet, rowCellReference, columnCellReference);

		if (referenceAux.getCellReference().getProtect())
			throw new ProtectedCellException(rowCell, columnCell);
		
		//falta o caseof e escolher a funcao binaria, e atribui-la como conteúdo
		
		
		
		
		//verifica se a celula a ser escrita por efectivamente ser escrita
/////   sheet.addContent(BINARYFUNCTION, rowCell, columnCell);
		//Cell rowCell;columnCell now has Content of type BinaryFunction
	}

	public final String getResult() {
		return "1";
	}
}
