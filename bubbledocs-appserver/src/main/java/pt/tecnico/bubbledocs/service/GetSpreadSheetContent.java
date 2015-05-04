package pt.tecnico.bubbledocs.service;

import java.util.ArrayList;

import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.Content;
import pt.tecnico.bubbledocs.domain.SpreadSheet;

public class GetSpreadSheetContent extends BubbleDocsService {
	private String userToken;
	private int docId;
	private String[][] grid;
	private int rowNumber;
	private int columnNumber;

	public GetSpreadSheetContent(String userToken, int docId) {
		this.userToken = userToken;
		this.docId = docId;
	}
	
	public void dispatch() {
		SpreadSheet sheet = getSpreadSheet(docId);
		//System.out.println(sheet);
		ArrayList<Cell> cells = new ArrayList<Cell>(sheet.getCellsSet());
		grid = new String[sheet.getNumberRows()][sheet.getNumberColumns()];
		int value;
		
		for (Cell cell : cells) {
			if (cell.getContent() != null) {
				grid[cell.getCellRow()-1][cell.getCellColumn()-1] = 
						Integer.toString(cell.getValue());
			}
			else {
				grid[cell.getCellRow()-1][cell.getCellColumn()-1] = "";
			}
		}
	}
	
	public String[][] getResult() {
		return grid;
	}
}
