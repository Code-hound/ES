///dsf
package pt.tecnico.bubbledocs.domain;

import org.joda.time.ReadablePartial;

import pt.tecnico.bubbledocs.exception.InvalidUsernameException;

public class User extends User_Base {

	public User(String username, String password, String name, String email) {
		super();
		setUsername(username);
		setPassword(password);
		setName(name);
		setEmail(email);
	}

	/*
	 * private SpreadSheet getDocumentById(int id){ for(SpreadSheet document :
	 * getDocsSet()){ if(document.getId().equals(id)){ return document; }
	 * 
	 * } return null; }
	 * 
	 * public boolean hasDocument(int documentId){ return
	 * getDocumentById(documentId) != null; }
	 * 
	 * public void addDocument(User userName, SpreadSheet spreadSheetToBeAdded)
	 * { //will throw exception if it already exists
	 * 
	 * 
	 * //if(hasDocument(spreadSheetToBeAdded.getId())) // throw new
	 * IdAlreadyExistsException(spreadSheetToBeAdded.getId());
	 * 
	 * 
	 * if (userName.viewSheets() != 0) //supondo que caso nao haja sheets do
	 * utilizador responde 0 { if (super.viewSheetfromUser(userName,
	 * spreadSheetToBeAdded) != 0) super.addDocs(spreadSheetToBeAdded); }
	 * 
	 * // nao foi possivel adicionar sheet, existe uma com nome igual }
	 * 
	 * 
	 * public void removeDocument(int spreadSheetId) { //will throw exception if
	 * it already exists /*SpreadSheet toRemove =
	 * getDocumentById(spreadSheetId);
	 * 
	 * if(toRemove == null) throw new SpreadSheetDoesNotExist(spreadSheetId);
	 */
	/*
	 * removeDocument(spreadSheetId); }
	 * 
	 * public List<SpreadSheet> getDocumentsByName(String spreadSheetName){
	 * List<SpreadSheet> docList = new ArrayList<SpreadSheet>(); for(SpreadSheet
	 * s :getDocsSet()){
	 * if(s.getSpreadSheetName().equalsIgnoreCase(spreadSheetName))
	 * docList.add(s); } return docList; }
	 */

	public static User createUser(String username, String password, 
			String name, String email) {
		if (username.length() < 3 || username.length() > 8)
			throw new InvalidUsernameException();
		return new User(username, password, name, email);
	}

	public String toString() {
		return Printer.use(this);
	}

	public ReadablePartial getLastAccess() {
		// TODO Auto-generated method stub
		return null;
	}
}
