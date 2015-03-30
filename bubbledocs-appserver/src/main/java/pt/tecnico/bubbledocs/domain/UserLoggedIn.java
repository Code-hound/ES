package pt.tecnico.bubbledocs.domain;

public class UserLoggedIn extends UserLoggedIn_Base {
    
    public UserLoggedIn() {
        super();
    }
    
    public User getUserLoggedInByToken(String userToken){
    	return this.getUser();
    }
    
}
