package pt.tecnico.bubbledocs.exception;

public class CannotLoadDocumentException extends BubbleDocsException {

	/*
	 * CANNOT LOAD DOCUMENT EXCEPTION
	 * 
	 * Excepcao de obtenção do documento.
	 * 
	 * @author: Luis Ribeiro Gomes
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String docName;

	public CannotLoadDocumentException(String docName) {
		this.docName = docName;
	}

	public String getDocName() {
		return this.docName;
	}

	@Override
	public String getMessage() {
		return "Cannot load " + this.docName;
	}

}
