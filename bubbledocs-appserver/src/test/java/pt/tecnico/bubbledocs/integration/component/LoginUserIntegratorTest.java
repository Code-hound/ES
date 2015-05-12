package pt.tecnico.bubbledocs.integration.component;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.exception.InvalidUserException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.integration.LoginUserIntegrator;

public class LoginUserIntegratorTest extends BubbleDocsIntegratorTest {

	private static final String USERNAME = "jpname";
	private static final String PASSWORD = "jp#";

	@Override
	public void populate4Test() {
		createUser(USERNAME, PASSWORD, "João Pereira", "email@email.email");
	}

	@Test
	public void success() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void loginUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		LoginUserIntegrator integration = new LoginUserIntegrator(USERNAME, PASSWORD);
		integration.execute();

	}

	@Test (expected = InvalidUserException.class)
	public void InvalidUser() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void loginUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new RemoteInvocationException();
			}
		};

		LoginUserIntegrator integration = new LoginUserIntegrator("error", PASSWORD);
		integration.execute();

	}

	@Test(expected = UnavailableServiceException.class)
	public void PasswordNotSaved() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void loginUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new RemoteInvocationException();
			}
		};

		User user = BubbleDocs.getInstance().getUserByUsername(USERNAME);
		user.setPassword(null);
		
		LoginUserIntegrator integration = new LoginUserIntegrator(USERNAME, PASSWORD);
		integration.execute();

	}

	@Test(expected = UnavailableServiceException.class)
	public void WrongPassword() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void loginUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new RemoteInvocationException();
			}
		};

		LoginUserIntegrator integration = new LoginUserIntegrator(USERNAME, "error");
		integration.execute();

	}

}
