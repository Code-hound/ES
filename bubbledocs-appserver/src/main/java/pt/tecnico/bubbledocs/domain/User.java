package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;

public class User extends User_Base {
    
	public User(String userName,String name, String password) {
        super();
        set_username(userName);
        set_name(name);
        set_password(password);
    }
    
    private SpreadSheet getDocumentById(int id){
    	for(SpreadSheet document : getDocsSet()){
    		if(document.get_id()==id){
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
    	/*SpreadSheet toRemove = getDocumentById(spreadSheetId);
    	*
    	*if(toRemove == null)
    	*  throw new SpreadSheetDoesNotExist(spreadSheetId);
    	*/
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
    
    public User createUser(String userName,String name, String password){
    	//if(!get_username().equals("root"))
    		//throws InvalidAccessException
    	return new User(userName, name, password);	
    }

    public String toString(){
    	return "UserName: "+get_username() +" Name: "+get_name()+" Password: "+ get_password();
    }

    public List<SpreadSheet> getSpreadsSheetByName(String Name){
    	List<SpreadSheet> spreadSheetList = new ArrayList<SpreadSheet>();
    	for(SpreadSheet s : getDocsSet()){
    		if(s.get_spreadSheetName().equals(Name))
    			spreadSheetList.add(s);
    	}
    	return spreadSheetList;
    }
    
    public SpreadSheet getSpreadSheetByNameAndDate(String Name, LocalDate docDate){
    	List<SpreadSheet> sameNameDocs = getSpreadsSheetByName(Name);
    	
    	for(SpreadSheet s : sameNameDocs){
    		LocalDate spreadSheetDate=s.get_date();
    		if(spreadSheetDate.equals(docDate))
    			return s;
    	}
    	return null;
    }
    
    
}
