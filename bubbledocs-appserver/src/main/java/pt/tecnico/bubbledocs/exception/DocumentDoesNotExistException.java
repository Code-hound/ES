package pt.tecnico.bubbledocs.exception;

import org.joda.time.LocalDate;

public class DocumentDoesNotExistException extends BubbleDocsException {
	/*
	 * USER ALREADY EXIST EXCEPTION
	 * 
	 * Excepcao da varificacao se user ja existe.
	 * 
	 * @author: Francisco Silveira
	 */
	
	private int documentId = 0;
	private String documentName;
	private LocalDate creationDate;
	private String owner = "";
	
	public DocumentDoesNotExistException (int docId) {
		this.documentId = docId;
	}
	
	public DocumentDoesNotExistException (String documentName, LocalDate creationDate) {
		this.creationDate = creationDate;
		this.documentName = documentName;
	}
	
	public DocumentDoesNotExistException (String owner, String documentName) {
		this.documentName = documentName;
		this.owner = owner;
	}
	
	public int getId () {
		return this.documentId;
	}
	
	public String getDocumentName () {
		return this.documentName;
	}
	
	public LocalDate getCreationDate () {
		return this.creationDate;
	}
	
	@Override
	public String getMessage () {
		if (documentId != 0)
			return "DocumentDoesNotExistException: The document with ID \"" +
				documentId + "\" does not exist.";
		else if (owner.equals(""))
			return "DocumentDoesNotExistException: The document with name \"" +
				documentName + "\" and creation date " + creationDate.toString() +
				" does not exist.";
		else
			return "DocumentDoesNotExistException: The document with name \"" +
			documentName + "\" and owner username \"" + owner +
			"\" does not exist.";
	}
}
