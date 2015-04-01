package pt.tecnico.bubbledocs.domain;

//import pt.tecnico.bubbledocs.exception.*;

import org.joda.time.*;

public class Session extends Session_Base {

	public static final int SESSION_DURATION_TIME = 120; // max_Session=2h

	public Session(String username) {
		super();
		DateTime lastAccess = new DateTime();
		setLastAccess(lastAccess);
		setUsername(username);
	}
	/*
	 * public User checkUserLoggedIn(String userToken) throws
	 * UserNotLoggedInException { User uli = getUserLoggedInByToken(userToken);
	 * 
	 * if(userToken == null) throw new UserNotLoggedInException(userToken);
	 * 
	 * LocalTime currentAccess = new LocalTime();
	 * 
	 * int difference =
	 * Minutes.minutesBetween(uli.getLastAccess(),currentAccess).getMinutes();
	 * if(difference > SESSION_DURATION_TIME){ uli.setUserToken(null); throw new
	 * UserNotLoggedInException(uli.getUserToken()); } else
	 * setLastAccess(currentAccess); return uli; }
	 * 
	 * private User getUserLoggedInByToken(String userToken) { if
	 * (getUser().getUserToken() != null) return this.getUser(); throw new
	 * UserNotLoggedInException(userToken); }
	 * 
	 * // ADD LOGIN SERVICE ("CREATE SESSION")
	 * 
	 * public void logout(String userToken){ User userLoggedIn =
	 * getUserLoggedInByToken(userToken);
	 * 
	 * if(userLoggedIn != null) userLoggedIn.setUserToken(null); //TODO: delete
	 * Session }
	 */
}
