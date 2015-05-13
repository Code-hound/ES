package id.cli;

import static org.junit.Assert.assertTrue;

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

/**
 * Test suite
 */
public class CreateUserIT extends AbstractIdIT {

    // tests
    // assertEquals(expected, actual);

    // public void createUser(CreateUser parameters)
    // throws EmailAlreadyExists_Exception, InvalidEmail_Exception,
    // UserAlreadyExists;
	private IdClient ID_CLIENT;
	private static final String uddiURL = "http://localhost:8081";
	private static final String wsName = "SD-ID";
	
	@Before
	public void setup() 
			throws JAXRException, 
			NoSuchAlgorithmException, 
			IdClient_Exception, 
			IDClientException {
		ID_CLIENT = new IdClient(uddiURL, wsName);
	}
	
	@After
	public void teardown() {
		ID_CLIENT = null;
	}
	
    @Test
    public void testCreateUser() throws Exception {
        String password = ID_CLIENT.createUser("user1", "user1@domain.net");
        assertTrue(password.length() == 20);
    }

    @Test(expected = InvalidUser_Exception.class)
    public void testCreateNullUser() throws Exception {
        ID_CLIENT.createUser(null, "user2@domain.net");
    }

    @Test(expected = InvalidUser_Exception.class)
    public void testCreateEmptyUser() throws Exception {
        ID_CLIENT.createUser("", "user2@domain.net");
    }

    @Test(expected = EmailAlreadyExists_Exception.class)
    public void testEmailExists() throws Exception {
        ID_CLIENT.createUser("user3a", "user3@domain.net");
        ID_CLIENT.createUser("user3b", "user3@domain.net");
    }

    @Test(expected = InvalidEmail_Exception.class)
    public void testInvalidEmail() throws Exception {
        ID_CLIENT.createUser("user4", "user4");
    }

    @Test(expected = InvalidEmail_Exception.class)
    public void testIncompleteEmail() throws Exception {
        ID_CLIENT.createUser("user5", "user5@");
    }
    
    @Test(expected = InvalidEmail_Exception.class)
    public void testIncompleteEmail2() throws Exception {
        ID_CLIENT.createUser("user6", "@domain.net");
    }

    @Test(expected = InvalidEmail_Exception.class)
    public void testEmptyEmail() throws Exception {
        ID_CLIENT.createUser("user7", "");
    }

    @Test(expected = InvalidEmail_Exception.class)
    public void testNullEmail() throws Exception {
        ID_CLIENT.createUser("user8", null);
    }

    @Test(expected = UserAlreadyExists_Exception.class)
    public void testUserAlreadyExists() throws Exception {
        ID_CLIENT.createUser("user9", "user9a@domain.net");
        ID_CLIENT.createUser("user9", "user9b@domain.net");
    }

}
