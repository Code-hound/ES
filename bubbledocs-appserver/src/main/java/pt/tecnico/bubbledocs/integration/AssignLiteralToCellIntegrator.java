package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.service.AssignLiteralToCellService;

public class AssignLiteralToCellIntegrator extends BubbleDocsIntegrator {
	
	private int docId;
	private String cellId;
	private String literal;
	private String userToken;
	//private SpreadSheet sheet;

	private String result;
	
	public AssignLiteralToCellIntegrator(String userToken, int docId, String cellId, String literal) {
		this.docId = docId;
		this.cellId = cellId;
		this.literal = literal;
		this.userToken = userToken;
	}

	@Override
	protected void dispatch() throws BubbleDocsException{
		
		AssignLiteralToCellService service = new AssignLiteralToCellService(this.userToken, this.docId, this.cellId, this.literal);
		service.execute();
		
		this.result = service.getResult();

	}
	
	public final String getResult() {
		return this.result;
	}
	
}
