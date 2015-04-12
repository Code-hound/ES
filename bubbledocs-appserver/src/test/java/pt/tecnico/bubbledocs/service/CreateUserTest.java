package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.Session;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.exception.UnknownBubbleDocsUserException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistsException;
import pt.tecnico.bubbledocs.exception.EmptyUsernameException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class CreateUserTest extends BubbleDocsServiceTest {
	
	/*
	 * Falta fazer o Teste de mail vazio.
	 * 
	 * Como e que trato a excepcao de pass vazia?
	 * 
	 * @author: Francisco Maria Calisto
	 * 
	 */

	// the tokens
	private String root;
	private String ars;


	//private static final String ROOT_USERNAME = "root";
	private static final String USERNAME = "ars";
	private static final String PASSWORD = "ars";
	private static final String USERNAME_DOES_NOT_EXIST = "no-one";

	@Override
	public void populate4Test() {
		createUser(USERNAME, PASSWORD, "António Rito Silva", "email@email.email");
        root = addUserToSession("root");
        ars = addUserToSession("ars");
	}

	@Test
	public void success() {
		CreateUser service = new CreateUser(root, USERNAME_DOES_NOT_EXIST, "José Ferreira", "email@email.email");
		service.execute();
		
		User user = getUserFromUsername(USERNAME_DOES_NOT_EXIST);
		assertEquals(USERNAME_DOES_NOT_EXIST, user.getUsername());
		assertEquals("jose", user.getPassword());
		assertEquals("José Ferreira", user.getName());
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void usernameExists() {
		CreateUser service = new CreateUser(root, USERNAME, "José Ferreira", "email@email.email");
		service.execute();
	}

	@Test(expected = EmptyUsernameException.class)
	public void emptyUsername() {
		CreateUser service = new CreateUser(root, "", "jose", "José Ferreira");
		service.execute();
	}

	// TODO

	@Test(expected = UnauthorizedOperationException.class)
	public void unauthorizedUserCreation() {
		CreateUser service = new CreateUser(ars, USERNAME_DOES_NOT_EXIST,
				"jose", "José Ferreira");
		service.execute();
	}

	@Test(expected = UserNotInSessionException.class)
	public void accessUsernameDoesNotExist() {
		removeUserFromSession(root);
		
		CreateUser service = new CreateUser(root, USERNAME_DOES_NOT_EXIST,
				"jose", "José Ferreira");
		service.execute();
	}
}
