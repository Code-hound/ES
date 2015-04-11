package pt.tecnico.bubbledocs.exception;

public class ExportDocumentException extends BubbleDocsException {

	/*
	 * EXPORT EXCEPTION
	 * 
	 * @author: Luis Ribeiro Gomes
	 */

	private static final long serialVersionUID = 1L;

	public ExportDocumentException() {
		super("Error in exporting document to XML");
	}
}