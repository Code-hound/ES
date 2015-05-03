package id.ws;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.id.ws.*;
import id.ws.IdImpl;

public class UserImplTests {
	private static final String ALICE_USERNAME = "alice";
	private static final String ALICE_EMAIL = "alice@tecnico.pt";

	private static final String BRUNO_USERNAME = "bruno";
	private static final String BRUNO_EMAIL = "bruno@tecnico.pt";

	private static final String CARLA_USERNAME = "carla";
	private static final String CARLA_EMAIL = "carla@tecnico.pt";

	private static final String DUARTE_USERNAME = "duarte";
	private static final String DUARTE_EMAIL = "duarte@tecnico.pt";

	private static final String EDUARDO_USERNAME = "eduardo";
	private static final String EDUARDO_EMAIL = "eduardo@tecnico.pt";
	
	private static final String INVALID_USERNAME = "";
	private static final String INVALID_EMAIL = "@b";

	private static IdImpl id = new IdImpl();
	@Before
	public void setUpBeforeClass() throws Exception {
		id.createUser(ALICE_USERNAME, ALICE_EMAIL);
		id.createUser(BRUNO_USERNAME, BRUNO_EMAIL);
		id.createUser(CARLA_USERNAME, CARLA_EMAIL);
		id.createUser(DUARTE_USERNAME, DUARTE_EMAIL);
		id.createUser(EDUARDO_USERNAME, EDUARDO_EMAIL);
	}
	
	@Test(expected = InvalidUser_Exception.class)
	public void testInvalidUser()
			throws EmailAlreadyExists_Exception,
		       InvalidEmail_Exception,
		       InvalidUser_Exception,
		       UserAlreadyExists_Exception{
		id.createUser(INVALID_USERNAME,"blabla@email.pt");
	}
	
	@Test(expected = UserAlreadyExists_Exception.class)
	public void testDuplicateUser()
			throws EmailAlreadyExists_Exception,
		       InvalidEmail_Exception,
		       InvalidUser_Exception,
		       UserAlreadyExists_Exception{
        id.createUser(ALICE_USERNAME,"alice@tecnico.pt");
        id.createUser(ALICE_USERNAME,"alice@tecnico.pt");
	}	
	
	
	// TESTS FOR PASSWORD RENEWING
	@Test
	public void renewSuccess() throws UserDoesNotExist_Exception {
		id.renewPassword("alice");
		id.renewPassword("bruno");
		id.renewPassword("carla");
		id.renewPassword("duarte");
		id.renewPassword("eduardo");
	}
	
	@Test(expected = UserDoesNotExist_Exception.class)
	public void testRenewUser() throws UserDoesNotExist_Exception {
		id.renewPassword("desconhecido");
	}
	
	
	// TESTS FOR USER REMOVING
	
	@Test(expected = UserDoesNotExist_Exception.class)
	public void testRemoveUser() throws UserDoesNotExist_Exception {
		id.removeUser("desconhecido");
	}
	
	@After
	public void tearDownAfterClass() throws Exception {
		id.removeUser(ALICE_USERNAME);
		id.removeUser(BRUNO_USERNAME);
		id.removeUser(CARLA_USERNAME);
		id.removeUser(DUARTE_USERNAME);
		id.removeUser(EDUARDO_USERNAME);
	}
}
