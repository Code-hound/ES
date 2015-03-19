package pt.tecnico.bubbledocs.service;


public class CreateSpreadSheet extends BubbleDocsService {
	
	private int sheetId;  // id of the new sheet
	private int numRows;
	private int numColumns;
	private String spreadsheetName;
	private String username;
	
	public CreateSpreadSheet (String userToken, String name, int rows,
            int columns) {
		this.numRows = rows;
		this.numColumns = columns;
		this.spreadsheetName = name;
		this.userToken = userToken;
	}
	
	@Override
    protected void dispatch(){
	// add code here
    }
}
