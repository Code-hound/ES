
package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.Reference;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.CellNotInSpreadSheetException;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;
import pt.tecnico.bubbledocs.exception.UserCantWriteException;
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

public class AssignReferenceToCell extends BubbleDocsService {

	private String result;
	private String tokenUser;
	private int docId;
	private String cellId;
	private String reference;
	private String username;

	public AssignReferenceToCell(String tokenUser, int docId, String cellId,
			String reference) {
		this.tokenUser = tokenUser;
		this.docId = docId;
		this.cellId = cellId;
		this.reference = reference;
	}

	@Override
	protected void dispatch() {
		User user = getBubbleDocs().getUserLoggedInByToken(tokenUser);

		if (user == null)
			throw new UserNotInSessionException(tokenUser);
		
		String username = user.getUsername();
		resetUserLastAccess(user);

		if(!getSpreadSheet(docId).canBeWrittenBy(username))
			throw new UserCantWriteException(username, docId);

		String[] rowAndColumnCell = cellId.split(";");
		int rowCell = Integer.parseInt(rowAndColumnCell[0]);
		int columnCell = Integer.parseInt(rowAndColumnCell[1]);

		String[] rowAndColumnContent = reference.split(";");
		int rowCellReference = Integer.parseInt(rowAndColumnContent[0]);
		int columnCellReference = Integer.parseInt(rowAndColumnContent[1]);

		int rowSpreadSheet = getSpreadSheet(docId).getNumberColumns();
		int columnSpreadSheet = getSpreadSheet(docId).getNumberRows();

		// testa se a celula existe nas dimensoes da spreadsheet
		if (!(rowCellReference    >= 0) ||
			!(rowCellReference    <= rowSpreadSheet) ||
			!(columnCellReference >= 0) ||
			!(columnCellReference <= columnSpreadSheet))
			throw new CellNotInSpreadSheetException(rowCellReference, columnCellReference, docId);

		Reference referenceAux = new Reference
				(getSpreadSheet(docId), rowCellReference, columnCellReference);

		if (referenceAux.getCellReference().getProtect())
			throw new ProtectedCellException(rowCell, columnCell);

		getSpreadSheet(docId).addContent(referenceAux, rowCell, columnCell);
		//Cell rowCell;columnCell now has Content of type Reference
		result = reference;
	}

	public final String getResult() {
		return result;
	}
}
