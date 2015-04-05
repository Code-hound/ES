package pt.tecnico.bubbledocs.service;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.UserAlreadyInSessionException;
import pt.tecnico.bubbledocs.exception.WrongPasswordException;

/*
 * LOG IN USER
 * 
 * Recebe o nome de utilizador e a password
 * Cria uma nova sessao para o utilizador caso a autenticacao
 * esteja correcta
 * 
 * @author: Francisco Maria Calisto
 * 
 */

public class LoginUserService extends BubbleDocsService {
	
	private String username;
	private String password;
	
	public LoginUserService (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	protected void dispatch() {
		BubbleDocs bd = getBubbleDocs();
		
		try {
			User user = getUser(username);
			if (user != null) {
				bd.addUserToSession(user, password);
			}
		}
		catch (UserAlreadyInSessionException | WrongPasswordException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
