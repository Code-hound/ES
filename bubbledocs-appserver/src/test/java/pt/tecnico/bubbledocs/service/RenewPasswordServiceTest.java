package pt.tecnico.bubbledocs.service;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class RenewPasswordServiceTest extends BubbleDocsServiceTest {

    // the tokens
	private String userToken;

	@Override
	public void populate4Test() {

		createUser("jpname", "jp#", "João Pereira", "email@email.email");
		this.userToken = addUserToSession("jpname");

	}

	@Test
	public void success() {

		RenewPasswordService service = new RenewPasswordService(userToken);
		service.execute();

	}

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

		RenewPasswordService service = new RenewPasswordService("error");
		service.execute();

    }
}