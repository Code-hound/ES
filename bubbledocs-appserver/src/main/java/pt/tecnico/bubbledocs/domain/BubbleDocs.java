package pt.tecnico.bubbledocs.domain;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.joda.time.LocalDate;

import pt.ist.fenixframework.FenixFramework;

public class BubbleDocs extends BubbleDocs_Base {
    
    private BubbleDocs() {
        super();
        addUsers(new User("root", "root", "rootroot"));
    }
    
    public static BubbleDocs getInstance(){
    	BubbleDocs bubble = FenixFramework.getDomainRoot().getBubbleDocs();
    	if(bubble==null) {
    		bubble=new BubbleDocs();
    		//bubble.
    	}
    	
    	return bubble;
    }
    
    public boolean hasUsers(){
    	return !getUsersSet().isEmpty();
    }
    
    public User getUserByUserName(String username){
    	if(hasUsers()){
	    	for(User u : getUsersSet()){
	    		if(u.get_username().equals(username))
	    			return u;
	    	}
    	}
    	return null;
    }
    
    public boolean hasUserByUserName(String UserName){
    	return getUserByUserName(UserName) != null;
    }

    /* Deprecated function
     * User initialization should be handled by the User class, not by the database adder
    public void addUser(User currentUser, String newUserName, String newName, String newPassword){
    	//try{
    	User newUser = currentUser.createUser(newUserName, newName, newPassword);
    	addUsers(newUser);
    	//catch(InvalidAccessException){}
    }
    */
    
    public void addUser(User user) {
    	//try
    	if (!hasUserByUserName(user.get_username())) {
    		addUsers(user);
    	}
    	//catch
    }
    
    public boolean hasSpreadSheet(){
    	return !getDocsSet().isEmpty();
    }

    public void removeUser(User currentUser, User userToRemove){
    	if(hasUserByUserName(userToRemove.get_username()) && currentUser.get_username().equals("root")){
    		removeUsers(userToRemove);
    	}
    	
    }
    
    public SpreadSheet getSpreadSheetById (String id) {
    	int idInteger = Integer.parseInt(id);
    	for (SpreadSheet s : getDocsSet()) {
    		if (s.get_id() == idInteger) {
    			return s;
    		}
    	}
    	return null;
    }

    public List<SpreadSheet> getSpreadSheetByName(String sheetName){
    	List<SpreadSheet> list=new ArrayList<SpreadSheet>();
    	for(SpreadSheet s : getDocsSet()){
    		String s_name=s.get_spreadSheetName();
    		if(s_name.equals(sheetName)){
    			list.add(s);
    		}
    	}
    	return list;
    }
    
    public SpreadSheet getSpreadSheetByNameAndDate(String sheetName,LocalDate date){
    	for(SpreadSheet s:getDocsSet()){
    		if(s.get_spreadSheetName().equals(sheetName)&&s.get_date().equals(date))
    			return s;
    	}
    	return null;
    }
    
    public void createSpreadSheet(User user,String sheetName,int rows,int columns){
    	SpreadSheet newSpreadSheet = new SpreadSheet(user, get_nextSpreadSheetId(), sheetName, rows, columns);
    	User root=getUserByUserName("root");
    	if (newSpreadSheet != null) {
    		newSpreadSheet.addDocAccess(new Access(root,0));
    		newSpreadSheet.addDocAccess(new Access(user, 1));
    		addDocs(newSpreadSheet);
    		
    		set_nextSpreadSheetId(get_nextSpreadSheetId() + 1);
    		//set_entityId(get_entityId()+1); //Unique and sequential ID
    	}
    }
    
    public void addSpreadSheet(SpreadSheet spreadsheet){
    	spreadsheet.set_id(get_nextSpreadSheetId());
    	User root=getUserByUserName("root");
    	spreadsheet.addDocAccess(new Access(root,0));
    	spreadsheet.addDocAccess(new Access(spreadsheet.getOwner(), 1));
    	addDocs(spreadsheet);
    	
    	set_nextSpreadSheetId(get_nextSpreadSheetId() + 1);
    	//set_entityId(get_entityId()+1); //Unique and sequential ID
    }

    public String listSpreadSheetNameAndId(){
    	String list="";
    	
    	if(hasSpreadSheet()){
	    	for(SpreadSheet s:getDocsSet()){
	    		if(!list.equals("")){
	    			list+="\n";
	    		}
	    		list+="Name: "+s.get_spreadSheetName()+" Id: "+s.get_id();
	    	}
	    	return list;
    	}
    	return null;
    }
    
    public void removeSpreadSheetByOwner(String owner, String sheetName) {
        for (SpreadSheet s : getSpreadSheetByName(sheetName)) {
            if (s.getOwner().get_username().equals(owner)) {
            	removeDocs(s);
            }
        }
    }
    
    public void removeSpreadSheetById(String id) {
    	removeDocs(getSpreadSheetById(id));
    }

    public String listUsers(){
    	if(hasUsers()){
    		String s="";
    		for(User u: getUsersSet()){
    			if(s.equals(""));
    			s+=u.toString();
    		}
    		return s;
    	}
    	return null;
    }

    public Element exportSpreadSheetToXML(String sheetName, LocalDate date){
    	//try{
    	return getSpreadSheetByNameAndDate(sheetName, date).exportToXML();
    	//}catch(Exception e){throw new NonExistingSpreadSheetException(name, date);}
    }

}
