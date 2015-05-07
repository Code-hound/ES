package pt.tecnico.bubbledocs.service;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;

public class RenewPasswordTest extends BubbleDocsServiceTest {

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
		RenewPasswordService service = new RenewPasswordService(this.userToken);
		service.execute();
	}

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {
		this.userToken = "error";
		success();
    }
}