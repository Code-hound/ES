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
	
	private static final String INVALID_USERNAME = "";
	private static final String INVALID_EMAIL = "@b";

	private static IdImpl id = new IdImpl();
	@Before
	public void setUpBeforeClass() throws Exception {
		id.createUser(ALICE_USERNAME, ALICE_EMAIL);
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
        id.createUser(ALICE_USERNAME,"b@a");
        id.createUser(ALICE_USERNAME,"b@a");
	}	
	
	
	// TESTS FOR PASSWORD RENEWING
	@Test
	public void renewSuccess() throws UserDoesNotExist_Exception {
		id.renewPassword("alice");		
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
	}
}
