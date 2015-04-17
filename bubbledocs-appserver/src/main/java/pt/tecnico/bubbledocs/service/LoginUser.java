package pt.tecnico.bubbledocs.service;

//import id.ws;
import pt.tecnico.bubbledocs.domain.Session;
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
	
	@Override
	protected void dispatch() throws BubbleDocsException {

		IDRemoteServices service = new IDRemoteServices();
		
		for (Session s : getBubbleDocs().getSessionsSet())
			if(s.getLastAccess().plusHours(2).isBeforeNow()) {
				getBubbleDocs().removeUserFromSession(s.getUser());
				getBubbleDocs().removeSessions(s);
			}

		//throws UnavailableServiceException
		try {
			service.loginUser(this.username, this.password);
		} catch (RemoteInvocationException e) {
			if (getUser(this.username) == null || !getUser(username).getPassword().equals(this.password))
				throw new UnavailableServiceException();
		}

		if (getUser(this.username) != null && !getUser(username).getPassword().equals(this.password))
			getUser(username).setPassword(this.password);
		
		this.userToken = getBubbleDocs().addUserToSession(getUser(username));
	}
}
