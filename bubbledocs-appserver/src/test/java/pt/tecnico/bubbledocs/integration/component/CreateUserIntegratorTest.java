package pt.tecnico.bubbledocs.integration.component;

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
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.integration.CreateUserIntegrator;

public class CreateUserIntegratorTest extends BubbleDocsIntegratorTest {
	
	/*
	 * @author: Francisco Maria Calisto
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

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		CreateUserIntegrator integration = new CreateUserIntegrator (root, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		integration.execute();
		
		User user = getUserFromUsername(USERNAME_DOES_NOT_EXIST);
		assertEquals(USERNAME_DOES_NOT_EXIST, user.getUsername());
		assertEquals("email@email.email", user.getEmail());
		assertEquals("José Ferreira", user.getName());

	}

	@Test(expected = EmptyEmailException.class)
	public void InvalidEmail() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		CreateUserIntegrator integration = new CreateUserIntegrator(root, USERNAME_DOES_NOT_EXIST, "", "José Ferreira");
		integration.execute();

	}

	@Test(expected = InvalidUsernameException.class)
	public void InvalidShortUsername() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		CreateUserIntegrator integration = new CreateUserIntegrator(root, "", "email@email.email", "José Ferreira");
		integration.execute();

	}
	
	@Test(expected = InvalidUsernameException.class)
	public void InvalidLongUsername() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		CreateUserIntegrator integration = new CreateUserIntegrator(root, "lookathowlongthisusernameis", "email@email.email", "José Ferreira");
		integration.execute();

	}
	
	@Test(expected = UnauthorizedOperationException.class)
	public void unauthorizedUserCreation() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		CreateUserIntegrator integration = new CreateUserIntegrator(ars, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		integration.execute();

	}

	@Test(expected = UserNotInSessionException.class)
	public void InvalidUser() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		removeUserFromSession(root);

		CreateUserIntegrator integration = new CreateUserIntegrator(root, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		integration.execute();

	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void InvalidCreate() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		CreateUserIntegrator integration = new CreateUserIntegrator (root, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		integration.execute();
		integration.execute();

	}

	@Test(expected = LoginBubbleDocsException.class)
	public void InvalidLogin() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new LoginBubbleDocsException(username);
			}
		};

		CreateUserIntegrator integration = new CreateUserIntegrator (root, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		integration.execute();

	}

	@Test(expected = UnavailableServiceException.class)
	public void InvalidService() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new RemoteInvocationException();
			}
		};
		
		CreateUserIntegrator integration = new CreateUserIntegrator (root, USERNAME_DOES_NOT_EXIST, "email@email.email", "José Ferreira");
		integration.execute();

	}
}
