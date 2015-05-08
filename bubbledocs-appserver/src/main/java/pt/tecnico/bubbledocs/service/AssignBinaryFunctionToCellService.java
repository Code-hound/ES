
package pt.tecnico.bubbledocs.service;

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
	private String binaryFunction;

	public AssignBinaryFunctionToCellService(String userToken, int docId, String cellId,
			String binaryFunction) {
		this.userToken = userToken;
		this.docId = docId;
		this.cellId = cellId;
		this.binaryFunction = binaryFunction;
	}

	@Override
	protected void dispatch() {

		String username = resetUserLastAccess(userToken);
		
		if (username == null)
			throw new UserNotInSessionException(username);
		/*
		if(!getSpreadSheet(docId).canBeWrittenBy(username))
			throw new UserCantWriteException(username, docId);
		*/
		SpreadSheet sheet = getSpreadSheet(docId);
		if (!canBeWrittenBy(sheet, username)) {
			throw new InvalidAccessException(username, docId, "WRITE");
		}

		String[] rowAndColumnCell = cellId.split(";");
		int rowCell    = Integer.parseInt(rowAndColumnCell[0]);
		int columnCell = Integer.parseInt(rowAndColumnCell[1]);

		int rowSpreadSheet    = sheet.getNumberRows();
		int columnSpreadSheet = sheet.getNumberColumns();
		//System.out.println("Rows:"+rowSpreadSheet+" Columns:"+columnSpreadSheet);

		// testa se a celula existe nas dimensoes da spreadsheet
		/*if (!(rowCellReference    >= 0) ||
			!(rowCellReference    <= rowSpreadSheet) ||
			!(columnCellReference >= 0) ||
			!(columnCellReference <= columnSpreadSheet))
			throw new CellNotInSpreadSheetException(rowCellReference, columnCellReference, docId);

		Reference referenceAux = new Reference
				(sheet, rowCellReference, columnCellReference);

		if (referenceAux.getCellReference().getProtect())
			throw new ProtectedCellException(rowCell, columnCell);
		
		sheet.addContent(referenceAux, rowCell, columnCell);
		//Cell rowCell;columnCell now has Content of type Reference
		 */
	}

	public final String getResult() {
		return "1";
	}
}
