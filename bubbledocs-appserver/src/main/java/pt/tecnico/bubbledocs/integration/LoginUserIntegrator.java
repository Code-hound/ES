package pt.tecnico.bubbledocs.integration;

//import id.ws;
import pt.tecnico.bubbledocs.domain.Session;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.InvalidUserException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

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

public class LoginUserIntegrator extends BubbleDocsIntegrator {
	
	private String username;
	private String password;
	private String userToken;
	
	public LoginUserIntegrator (String username, String password) {
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
	
	public boolean checkPassword (User user, String password) {
		return (user.getPassword().equals(password));
	}
	
	@Override
	protected void dispatch() throws BubbleDocsException {
		BubbleDocs bd = getBubbleDocs();
		for (Session s : getBubbleDocs().getSessionsSet()) {
			if(s.getLastAccess().plusHours(2).isBeforeNow()) {
				getBubbleDocs().removeUserFromSession(s.getUser());
				getBubbleDocs().removeSessions(s);
			}
		}
		
		User user = getUser(this.username);
		if (user == null)
			throw new InvalidUserException(this.username);
		if (checkPassword (user, this.password)) {
			this.userToken = getBubbleDocs().addUserToSession(user);
		}
		
		IDRemoteServices integration = new IDRemoteServices();
		try {
			integration.loginUser(this.username, this.password);
		} catch (RemoteInvocationException e) {
			if (getUser(this.username) == null || !getUser(username).getPassword().equals(this.password))
				throw new UnavailableServiceException();
		}
	}
}
