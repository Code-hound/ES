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
