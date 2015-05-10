package pt.tecnico.bubbledocs.service;

//import id.ws;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.Session;
import pt.tecnico.bubbledocs.domain.BubbleDocs;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.InvalidUserException;
import pt.tecnico.bubbledocs.exception.WrongPasswordException;

/*
 * LOG IN USER
 * 
 * Recebe o nome de utilizador e a password
 * Cria uma nova sessao para o utilizador caso a autenticacao
 * esteja correcta
 * 
 * @author: Francisco Silveira
 * @author: Aline Caliente
 * 
 */

public class LoginUserService extends BubbleDocsService {
	
	private String username;
	private String password;
	private String userToken;
	
	public LoginUserService (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUserToken() {
		return this.userToken;
	}

	private BubbleDocs removeIdleSessions () {

		BubbleDocs bd = getBubbleDocs();
		
		for ( Session s : bd.getSessionsSet() ) {
			if( s.getLastAccess().plusHours(2).isBeforeNow() ) {
				bd.removeUserFromSession(s.getUser());
				bd.removeSessions(s);
			}
		}

		return bd;

	}

	private boolean userIsNotValid (User user) {
		return (user == null);
	}
	
	private boolean passwordIsNotValid (User user, String password) {
		return !(user.getPassword().equals(password));
	}

	public void revert() {

		BubbleDocs bd = getBubbleDocs();

		bd.removeSessions(bd.getSessionByToken(this.userToken));

	}

	@Override
	protected void dispatch() throws BubbleDocsException {

		BubbleDocs bd   = removeIdleSessions();
		User       user = getUser(this.username);

		//throws InvalidUserException
		if ( userIsNotValid(user) ) {
			throw new InvalidUserException(this.username);
		}

		//throws WrongPasswordException
		if ( passwordIsNotValid(user, this.password) ) {
			throw new WrongPasswordException(this.username);
		}

		this.userToken = bd.addUserToSession(user);

	}
}
