package pt.tecnico.bubbledocs.integration;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

public class RenewPasswordIntegratorTest extends BubbleDocsIntegratorTest {

	private static final String USERNAME = "jpname";
	private static final String PASSWORD = "jp#";
	private String userToken;

	@Override
	public void populate4Test() {
		createUser(USERNAME, PASSWORD, "João Pereira", "email@email.email");
		this.userToken = addUserToSession(USERNAME);
	}

	@Test
	public void success() {
		RenewPasswordIntegrator integration = new RenewPasswordIntegrator(this.userToken);
		integration.execute();
	}

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {
		this.userToken = "error";
		success();
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
		success();
	}
}
