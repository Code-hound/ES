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
 * @author: Francisco Silveira
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
	protected void dispatch() {
		BubbleDocs bd = getBubbleDocs();
		
		try {
			User user = getUser(username);
			if (user != null && user.getPassword().equals(password)) {
				this.userToken = bd.addUserToSession(user);
			}
		}
		catch (UserAlreadyInSessionException | WrongPasswordException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
