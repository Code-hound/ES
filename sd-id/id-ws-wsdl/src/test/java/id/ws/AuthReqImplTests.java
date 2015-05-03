package id.ws;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.id.ws.*;
import id.ws.IdImpl;

public class AuthReqImplTests {
	private static final String ALICE_USERNAME = "alice";
	private static final String ALICE_EMAIL = "alice@tecnico.pt";
	
	private static final String INVALID_USERNAME = "";
	private static final String INVALID_EMAIL = "@b";

	private static IdImpl id = new IdImpl();
	
	@Before
	public void setUpBeforeClass() throws Exception {
		id.createUser(ALICE_USERNAME, ALICE_EMAIL);
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
