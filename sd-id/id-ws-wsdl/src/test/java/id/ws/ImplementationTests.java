package id.ws;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.id.ws.exception;
import id.ws.IdImpl;

public class ImplementationTests {
	/*private static final String USERNAME = "username";
	private static final String USERNAME2 = "username2";
	private static final String EMAIL = "a@b";
	private static final String INVALID_USERNAME = "";
	private static final String INVALID_EMAIL = "@b";*/
	// private static final byte[] PASSWORD = "" /* COMO COMPARAR SE A PASS É GERADA AUTOMATICAMENTE? */
	private static createUser alice;
	private static createUser userDupEmail = new createUser(); //CONFIRMAR

	private static IdImpl id = new IdImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    alice.setUserId("alice");
	    alice.setEmailAddress("alice@tecnico.pt");
	}
	
	
	//TESTS FOR USER CREATION
	@Test
	public void userSuccess() {
	    id.createUser = new createUser()
	    assertEquals("alice", alice.getUserId());
		assertEquals("alice@tecnico.pt", alice.getEmailAddress());
	}
	
	@Test(expected = EmailAlreadyExists_Exception.class)
	public void testDuplicateEmail() {
		id.createUser(USERNAME2,EMAIL);
	}
	
	@Test(expected = InvalidEmail_Exception.class)
	public void testInvalidEmail() {
		id.createUser(USERNAME2,INVALID_EMAIL);
	}
	
	@Test(expected = InvalidUser_Exception.class)
	public void testInvalidUser(){
		id.createUser(INVALID_USERNAME,"b@a");
	}
	
	@Test(expected = UserAlreadyExists_Exception.class)
	public void testDuplicateUser(){
		id.createUser(USERNAME,"b@a");
	}	
	
	
	// TESTS FOR PASSWORD RENEWING XXX
	@Test
	public void renewSuccess() {
		/*FIX ME*/
	}
	
	@Test(expected = UserDoesNotExist_Exception.class)
	public void testRenewUser(){
		/*FIX ME*/
	}
	
	
	// TESTS FOR USER REMOVING
	@Test
	public void removeSuccess() {
		/*FIX ME*/
	}
	
	@Test(expected = UserDoesNotExist_Exception.class)
	public void testRemoveUser(){
		/*FIX ME*/
	}	
	
	
	// TESTS FOR AUTHENTICATION
	
	@Test
	public void authenticationSuccess(){
		/*FIX ME*/
	}
	
	@Test(expected = AuthReqFailed_Exception.class)
	public void testUserDoesNotExist(){
		/*FIX ME*/
	}
	
	@Test(expected = AuthReqFailed_Exception.class) XXX
	public void testIncorrectPassword(){
		/*FIX ME*/
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		id.removeUser(USERNAME);
	}
}
