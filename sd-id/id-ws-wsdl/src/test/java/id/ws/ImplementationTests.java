package id.ws;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.id.ws.*;
import id.ws.IdImpl;

public class ImplementationTests {
	private static final String ALICE_USERNAME = "alice";
	private static final String ALICE_EMAIL = "alice@tecnico.pt";
	
	private static final String INVALID_USERNAME = "";
	private static final String INVALID_EMAIL = "@b";

	private static IdImpl id = new IdImpl();
	@Before
	public void setUpBeforeClass() throws Exception {
		id.createUser(ALICE_USERNAME, ALICE_EMAIL);
	}
	
	
	//TESTS FOR USER CREATION
	/*
	@Test
	public void userSuccess() 
			throws EmailAlreadyExists_Exception,
			InvalidEmail_Exception,
			InvalidUser_Exception,
			UserAlreadyExists_Exception {
	    id.createUser(ALICE_USERNAME,ALICE_EMAIL);
	    //assertEquals(ALICE_USERNAME, alice.getUserId());
		//assertEquals(ALICE_EMAIL, alice.getEmailAddress());
	}
	*/
	
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
	/*
	@Test
	public void removeSuccess() throws UserDoesNotExist_Exception {
		id.removeUser(ALICE_USERNAME);
	}
	*/
	
	@Test(expected = UserDoesNotExist_Exception.class)
	public void testRemoveUser() throws UserDoesNotExist_Exception {
		id.removeUser("desconhecido");
	}	
	
	
	// TESTS FOR AUTHENTICATION
	/*
	@Test
	public void authenticationSuccess() throws AuthReqFailed_Exception {
		String password = "Aaa1";
	    id.requestAuthentication("alice",password.getBytes());
	}
	*/
	
	@Test(expected = AuthReqFailed_Exception.class)
	public void testUserDoesNotExist() throws AuthReqFailed_Exception {
		String password = "abcd";
		id.requestAuthentication("desconhecido",password.getBytes()); ///o arg 2 tem de ser byte[]
	}
	
	@Test(expected = AuthReqFailed_Exception.class)
	public void testIncorrectPassword() throws AuthReqFailed_Exception {
		String password = "abcd";
		id.requestAuthentication("alice",password.getBytes()); ///o arg 2 tem de ser byte[]
	}
	
	@After
	public void tearDownAfterClass() throws Exception {
		id.removeUser(ALICE_USERNAME);
	}
}
