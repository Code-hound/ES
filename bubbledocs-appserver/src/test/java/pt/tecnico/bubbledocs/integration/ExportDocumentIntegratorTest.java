package pt.tecnico.bubbledocs.integration;

import static org.junit.Assert.assertNotNull;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;

import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;

/*
 * Export Document Integrator Test
 * 
 * BubbleDocs Exporting Integrator Test
 * 
 * @author: Luis Ribeiro Gomes
 * 
 */

public class ExportDocumentIntegratorTest extends BubbleDocsIntegratorTest {
	
    // the tokens
	private String ownerToken;
	private String userToken;
	private int    docID;
	private int    error;

    @Override
    public void populate4Test() {

		createUser("admin"    , "password1", "nameowner da Costa" , "rnl@tecnico.ulisboa.pt"           );
    	createUser("usrnm666" , "password" , "nameuser da Costa"  , "nameuser.costa@tecnico.ulisboa.pt");
	
    	ownerToken = addUserToSession("admin");
    	userToken  = addUserToSession("usrnm666");
		docID      = createSpreadSheet(getUserFromSession(ownerToken) , "doc", 10, 10).getId();
		error      = -1;

    }

    @Test
    public void successOwner() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    	};

        ExportDocumentIntegrator integration = new ExportDocumentIntegrator(ownerToken, docID);
        integration.execute();

        assertNotNull(integration.getResult());

    }

    @Test
    public void successReader() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    	};

    	addAccess(getUserFromSession(userToken), getSpreadSheet("doc"), "reader");

        ExportDocumentIntegrator integration = new ExportDocumentIntegrator(userToken, docID);
        integration.execute();

        assertNotNull(integration.getResult());

    }

    @Test
    public void successWriter() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    	};

    	addAccess(getUserFromSession(userToken), getSpreadSheet("doc"), "writer");

        ExportDocumentIntegrator integration = new ExportDocumentIntegrator(userToken, docID);
        integration.execute();

        assertNotNull(integration.getResult());

    }

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    	};

		ExportDocumentIntegrator integration = new ExportDocumentIntegrator("error", docID);
		integration.execute();

    }

    @Test(expected = InvalidAccessException.class)
    public void InvalidAccess() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    	};

		ExportDocumentIntegrator integration = new ExportDocumentIntegrator(userToken, docID);
		integration.execute();

    }
	
    @Test(expected = DocumentDoesNotExistException.class)
    public void InvalidDocument() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    	};

		ExportDocumentIntegrator integration = new ExportDocumentIntegrator(ownerToken, error);
		integration.execute();

    }

    @Test(expected = CannotStoreDocumentException.class)
    public void InvalidStorage() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			throw new CannotStoreDocumentException(SpreadSheetName);
    		}
    	};

        ExportDocumentIntegrator integration = new ExportDocumentIntegrator(ownerToken, docID);
        integration.execute();

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

        ExportDocumentIntegrator integration = new ExportDocumentIntegrator(ownerToken, docID);
        integration.execute();

    }
}
