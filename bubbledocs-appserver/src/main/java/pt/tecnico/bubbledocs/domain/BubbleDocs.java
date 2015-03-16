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
    	if(bubble==null)
    		bubble=new BubbleDocs();
    	
    	return bubble;
    }
    
    public boolean hasUsers(){
    	return !getUsersSet().isEmpty();
    }
    
    public User getUserByUserName(String username){
    	if(hasUsers()){
	    	for(User u : getUsersSet()){
	    		if(u.get_username().equals("username"))
	    			return u;
	    	}
    	}
    	return null;
    }
    
    public boolean hasUserByUserName(String UserName){
    	return getUserByUserName(UserName) != null;
    }

    public boolean hasSpreadSheet(){
    	return !getDocsSet().isEmpty();
    }

    public void addUser(User currentUser, String newUserName, String newName, String newPassword){
    	//try{
    	User newUser = currentUser.createUser(newUserName, newName, newPassword);
    	addUsers(newUser);
    	//catch(InvalidAccessException){}
    }

    public void removeUser(User currentUser, String userNameToRemove){
    	String currentUserType=currentUser.get_username();
    	if(hasUserByUserName(userNameToRemove)&&currentUserType.equals("root")){
    		removeUsers(getUserByUserName(userNameToRemove));
    	}
    	
    }

    public List<SpreadSheet> getSpreadSheetByName(String sheetName){
    	List<SpreadSheet> list=new ArrayList<SpreadSheet>();
    	for(SpreadSheet s: getDocsSet()){
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
    	SpreadSheet newSpreadSheet = new SpreadSheet(user, get_entityId(), sheetName, new LocalDate(), rows, columns);
    	User root=getUserByUserName("root");
    	newSpreadSheet.addDocAccess(new Access(root,0));
    	newSpreadSheet.addDocAccess(new Access(user, 1));
    	
    	set_entityId(get_entityId()+1); //Unique and sequential ID
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
    
    public void rmvUserSheet(String username, String sheetName) {
        for (SpreadSheet s : getSpreadSheetByName(sheetName)) {
            for (User u : s.getAccessUsers()) {
            	if (u.get_username().equals(username)) {
            		removeDocs(s);
            	}
            }
        }
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
