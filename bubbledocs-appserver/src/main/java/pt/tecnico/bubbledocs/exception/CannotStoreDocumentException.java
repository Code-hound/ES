package pt.tecnico.bubbledocs.exception;

public class CannotStoreDocumentException extends BubbleDocsException {

	/*
	 * CANNOT STORE DOCUMENT EXCEPTION
	 * 
	 * Excepcao de armazenamento do documento.
	 * 
	 * @author: Luis Ribeiro Gomes
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String docName;

	public CannotStoreDocumentException(String docName) {
		this.docName = docName;
	}

	public String getDocName() {
		return this.docName;
	}

	@Override
	public String getMessage() {
		return "Cannot store " + this.docName;
	}

}
