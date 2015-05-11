
package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.domain.ADD;
import pt.tecnico.bubbledocs.domain.BinaryFunction;
import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.DIV;
import pt.tecnico.bubbledocs.domain.FunctionArguments;
import pt.tecnico.bubbledocs.domain.Literal;
import pt.tecnico.bubbledocs.domain.MUL;
import pt.tecnico.bubbledocs.domain.Reference;
import pt.tecnico.bubbledocs.domain.SUB;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.service.AssignBinaryFunctionToCellService;
import pt.tecnico.bubbledocs.exception.CellNotInSpreadSheetException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.InvalidValueException;
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

public class AssignBinaryFunctionToCellIntegrator extends BubbleDocsIntegrator {

	private String userToken;
	private int docId;
	private String cellId;
	private String auxBinaryFunction;
	private String docname;
	private String result;


	public AssignBinaryFunctionToCellIntegrator(String userToken, int docId, 
			String cellId, String auxBinaryFunction) {
		this.userToken = userToken;
		this.docId = docId;
		this.cellId = cellId;
		this.auxBinaryFunction = auxBinaryFunction;
	}

	@Override
	protected void dispatch(){// throws BubbleDocsException{
		
		AssignBinaryFunctionToCellService service = new AssignBinaryFunctionToCellService(this.userToken, this.docId, this.cellId, this.auxBinaryFunction);
		service.execute();
	}
	
	public final String getResult() {
		return "1";//""+service.getResult();
	}
	
/*
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
		int opNumber   = Integer.parseInt(parseOpRef[0]);
		//cellReference = celula referenciada dentro da funcao binaria
		int rowCellReference    = Integer.parseInt(parseOpRef[1]);
		int columnCellReference = Integer.parseInt(parseOpRef[2]);
				
		//nao necessario so se verifica no calc value
		/*int rowSpreadSheet    = sheet.getNumberRows();
		int columnSpreadSheet = sheet.getNumberColumns();
		// testa se a celula referenciada existe nas dimensoes da spreadsheet
		if (!(rowCellReference    >= 0) ||
			!(rowCellReference    <= rowSpreadSheet) ||
			!(columnCellReference >= 0) ||
			!(columnCellReference <= columnSpreadSheet))
			throw new CellNotInSpreadSheetException(rowCellReference, columnCellReference, docId);
		*/
		/*
		Reference referenceAux = new Reference
				(sheet, rowCellReference, columnCellReference);
		Literal literalAux = new Literal(opNumber);
		
		
		if (referenceAux.getCellReference().getProtect())
			throw new ProtectedCellException(rowCell, columnCell);
		

		switch (binaryFunction) {
		case "ADD":
			ADD binaryFuncA = new ADD(literalAux,referenceAux);
			sheet.addContent(binaryFuncA, rowCell, columnCell);
			this.result = ""+binaryFuncA.getContentValue();
			return;
		case "SUB":
			SUB binaryFuncS = new SUB(literalAux,referenceAux);
			sheet.addContent(binaryFuncS, rowCell, columnCell);
			this.result = ""+binaryFuncS.getContentValue();
			return;
		case "MUL":
			MUL binaryFuncM = new MUL(literalAux,referenceAux);
			sheet.addContent(binaryFuncM, rowCell, columnCell);
			this.result = ""+binaryFuncM.getContentValue();
			return;
		case "DIV":
			DIV binaryFuncD = new DIV(literalAux,referenceAux);
			sheet.addContent(binaryFuncD, rowCell, columnCell);
			this.result = ""+binaryFuncD.getContentValue();
			return; 
		default:
			throw new InvalidValueException();
		}
	}
	public final String getResult() {
		return result+"";
	}
*/
	
}