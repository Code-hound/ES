package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.service.GetSpreadSheetContentService;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;

/*
 * GET SPREADSHEET CONTENT
 * 
 * Obtem um  documento do tipo SpreadSheet
 * Recebe o token do utilizador que quer obter a spreadsheet,
 * e o nome do documento
 * 
 * @author: Francisco Silveira
 * @author: Luis Ribeiro Gomes
 * 
 */

public class GetSpreadSheetContentIntegrator extends BubbleDocsIntegrator {

	private String userToken;
	private int docId;

	private String[][] result;

	public GetSpreadSheetContentIntegrator(String userToken, int docId) {
		this.userToken = userToken;
		this.docId = docId;
	}

	public String[][] getResult() {
		return result;
	}

	@Override
	protected void dispatch() throws BubbleDocsException{
		
		GetSpreadSheetContentService service = new GetSpreadSheetContentService(this.userToken, this.docId);
		service.execute();
		
		this.result = service.getResult();

	}

}
