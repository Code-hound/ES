package id.cli;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import javax.xml.registry.JAXRException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.sdis.id.cli.IDClientException;
import pt.ulisboa.tecnico.sdis.id.cli.IdClient;
import pt.ulisboa.tecnico.sdis.id.exception.IdClient_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.EmailAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.InvalidEmail_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.InvalidUser_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.UserAlreadyExists_Exception;

public class RealTests {
	
	private static final String uddiURL = "http://localhost:8081";
	private static final String wsName = "SD-ID";
	private static final String userID = "luis";
	private static final String email = "luis@gmail.com";
	private IdClient client;
	
	@Before
	public void setup() 
			throws JAXRException, 
			NoSuchAlgorithmException, 
			IdClient_Exception, 
			IDClientException {
		client = new IdClient(uddiURL, wsName);
	}
	
	@After
	public void teardown() {
		client = null;
	}
	
	@Test
	public void successCreateUser() throws Exception {
		String password = client.createUser(userID, email);
		assertTrue(password.length() == 20);
	}
	
	@Test (expected = UserAlreadyExists_Exception.class)
	public void createDuplicateUser() throws Exception {
		String password1 = client.createUser(userID, email);
		String password2 = client.createUser(userID, email);
	}
	
	@Test (expected = InvalidEmail_Exception.class)
	public void createUserWithInvalidEmail() throws Exception {
		String password = client.createUser(userID, "donut");
	}
}
