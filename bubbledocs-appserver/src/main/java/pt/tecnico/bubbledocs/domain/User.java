///dsf
package pt.tecnico.bubbledocs.domain;

import org.jdom2.Element;
import org.joda.time.ReadablePartial;

import pt.tecnico.bubbledocs.exception.ImportException;
import pt.tecnico.bubbledocs.exception.ExportException;

public class User extends User_Base {

	public User (Element element) {
		super();
		importFromXML(element);
	}

	public User (String userName, String name, String password) {
        super();
        setUsername(userName);
        setName(name);
        setPassword(password);
    }
    /*
    private SpreadSheet getDocumentById(int id){
    	for(SpreadSheet document : getDocsSet()){
    		if(document.getId().equals(id)){
    			return document;
    		}
    		
    	}
    	return null;
    }
    
    public boolean hasDocument(int documentId){
    	return getDocumentById(documentId) != null;
    }	 
    
    public void addDocument(User userName, SpreadSheet spreadSheetToBeAdded) { //will throw exception if it already exists
    	
    	
    	//if(hasDocument(spreadSheetToBeAdded.getId()))
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
    		if(s.getSpreadSheetName().equalsIgnoreCase(spreadSheetName))
    			docList.add(s);
    	}
    	return docList;
    }
    */
    
    public User createUser(String userName,String name, String password){
    	//if(!getUsername().equals("root"))
    		//throws InvalidAccessException
    	return new User(userName, name, password);	
    }
    
    public void removeUser(User user) {
    	if (this.getUsername().equals("root")) {
    		getBubbleDocs().removeUser(this, user);
    	}
    }
   
    public void removeSpreadsheet(SpreadSheet sheet) {
    	if (this.getUsername().equals("root")) {
    		getBubbleDocs().removeSpreadSheetById(sheet.getId());
    	}
    }

	public void    importFromXML (Element element) throws ImportException { Importer.use (this, element) ; }
	public Element exportToXML   ()                throws ExportException { return Exporter.use (this)   ; }
    public String  toString         ()                { return Printer.use  (this)   ; }

	public ReadablePartial getLastAccess() {
		// TODO Auto-generated method stub
		return null;
	}
}
