package pt.tecnico.bubbledocs.service.remote;

import pt.tecnico.bubbledocs.exception.DuplicateEmailException;
import pt.tecnico.bubbledocs.exception.DuplicateUsernameException;
import pt.tecnico.bubbledocs.exception.InvalidEmailException;
import pt.tecnico.bubbledocs.exception.InvalidUserException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.ulisboa.tecnico.sdis.id.cli.IdClient;
import pt.ulisboa.tecnico.sdis.id.ws.UserDoesNotExist_Exception;

public class IDRemoteServices {
	
	private IdClient client;
	
	public String createUser(String username, String email)
		throws Exception {
			client = new IdClient("http://localhost:8081", "SD-ID");
			String password = client.createUser(username, email);
			return password;
		}
	
	public void loginUser(String username, String password)
		throws Exception {
			client.requestAuthentication(username, password);
		}
	
	public void removeUser(String username)
		throws Exception {
			client.removeUser(username);
		}
	
	public void renewPassword(String username)
		throws Exception {
			client.renewPassword(username);
		}
	
}