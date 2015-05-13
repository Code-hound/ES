package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class RenewPasswordServiceTest extends BubbleDocsServiceTest {

	// User
	private User user;
    private final String USERNAME     = "jpname";
    private final String PASSWORD     = "jp#";
    private final String NAMEUSER     = "João Pereira";
    private final String EMAIL        = "email@email.email";
    
    // the tokens
	private String userToken;

	@Override
	public void populate4Test() {

		user = createUser(USERNAME, PASSWORD, NAMEUSER, EMAIL);
		this.userToken = addUserToSession(USERNAME);

	}

	@Test
	public void success() {

		RenewPasswordService service = new RenewPasswordService(userToken);
		service.execute();
		
		assertNull(user.getPassword());

	}

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

		RenewPasswordService service = new RenewPasswordService("error");
		service.execute();

    }
}