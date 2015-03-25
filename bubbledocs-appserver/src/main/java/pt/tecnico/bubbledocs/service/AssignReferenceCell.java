package pt.tecnico.bubbledocs.service;

//redefinir para ficar apenas os importes necessários necessários
import pt.tecnico.bubbledocs.*;
// add needed import declarations

public class AssignReferenceCell extends BubbleDocsService {
    private String result;

    public AssignReferenceCell(String tokenUser, int docId, String cellId,
            String reference) {
    	
    	this.tokenUser = tokenUser;
    	this.docId = docId;
    	this.cellId = cellId;
    	this.reference = reference;
    	
	// add code here
    }

    @Override
    protected void dispatch() throws BubbleDocsException {
	// add code here
    }

    public final String getResult() {
        return result;
    }
}
