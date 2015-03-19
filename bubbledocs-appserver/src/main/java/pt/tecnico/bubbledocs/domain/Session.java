package pt.tecnico.bubbledocs.domain;

public class Session extends Session_Base {
    
	public static final int SESSION_DURATION_TIME = 120; //max_Session=2h
	
    public Session() {
        super();
    }
    
    public User checkUserLoggedIn(String userToken) throws UserNotLoggedInException{ //add LastAccess @ Session
    	UserLoggedIn uli = getUserLoggedInByToken(userToken);
    	
    	if(uli == null)
    		throw new UserNotLoggedInException(userToker);
    	
    	LocalTime currentAccess = new LocalTime();
    	
    	int difference = Minutes.minutesBetween(uli.getLastAccess(),currentAccess).getMinutes();
    	if(difference > SESSION_DURATION_TIME){
    		uli.delete();
    		throw new UserNotLoggedInException(uli.getUser().getUsername());
    	} else
    		uli.setLastAccess(currentAccess);
    	return uli.getUser();
    }
    
    public void logout(String userToken){
    	UserLoggedIn userLoggedIn = getUserLoggedInSet().stream();
	    	.filter(uli.getUserToken().equals(userToken))
	    	.findFirst().orElse(null);
	    	
	    if(userLoggedIn != null)
	    	userLoggedIn.delete();
    }
    
}
