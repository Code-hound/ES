package pt.tecnico.bubbledocs.exception;

public class ImportDocumentException extends BubbleDocsException {

	/*
	 * IMPORT EXCEPTION
	 * 
	 * @author: Luis Ribeiro Gomes
	 */

	private static final long serialVersionUID = 1L;

	public ImportDocumentException() {
		super("Error in importing document from XML");
	}
}