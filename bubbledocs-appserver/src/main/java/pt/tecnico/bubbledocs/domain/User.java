package pt.tecnico.bubbledocs.domain;

public class User extends User_Base {
    
	public User(String userName,String name, String password) {
        super();
        set_username(userName);
        set_name(name);
        set_password(password);
    }
    
    private SpreadSheet getDocumentById(int id){
    	for(SpreadSheet document : getDocsSet()){
    		if(document.get_id().equals(id)){
    			return document;
    		}
    		
    	}
    	return null;
    }
    
    public boolean hasDocument(int documentId){
    	return getDocumentById(documentId) != null;
    }	 
    
    public void addDocument(SpreadSheet spreadSheetToBeAdded) { //will throw exception if it already exists
    	/*if(hasDocument(spreadSheetToBeAdded.get_id()))
    		*throw new IdAlreadyExistsException(spreadSheetToBeAdded.getId());
    		*/
    	super.addDocs(spreadSheetToBeAdded);
    }
    
    public void removeDocument(int spreadSheetId) { //will throw exception if it already exists
    	SpreadSheet toRemove = getDocumentById(spreadSheetId);
    	
    	/*if(toRemove == null)
    	 * throw new SpreadSheetDoesNotExist(spreadSheetId);
    	 */
    	removeDocument(spreadSheetId);
    }
    
    
    
}
