package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.exception.InvalidUserException;
import pt.tecnico.bubbledocs.exception.WrongPasswordException;


public class LoginUserServiceTest extends BubbleDocsServiceTest {

	private static final String USERNAME = "jpname";
	private static final String PASSWORD = "jp#";

	@Override
	public void populate4Test() {
		createUser(USERNAME, PASSWORD, "João Pereira", "email@email.email");
	}

	// returns the time of the last access for the user with token userToken.
	// It must get this data from the session object of the application
	private LocalTime getLastAccessTimeInSession(String userToken) {
		BubbleDocs bd = BubbleDocs.getInstance();
		return bd.getSessionByToken(userToken).getLastAccess().toLocalTime();
	}

	@Test
	public void success() {
		LoginUserService service = new LoginUserService(USERNAME, PASSWORD);
		service.execute();
		LocalTime currentTime = new LocalTime();

		String token = service.getUserToken();
		//System.out.println("User token: " + token);

		User user = getUserFromSession(service.getUserToken());
		assertEquals(USERNAME, user.getUsername());

		int difference = Seconds.secondsBetween(getLastAccessTimeInSession(token), currentTime).getSeconds();

		assertTrue("Access time in session not correctly set", difference >= 0);
		assertTrue("diference in seconds greater than expected", difference < 2);
	}

	@Test
	public void successLoginTwice() {
		LoginUserService service = new LoginUserService(USERNAME, PASSWORD);

		service.execute();
		String token1 = service.getUserToken();

		service.execute();
		String token2 = service.getUserToken();

		User user = getUserFromSession(token1);
		assertNull(user);
		user = getUserFromSession(token2);
		assertEquals(USERNAME, user.getUsername());
	}

	@Test (expected = InvalidUserException.class)
	public void InvalidUser() {
		LoginUserService service = new LoginUserService("error", PASSWORD);
		service.execute();
	}

	@Test(expected = WrongPasswordException.class)
	public void WrongPassword() {
		LoginUserService service = new LoginUserService(USERNAME, "error");
		service.execute();
	}
}
