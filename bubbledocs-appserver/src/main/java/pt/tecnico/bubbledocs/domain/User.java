///dsf
package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;

public class User extends User_Base {

	public User (Element element) {
		super();
		importFromXML(element);
	}

	public User (String userName, String name, String password) {
        super();
        set_username(userName);
        set_name(name);
        set_password(password);
    }
    /*
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
    
    public void addDocument(User userName, SpreadSheet spreadSheetToBeAdded) { //will throw exception if it already exists
    	
    	
    	//if(hasDocument(spreadSheetToBeAdded.get_id()))
    	//	throw new IdAlreadyExistsException(spreadSheetToBeAdded.getId());
    	
    
    	if (userName.viewSheets() != 0) //supondo que caso nao haja sheets do utilizador responde 0
    	{
    		if (super.viewSheetfromUser(userName, spreadSheetToBeAdded) != 0)
    			super.addDocs(spreadSheetToBeAdded);
    	}
    	
    	// nao foi possivel adicionar sheet, existe uma com nome igual
    }
    
    
    public void removeDocument(int spreadSheetId) { //will throw exception if it already exists
    	/*SpreadSheet toRemove = getDocumentById(spreadSheetId);
    	*
    	*if(toRemove == null)
    	*  throw new SpreadSheetDoesNotExist(spreadSheetId);
    	*/
	/*
    	removeDocument(spreadSheetId);
    }
    
    public List<SpreadSheet> getDocumentsByName(String spreadSheetName){
    	List<SpreadSheet> docList = new ArrayList<SpreadSheet>();
    	for(SpreadSheet s :getDocsSet()){
    		if(s.get_spreadSheetName().equalsIgnoreCase(spreadSheetName))
    			docList.add(s);
    	}
    	return docList;
    }
    */
    
    public User createUser(String userName,String name, String password){
    	//if(!get_username().equals("root"))
    		//throws InvalidAccessException
    	return new User(userName, name, password);	
    }
    
    public void removeUser(User user) {
    	if (this.get_username().equals("root")) {
    		getBubbleDocs().removeUser(this, user);
    	}
    }
    
    public void removeSpreadsheet(SpreadSheet sheet) {
    	if (this.get_username().equals("root")) {
    		getBubbleDocs().removeSpreadSheetById(Integer.toString(sheet.get_id()));
    	}
    }

	public void    importFromXML    (Element element) { Importer.visit (this, element) ; }
	public Element exportToXML      ()                { return Exporter.visit (this)   ; }
    public String  toString         ()                { return Printer.visit  (this)   ; }
}
