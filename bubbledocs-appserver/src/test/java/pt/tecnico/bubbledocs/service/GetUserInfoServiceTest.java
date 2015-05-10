package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.InvalidUserException;

public class GetUserInfoServiceTest extends BubbleDocsServiceTest {

	@Override
	public void populate4Test() {

		createUser("jpname", "jp#", "João Pereira", "email@email.email");
		addUserToSession("jpname");

	}

	@Test
	public void success() {

		GetUserInfoService service = new GetUserInfoService("jpname");
		service.execute();
		
		assertNotNull(service.getUser());

	}

	@Test (expected = InvalidUserException.class)
    public void InvalidUser() {

		GetUserInfoService service = new GetUserInfoService("error");
		service.execute();

    }

}