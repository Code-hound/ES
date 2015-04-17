package id.ws;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.id.ws.AuthReqFailed_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.InvalidUser_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.UserAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.UserDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.exception;
import id.ws.IdImpl;

public class ImplementationTests {
	private static final String ALICE_USERNAME = "alice";
	private static final String ALICE_EMAIL = "alice@tecnico.pt";
	
	private static final String INVALID_USERNAME = "";
	private static final String INVALID_EMAIL = "@b";
    
	private static createUser alice;

	private static IdImpl id = new IdImpl(ALICE_USERNAME,ALICE_EMAIL,  );

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	
	//TESTS FOR USER CREATION
	@Test
	public void userSuccess() {
	    alice = id.createUser(ALICE_USERNAME,ALICE_EMAIL);
	    assertEquals(ALICE_USERNAME, alice.getUserId());
		assertEquals(ALICE_EMAIL, alice.getEmailAddress());
	}
	
	@Test(expected = EmailAlreadyExists_Exception.class)
	public void testDuplicateEmail() {
		id.createUser("aline",ALICE_EMAIL);
	}
	
	@Test(expected = InvalidEmail_Exception.class)
	public void testInvalidEmail() {
		id.createUser("joao",INVALID_EMAIL);
	}
	
	@Test(expected = InvalidUser_Exception.class)
	public void testInvalidUser(){
		id.createUser(INVALID_USERNAME,"b@a");
	}
	
	@Test(expected = UserAlreadyExists_Exception.class)
	public void testDuplicateUser(){
        id.createUser(ALICE_USERNAME,"b@a");
        id.createUser(ALICE_USERNAME,"b@a");
	}	
	
	
	// TESTS FOR PASSWORD RENEWING
	@Test
	public void renewSuccess() {
		id.renewPassword("alice");		
	}
	
	@Test(expected = UserDoesNotExist_Exception.class)
	public void testRenewUser(){
		id.renewPassword("desconhecido");
	}
	
	
	// TESTS FOR USER REMOVING
	@Test
	public void removeSuccess() {
		id.removeUser("alice");
	}
	
	@Test(expected = UserDoesNotExist_Exception.class)
	public void testRemoveUser(){
		id.removeUser("desconhecido");
	}	
	
	
	// TESTS FOR AUTHENTICATION
	
	@Test
	public void authenticationSuccess(){
	    id.requestAuthentication("alice","Aaa1")
	}
	
	@Test(expected = AuthReqFailed_Exception.class)
	public void testUserDoesNotExist(){
		id.requestAuthentication("desconhecido","abcd"); ///o arg 2 tem de ser byte[]
	}
	
	@Test(expected = AuthReqFailed_Exception.class) XXX
	public void testIncorrectPassword(){
		id.requestAuthentication("alice","abcd"); ///o arg 2 tem de ser byte[]
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		id.removeUser(ALICE_USERNAME);
	}
}
