package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.service.CreateSpreadSheetService;

/*
 * CREATE SPREADSHEET
 * 
 * Cria um novo documento do tipo SpreadSheet
 * Recebe o token do utilizador que quer criar a spreadsheet,
 * o nome do novo documento, o numero de linhas e o numero de colunas
 * 
 * @author: Francisco Silveira
 * @author: Luis Ribeiro Gomes
 * 
 */

public class CreateSpreadSheetIntegrator extends BubbleDocsIntegrator {

	private int numRows;
	private int numColumns;
	private String spreadsheetName;
	private String userToken;

	private int id;


	public CreateSpreadSheetIntegrator(String userToken, String name, int rows, int columns) {
		this.numRows = rows;
		this.numColumns = columns;
		this.spreadsheetName = name;
		this.userToken = userToken;
	}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException{
		
		CreateSpreadSheetService service = new CreateSpreadSheetService(this.userToken, this.spreadsheetName, this.numRows, this.numColumns);
		service.execute();
		
		this.id = service.getId();

	}

}
