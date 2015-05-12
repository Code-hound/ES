package pt.tecnico.bubbledocs.integration.component;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.service.AssignReferenceToCellService;

/*
 * ASSIGN REFERENCE CELL
 * 
 * Atribui referencia a uma celula.
 * 
 * @author: Joao Pedro Zeferino
 * @author: Francisco Maria Calisto
 * 
 */

public class AssignReferenceToCellIntegrator extends BubbleDocsIntegrator {

	private String userToken;
	private int docId;
	private String cellId;
	private String reference;
	
	private String result;

	public AssignReferenceToCellIntegrator(String userToken, int docId, String cellId,
			String reference) {
		this.userToken = userToken;
		this.docId = docId;
		this.cellId = cellId;
		this.reference = reference;
	}

	@Override
	protected void dispatch() throws BubbleDocsException{
		
		AssignReferenceToCellService service = new AssignReferenceToCellService(this.userToken, this.docId, this.cellId, this.reference);
		service.execute();
		
		this.result = service.getResult();

	}
	
	public final String getResult() {
		return this.result;
	}
	
}
