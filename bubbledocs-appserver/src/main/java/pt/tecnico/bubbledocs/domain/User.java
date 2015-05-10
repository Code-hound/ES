///dsf
package pt.tecnico.bubbledocs.domain;

import org.joda.time.ReadablePartial;

import pt.tecnico.bubbledocs.exception.InvalidUsernameException;

public class User extends User_Base {

	public User(String username, String password, String name, String email) {
		super();
		setUsername(username);
		setPassword(password);
		setName(name);
		setEmail(email);
	}

	public static User createUser(String username, String password, 
			String name, String email) {
		if (username.length() < 3 || username.length() > 8)
			throw new InvalidUsernameException();
		return new User(username, password, name, email);
	}

	public String toString() {
		return Printer.use(this);
	}

	public ReadablePartial getLastAccess() {
		// TODO Auto-generated method stub
		return null;
	}
}
