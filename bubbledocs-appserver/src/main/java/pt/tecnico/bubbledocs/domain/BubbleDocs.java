package pt.tecnico.bubbledocs.domain;

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
    
    public User getUserByUserName(String username){
    	for(User u : getUsersSet()){
    		if(u.get_username().equals("username"))
    			return u;
    	}
    	return null;
    }
    
    public boolean hasUser(String UserName){
    	return getUserByUserName(UserName) != null;
    }

    public void addUser(User currentUser, String newUserName, String newName, String newPassword){
    	//try{
    	User newUser = currentUser.createUser(newUserName, newName, newPassword);
    	addUsers(newUser);
    	//catch(InvalidAccessException){}
    }

    public void removeUser(User currentUser, String userNameToRemove){
    	String currentUserType=currentUser.get_username();
    	if(hasUser(userNameToRemove)&&currentUserType.equals("root")){
    		removeUsers(getUserByUserName(userNameToRemove));
    	}
    	
    }
}
