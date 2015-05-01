package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;
import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;

public class ExportDocumentTest extends BubbleDocsServiceTest {
	
    // the tokens
	private String userToken;
	private int sheetId;

	//User-Owner
	private final String USERNAME_OWNER = "owner";
	private final String PASSWORD_OWNER = "password_owner";
	private final String NAMEUSER_OWNER = "nameuser_owner";
	private final String EMAIL_OWNER = "email_owner";

	//User-No-Access
	private final String USERNAME_NO_ACCESS = "noaccess";
	private final String PASSWORD_NO_ACCESS = "password_no_access";
	private final String NAMEUSER_NO_ACCESS = "nameuser_no_access";
	private final String EMAIL_NO_ACCESS = "email_no_access";

	//Document
	private final String NAME = "sheet";
	private final int ROW     = 10;
	private final int COLUMN  = 10;

    @Override
    public void populate4Test() {
		createUser(USERNAME_NO_ACCESS, PASSWORD_NO_ACCESS, NAMEUSER_NO_ACCESS, EMAIL_NO_ACCESS);
	
		this.sheetId   = createSpreadSheet(createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER, EMAIL_OWNER),NAME, ROW, COLUMN).getId();
		this.userToken = addUserToSession(USERNAME_OWNER);

    }

    @Test
    public void success() {
        ExportDocument service = new ExportDocument(this.userToken, this.sheetId);
        service.execute();
        assertNotNull(service.getResult());
    }

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {
		this.userToken = "error";
		success();
    }

    @Test(expected = InvalidAccessException.class)
    public void InvalidAccess() {
    	this.userToken = addUserToSession(USERNAME_NO_ACCESS);
    	success();
    }	
	
    @Test(expected = DocumentDoesNotExistException.class)
    public void InvalidDocument() {
    	this.sheetId = -1;
    	success();
    }
    
    @Test(expected = UnavailableServiceException.class)
    public void InvalidService() {
    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			throw new RemoteInvocationException();
    		}
    	};
    	success();
    }
}
