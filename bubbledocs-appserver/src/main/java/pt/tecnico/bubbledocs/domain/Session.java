package pt.tecnico.bubbledocs.domain;
import pt.tecnico.bubbledocs.exception.*;
import org.joda.time.*;

public class Session extends Session_Base {
    
	public static final int SESSION_DURATION_TIME = 120; //max_Session=2h
	
    public Session() {
        super();
    }
    
    //public User getUserLoggedInByToken(String userToken) {
        /*FIX ME*/
    //}
    
    /*public User checkUserLoggedIn(String userToken) 
    		throws UserNotLoggedInException { //add LastAccess @ Session
    	User uli = getUserLoggedInByToken(userToken);
    	
    	if(uli == null)
    		throw new UserNotLoggedInException(userToken);
    	
    	LocalTime currentAccess = new LocalTime();
    	
    	int difference = Minutes.minutesBetween(uli.getLastAccess(),currentAccess).getMinutes();
    	if(difference > SESSION_DURATION_TIME){
    		uli.delete();
    		throw new UserNotLoggedInException(uli.getUserToken());
    	} else
    		uli.setLastAccess(currentAccess);
    	return uli.getUser();
    }
    
    public void logout(String userToken){
    	User userLoggedIn = getUserLoggedInSet().stream().filter(uli.getUserToken().equals(userToken)).findFirst().orElse(null);
	    	
	    if(userLoggedIn != null)
	    	userLoggedIn.delete();
    }*/
    
}
