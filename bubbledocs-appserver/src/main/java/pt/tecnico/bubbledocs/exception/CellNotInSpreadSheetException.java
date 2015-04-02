package pt.tecnico.bubbledocs.exception;

/*
 * USER CAN'T WRITE EXCEPTION
 * 
 * Ficheiro "copiado" do exemplo phonebook
 * 
 * @author: Francisco Silveira
 */

public class CellNotInSpreadSheetException extends BubbleDocsException {
	private int row;
	private int column;
	private int docId;
	
	public CellNotInSpreadSheetException(int row, int column, int docId) {
		super();
		this.row = row;
		this.column = column;
		this.docId = docId;
	}
	
	public int getRow () {
		return this.row;
	}
	
	public int getColumn () {
		return this.column;
	}
	
	public int getDocId () {
		return this.docId;
	}
	
	@Override
	public String getMessage() {
		return "CellNotInSpreadSheetException: Cell [" + this.row + ":" + this.column + "] does not exist in spreadsheet with ID: " + this.docId + ".";
	}
}
