package pt.tecnico.bubbledocs.service;

//import id.ws;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.Session;
import pt.tecnico.bubbledocs.exception.*;

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

public class LoginUser extends BubbleDocsService {
	
	private String username;
	private String password;
	private String userToken;
	
	public LoginUser (String username, String password) {
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
	
	protected void removeIdleUsers() {
		BubbleDocs bd = getBubbleDocs();
		for (Session s : bd.getSessionsSet()) {
			if(s.getLastAccess().plusHours(2).isBeforeNow()) {
				User userToRemove = s.getUser();
				bd.removeUserFromSession(userToRemove);
				bd.removeSessions(s);
			}
		}
	}
	
	@Override
	protected void dispatch() {
		removeIdleUsers();
		BubbleDocs bd = getBubbleDocs();
		
		//SDId service = new SDId();
		//SdId port = service.getIdImplPort();
		
		
		User user = getUser(username);
		if (user==null)
			throw new UnknownBubbleDocsUserException(username);
		bd.verifyUser(user, this.password);
		
		this.userToken = bd.addUserToSession(user);
		
		
		
	}
}
