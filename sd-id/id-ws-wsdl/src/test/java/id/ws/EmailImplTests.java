package id.ws;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.id.ws.*;
import id.ws.IdImpl;

public class EmailImplTests {
	private static final String ALICE_USERNAME = "alice";
	private static final String ALICE_EMAIL = "alice@tecnico.pt";
	
	private static final String INVALID_USERNAME = "";
	private static final String INVALID_EMAIL = "@b";

	private static IdImpl id = new IdImpl();
	@Before
	public void setUpBeforeClass() throws Exception {
		id.createUser(ALICE_USERNAME, ALICE_EMAIL);
	}
	
	@Test(expected = EmailAlreadyExists_Exception.class)
	public void testDuplicateEmail() 
			throws EmailAlreadyExists_Exception,
		    InvalidEmail_Exception,
	        InvalidUser_Exception,
		    UserAlreadyExists_Exception{
		id.createUser("aline",ALICE_EMAIL);
	}
	
	@Test(expected = InvalidEmail_Exception.class)
	public void testInvalidEmail() 
			throws EmailAlreadyExists_Exception,
		       InvalidEmail_Exception,
		       InvalidUser_Exception,
		       UserAlreadyExists_Exception{
		id.createUser("joao",INVALID_EMAIL);
	}
	
	@Test(expected = UserDoesNotExist_Exception.class)
	public void testRemoveUser() throws UserDoesNotExist_Exception {
		id.removeUser("desconhecido");
	}
	
	@After
	public void tearDownAfterClass() throws Exception {
		id.removeUser(ALICE_USERNAME);
	}
}
