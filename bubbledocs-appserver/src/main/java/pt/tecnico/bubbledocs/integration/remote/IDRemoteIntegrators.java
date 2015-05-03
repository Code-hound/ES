package pt.tecnico.bubbledocs.integration.remote;

import pt.tecnico.bubbledocs.exception.DuplicateEmailException;
import pt.tecnico.bubbledocs.exception.DuplicateUsernameException;
import pt.tecnico.bubbledocs.exception.InvalidEmailException;
import pt.tecnico.bubbledocs.exception.InvalidUserException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;

public class IDRemoteIntegrators {
	
	/*
	 * ID REMOTE INTEGRATOR
	 * 
	 * Servico remoto que abstrai o servico externo do SD-ID.
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */
	
	public void createUser(String username, String email)
		throws InvalidUserException, DuplicateUsernameException, DuplicateEmailException, InvalidEmailException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote integration
			
		}
	
	public void loginUser(String username, String password)
		throws LoginBubbleDocsException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote integration
			
		}
	
	public void removeUser(String username)
		throws LoginBubbleDocsException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote integration
			
		}
	
	public void renewPassword(String username)
		throws LoginBubbleDocsException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote integration
			
		}
	
}
