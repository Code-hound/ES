package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.User;

import pt.tecnico.bubbledocs.exception.EmptyEmailException;
import pt.tecnico.bubbledocs.exception.InvalidUsernameException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistsException;

public class CreateUserServiceTest extends BubbleDocsServiceTest {
	
	/*
	 * @author: Francisco Maria Calisto
	 * @author: Luís Ribeiro Gomes
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

		createUser(USERNAME, PASSWORD, "email@email.email", "António Rito Silva");
        root = addUserToSession("root");
        ars = addUserToSession("ars");

	}

	@Test
	public void success() {

		CreateUserService service = new CreateUserService (root, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		service.execute();
		
		User user = getUserFromUsername(USERNAME_DOES_NOT_EXIST);
		assertEquals(USERNAME_DOES_NOT_EXIST, user.getUsername());
		assertEquals("email@email.email", user.getEmail());
		assertEquals("José Ferreira", user.getName());

	}

	@Test(expected = EmptyEmailException.class)
	public void InvalidEmail() {

		CreateUserService service = new CreateUserService(root, USERNAME_DOES_NOT_EXIST, "", "José Ferreira");
		service.execute();

	}
	
	@Test(expected = InvalidUsernameException.class)
	public void InvalidShortUsername() {

		CreateUserService service = new CreateUserService(root, "", "email@email.email", "José Ferreira");
		service.execute();

	}
	
	@Test(expected = InvalidUsernameException.class)
	public void InvalidLongUsername() {

		CreateUserService service = new CreateUserService(root, "lookathowlongthisusernameis", "email@email.email", "José Ferreira");
		service.execute();

	}
	
	@Test(expected = UnauthorizedOperationException.class)
	public void unauthorizedUserCreation() {

		CreateUserService service = new CreateUserService(ars, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		service.execute();

	}

	@Test(expected = UserNotInSessionException.class)
	public void InvalidUser() {

		removeUserFromSession(root);

		CreateUserService service = new CreateUserService(root, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		service.execute();

	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void InvalidCreate() {

		CreateUserService service = new CreateUserService (root, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		service.execute();
		service.execute();

	}

}
