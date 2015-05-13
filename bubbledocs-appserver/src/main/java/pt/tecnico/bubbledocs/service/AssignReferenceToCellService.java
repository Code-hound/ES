
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

public class AssignReferenceToCellService extends BubbleDocsService {

	private String userToken;
	private int docId;
	private String cellId;
	private String reference;
	private String docname;

	public AssignReferenceToCellService(String userToken, int docId, String cellId,
			String reference) {
		this.userToken = userToken;
		this.docId = docId;
		this.cellId = cellId;
		this.reference = reference;
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

		String[] rowAndColumnCell = cellId.split(";");
		int rowCell    = Integer.parseInt(rowAndColumnCell[0]);
		int columnCell = Integer.parseInt(rowAndColumnCell[1]);

		String[] rowAndColumnContent = reference.split(";");
		int rowCellReference    = Integer.parseInt(rowAndColumnContent[0]);
		int columnCellReference = Integer.parseInt(rowAndColumnContent[1]);

		int rowSpreadSheet    = sheet.getNumberRows();
		int columnSpreadSheet = sheet.getNumberColumns();
		
		// testa se cellId existe nas dimensoes da spreadsheet
		if (!(rowCell    >= 0) ||
			!(rowCell    <= rowSpreadSheet) ||
			!(columnCell >= 0) ||
			!(columnCell <= columnSpreadSheet))
			throw new CellNotInSpreadSheetException
			(rowCellReference, columnCellReference, docId);

		Reference referenceAux = new Reference
				(sheet, rowCellReference, columnCellReference);
		
		Reference referenceCellId = new Reference
				(sheet, rowCell, columnCell);
		//para saber se a celula esta protegida antes de fazer assign
		if (referenceCellId.getCellReference().getProtect())
			throw new ProtectedCellException(rowCell, columnCell);

		sheet.addContent(referenceAux, rowCell, columnCell);
		//Cell rowCell;columnCell now has Content of type Reference
	}

	public final String getResult() {
		return this.reference;
	}
}
