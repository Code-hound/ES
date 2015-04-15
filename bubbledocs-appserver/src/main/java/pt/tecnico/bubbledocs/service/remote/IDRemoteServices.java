package pt.tecnico.bubbledocs.service.remote;

import pt.tecnico.bubbledocs.exception.DuplicateEmailException;
import pt.tecnico.bubbledocs.exception.DuplicateUsernameException;
import pt.tecnico.bubbledocs.exception.InvalidEmailException;
import pt.tecnico.bubbledocs.exception.InvalidUserException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;

public class IDRemoteServices {
	
	/*
	 * ID REMOTE SERVICES
	 * 
	 * Servico remoto que abstrai o servico externo do SD-ID.
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */
	
	public void createUser(String username, String email)
		throws InvalidUserException, DuplicateUsernameException, DuplicateEmailException, InvalidEmailException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote service
			
		}
	
	public void loginUser(String username, String password)
		throws LoginBubbleDocsException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote service
			
		}
	
	public void removeUser(String username)
		throws LoginBubbleDocsException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote service
			
		}
	
	public void renewPassword(String username)
		throws LoginBubbleDocsException, RemoteInvocationException {
			
			// TODO: the connection and invocation of the remote service
			
		}
	
}