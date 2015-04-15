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
	private static final String USERNAME = "username";
	private static final String USERNAME2 = "username2";
	private static final String EMAIL = "a@b";
	private static final String INVALID_USERNAME = "";
	private static final String INVALID_EMAIL = "@b";
	// private static final byte[] PASSWORD = "" /* COMO COMPARAR SE A PASS É GERADA AUTOMATICAMENTE? */
	private static createUser user = new createUser();
	private static createUser userDupEmail = new createUser(); //CONFIRMAR

	private static IdImpl id = new IdImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		user.setUserId(USERNAME);
		user.setEmailAddress(EMAIL);
		
		userDupEmail.setUserId(USERNAME2);
		userDupEmail.setEmailAddress(EMAIL);
	}
	
	@Test
	public void success() {
		id.createUser(user);
		
		assertEquals("username", user.getUserId());
		assertEquals("a@b", user.getEmailAddress());
	}
	
	@Test(expected = EmailAlreadyExists_Exception.class)
	public void testDuplicateEmail() {
		id.createUser(userDupEmail);
	}
	
	
	@Test
	public void renewPassword() {
		/*List<String> docs = store.listDocs(USERNAME);
		//System.out.println(docs);
		assertTrue("File number does not match the expected",
				(store.listDocs(USERNAME).size()==1));*/
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		id.removeUser(USERNAME);
		id.removeUser(USERNAME2);
	}
}
