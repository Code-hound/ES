package pt.tecnico.bubbledocs.integration;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.integration.component.RenewPasswordIntegrator;

public class RenewPasswordIntegratorTest extends BubbleDocsIntegratorTest {

    // the tokens
	private String userToken;

	@Override
	public void populate4Test() {

		createUser("jpname", "jp#", "João Pereira", "email@email.email");
		this.userToken = addUserToSession("jpname");

	}

	@Test
	public void success() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void renewPassword(String username)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		RenewPasswordIntegrator integration = new RenewPasswordIntegrator(userToken);
		integration.execute();

	}

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void renewPassword(String username)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};

		RenewPasswordIntegrator integration = new RenewPasswordIntegrator("error");
		integration.execute();

    }
	
	@Test (expected = UnavailableServiceException.class)
	public void InvalidService() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void renewPassword(String username)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new RemoteInvocationException();
			}
		};

		RenewPasswordIntegrator integration = new RenewPasswordIntegrator(userToken);
		integration.execute();

	}
	
	@Test (expected = LoginBubbleDocsException.class)
	public void InvalidLogin() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void renewPassword(String username)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new LoginBubbleDocsException(username);
			}
		};

		RenewPasswordIntegrator integration = new RenewPasswordIntegrator(userToken);
		integration.execute();

	}
}
