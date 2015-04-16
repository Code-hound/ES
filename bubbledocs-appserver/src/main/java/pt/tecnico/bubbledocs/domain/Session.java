package pt.tecnico.bubbledocs.domain;

import pt.tecnico.bubbledocs.domain.User;

import org.joda.time.*;

public class Session extends Session_Base {

	public static final int SESSION_DURATION_TIME = 120; // max_Session=2h

	public Session(User user) {
		super();
		DateTime lastAccess = new DateTime();
		setLastAccess(lastAccess);
		setUser(user);
	}
	
	public void resetLastAccess() {
		DateTime lastAccess = new DateTime();
		setLastAccess(lastAccess);
	}
}
