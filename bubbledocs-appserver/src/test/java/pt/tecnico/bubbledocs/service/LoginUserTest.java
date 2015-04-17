package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;

import pt.tecnico.bubbledocs.domain.Session;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.exception.InvalidUserException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;


public class LoginUserTest extends BubbleDocsServiceTest {

	private static final String USERNAME = "jp";
	private static final String PASSWORD = "jp#";

	@Override
	public void populate4Test() {
		createUser(USERNAME, PASSWORD, "João Pereira", "email@email.email");
	}

	// returns the time of the last access for the user with token userToken.
	// It must get this data from the session object of the application
	private LocalTime getLastAccessTimeInSession(String userToken) {
		BubbleDocs bd = BubbleDocs.getInstance();
		Session session = bd.getSessionByToken(userToken);
		return session.getLastAccess().toLocalTime();
	}

	@Test
	public void success() {
		LoginUser service = new LoginUser(USERNAME, PASSWORD);
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
		LoginUser service = new LoginUser(USERNAME, PASSWORD);

		service.execute();
		String token1 = service.getUserToken();

		service.execute();
		String token2 = service.getUserToken();

		User user = getUserFromSession(token1);
		assertNull(user);
		user = getUserFromSession(token2);
		assertEquals(USERNAME, user.getUsername());
	}

	@Test (expected = UnavailableServiceException.class)
	public void InvalidUser() {
		new MockUp<IDRemoteServices>() {
			@Mock
			public void loginUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new RemoteInvocationException();
			}
		};
		LoginUser service = new LoginUser("error", PASSWORD);
		service.execute();
	}
	
	@Test(expected = UnavailableServiceException.class)
	public void InvalidPassword() {
		new MockUp<IDRemoteServices>() {
			@Mock
			public void loginUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
				throw new RemoteInvocationException();
			}
		};
		LoginUser service = new LoginUser(USERNAME, "error");
		service.execute();
	}
}
