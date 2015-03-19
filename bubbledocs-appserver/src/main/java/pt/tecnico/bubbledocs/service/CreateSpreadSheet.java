package pt.tecnico.bubbledocs.service;

public class CreateSpreadSheet extends BubbleDocsService {
	
	private int sheetId;  // id of the new sheet

    public int getSheetId() {
        return sheetId;
    }

    public CreateSpreadSheet(String userToken, String name, int rows,
            int columns) {
	// add code here
    }

    @Override
    protected void dispatch() throws BubbleDocsException {
	// add code here
    }
	
	
	
	
	
	
	private int numLines;
	private int numRows;
	private String spreadsheetName;
	private String username;
	
	public CreateSpreadSheetService (int numlines, int numRows, String spreadsheetName, String username) {
		this.numLines = numLines;
		this.numRows = numRows;
		this.spreadsheetName = spreadsheetName;
		this.username = username;
	}
}
