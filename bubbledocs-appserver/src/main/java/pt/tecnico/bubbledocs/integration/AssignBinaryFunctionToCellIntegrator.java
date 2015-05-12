package pt.tecnico.bubbledocs.integration;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.service.AssignBinaryFunctionToCellService;

/*
 * ASSIGN REFERENCE CELL
 * 
 * Atribui referencia a uma celula.
 * 
 * @author: Joao Pedro Zeferino
 * @author: Francisco Maria Calisto
 * 
 */

public class AssignBinaryFunctionToCellIntegrator extends BubbleDocsIntegrator {

	private String userToken;
	private int docId;
	private String cellId;
	private String auxBinaryFunction;
	private String docname;

	private String result;


	public AssignBinaryFunctionToCellIntegrator(String userToken, int docId, 
			String cellId, String auxBinaryFunction) {
		this.userToken = userToken;
		this.docId = docId;
		this.cellId = cellId;
		this.auxBinaryFunction = auxBinaryFunction;
	}

	@Override
	protected void dispatch() throws BubbleDocsException{
		
		AssignBinaryFunctionToCellService service = new AssignBinaryFunctionToCellService(this.userToken, this.docId, this.cellId, this.auxBinaryFunction);
		service.execute();
		
		this.result = service.getResult();

	}
	
	public final String getResult() {
		return this.result;
	}

}
