package pt.tecnico.bubbledocs.service;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class GetUserInfoServiceTest extends BubbleDocsServiceTest {

    // the tokens
	private String userToken;

	@Override
	public void populate4Test() {

		createUser("jpname", "jp#", "João Pereira", "email@email.email");
		this.userToken = addUserToSession("jpname");

	}

	@Test
	public void success() {

		GetUserInfoService service = new GetUserInfoService(userToken);
		service.execute();

	}

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

		GetUserInfoService service = new GetUserInfoService("error");
		service.execute();

    }

}