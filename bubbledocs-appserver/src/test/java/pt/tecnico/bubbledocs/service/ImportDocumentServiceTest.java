package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertNotNull;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.integration.ExportDocumentIntegrator;
import pt.tecnico.bubbledocs.integration.ImportDocumentIntegrator;

import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;

import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;

public class ImportDocumentServiceTest extends BubbleDocsServiceTest {
    // the tokens
	private String ownerToken;
	private String userToken;
	private int    docID;
	private String docName;
	private String error;
	private byte[] doc;

    @Override
    public void populate4Test() {

		createUser("admin"    , "password1", "nameowner da Costa" , "rnl@tecnico.ulisboa.pt"           );
    	createUser("usrnm666" , "password" , "nameuser da Costa"  , "nameuser.costa@tecnico.ulisboa.pt");
	
    	ownerToken = addUserToSession("admin");
    	userToken  = addUserToSession("usrnm666");
		docID      = createSpreadSheet(getUserFromSession(ownerToken) , "doc", 10, 10).getId();
		docName    = "doc";
		error      = "error";
		
		//note: Actually, the fact the username666 being or writer or reader
		//      or the admin being the owner is not that relevant.
		addAccess(getUserFromSession(userToken), getSpreadSheet("doc"), "reader");
		
		ExportDocumentIntegrator integration = new ExportDocumentIntegrator(userToken, docID);
        integration.execute();
        
        this.doc = integration.getResult();

    }

    @Test
    public void success() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			return null;// this.doc;
    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator(userToken, docName);
        integration.execute();

        assertNotNull(integration.getResult());

    }

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			return null;// this.doc;
    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator("error", docName);
		integration.execute();

    }

    @Test(expected = InvalidAccessException.class)
    public void InvalidAccess() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			return null;// this.doc;
    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator(ownerToken, docName);
		integration.execute();

    }
	
    @Test(expected = DocumentDoesNotExistException.class)
    public void InvalidDocument() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			return null;// this.doc;
    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator(userToken, error);
		integration.execute();

    }

    @Test(expected = CannotStoreDocumentException.class)
    public void InvalidStorage() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			throw new CannotStoreDocumentException(SpreadSheetName);
    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator(userToken, docName);
        integration.execute();

    }

    @Test(expected = UnavailableServiceException.class)
    public void InvalidService() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			throw new RemoteInvocationException();
    		}
    	};

        ImportDocumentIntegrator integration = new ImportDocumentIntegrator(userToken, docName);
        integration.execute();

    }

}
