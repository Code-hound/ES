package pt.tecnico.bubbledocs.exception;

public class UserAlreadyHasThisDocumentException extends BubbleDocsException {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String docName;
	
	public UserAlreadyHasThisDocumentException (String userName, String documentName) {
		this.docName = documentName;
		this.username = userName;
	}
	
	public String getUsername () {
		return this.username;
	}
	
	public String getDocumentName () {
		return this.docName;
	}

	
	@Override
	public String getMessage () {
		return "UserAlreadyHasThisDocumentException: User \"" + username + 
				"\" already has a document called \"" + docName + "\".";
	}
}
