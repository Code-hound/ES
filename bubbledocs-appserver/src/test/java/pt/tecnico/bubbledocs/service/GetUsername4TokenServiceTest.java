package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class GetUsername4TokenServiceTest extends BubbleDocsServiceTest {

    // the tokens
	private String userToken;

	@Override
	public void populate4Test() {

		createUser("jpname", "jp#", "João Pereira", "email@email.email");
		this.userToken = addUserToSession("jpname");

	}

	@Test
	public void success() {

		GetUsername4TokenService service = new GetUsername4TokenService(userToken);
		service.execute();

		assertEquals(service.getUsername(), "jpname");

	}

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

		GetUsername4TokenService service = new GetUsername4TokenService("error");
		service.execute();

    }

}
