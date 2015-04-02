package pt.tecnico.bubbledocs.exception;

/*
 * USER CAN'T WRITE EXCEPTION
 * 
 * Ficheiro "copiado" do exemplo phonebook
 * 
 * @author: Francisco Silveira
 */

public class UserCantWriteException extends BubbleDocsException {
	
	private String username;
	private int docId;
	
	public UserCantWriteException(String username, int docId) {
		this.username = username;
		this.docId = docId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public int getDocId() {
		return this.docId;
	}
	
	@Override
	public String getMessage() {
		return "UserCantWriteException: User \"" + this.username + "\" doesn't have writing permission on Document \"" + this.docId + "\".";
	}
}
