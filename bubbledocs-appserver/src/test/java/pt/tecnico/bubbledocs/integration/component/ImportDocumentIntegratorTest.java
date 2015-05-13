package pt.tecnico.bubbledocs.integration.component;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import mockit.Mock;
import mockit.MockUp;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.junit.Test;
import org.omg.CORBA.portable.InputStream;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.integration.ExportDocumentIntegrator;
import pt.tecnico.bubbledocs.integration.ImportDocumentIntegrator;
import pt.tecnico.bubbledocs.exception.ExportDocumentException;
import pt.tecnico.bubbledocs.exception.ImportDocumentException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;
import pt.tecnico.bubbledocs.service.ImportDocumentService;
import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;

public class ImportDocumentIntegratorTest extends BubbleDocsIntegratorTest {

    // the tokens
	private String      ownerToken;
	private String      userToken;
	private SpreadSheet doc;
	private int         docID;

    @Override
    public void populate4Test() {

		createUser("admin"    , "password1", "nameowner da Costa" , "rnl@tecnico.ulisboa.pt"           );
    	createUser("usrnm666" , "password" , "nameuser da Costa"  , "nameuser.costa@tecnico.ulisboa.pt");
	
    	ownerToken = addUserToSession("admin");
    	userToken  = addUserToSession("usrnm666");
		doc        = createSpreadSheet(getUserFromSession(ownerToken) , "doc", 10, 10);
		docID      = doc.getId();
		
		addAccess(getUserFromSession(userToken), doc, "reader");
		
        doc.setOwnerUsername("usrnm666");
        doc.setId(doc.getId() + 1);

    }

    @Test
    public void success() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {

    			pt.tecnico.bubbledocs.domain.SpreadSheet doc = new pt.tecnico.bubbledocs.domain.SpreadSheet("usrnm666", 0, "doc", 10, 10);

    			XMLOutputter xml = new XMLOutputter();
    			Document jdomDoc = new Document();

    			jdomDoc.setRootElement(doc.exportToXML());
    			
    			//throws ExportDocumentException
    			try {
    				return xml.outputString(jdomDoc).getBytes("UTF-8");
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    				throw new ExportDocumentException();
    			}

    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator(userToken, docID);
        integration.execute();

        assertEquals(integration.getResult().toString(),doc.toString());

    }

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {

    			pt.tecnico.bubbledocs.domain.SpreadSheet doc = new pt.tecnico.bubbledocs.domain.SpreadSheet("usrnm666", 0, "doc", 10, 10);

    			XMLOutputter xml = new XMLOutputter();
    			Document jdomDoc = new Document();

    			jdomDoc.setRootElement(doc.exportToXML());
    			
    			//throws ExportDocumentException
    			try {
    				return xml.outputString(jdomDoc).getBytes("UTF-8");
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    				throw new ExportDocumentException();
    			}

    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator("error", docID);
		integration.execute();

    }

    @Test(expected = InvalidAccessException.class)
    public void InvalidAccess() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {

    			pt.tecnico.bubbledocs.domain.SpreadSheet doc = new pt.tecnico.bubbledocs.domain.SpreadSheet("usrnm666", 0, "doc", 10, 10);

    			XMLOutputter xml = new XMLOutputter();
    			Document jdomDoc = new Document();

    			jdomDoc.setRootElement(doc.exportToXML());
    			
    			//throws ExportDocumentException
    			try {
    				return xml.outputString(jdomDoc).getBytes("UTF-8");
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    				throw new ExportDocumentException();
    			}

    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator(ownerToken, docID);
		integration.execute();

    }

    @Test(expected = CannotStoreDocumentException.class)
    public void InvalidStorage() {

    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			throw new CannotStoreDocumentException(username);
    		}
    	};

    	ImportDocumentIntegrator integration = new ImportDocumentIntegrator(userToken, docID);
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

        ImportDocumentIntegrator integration = new ImportDocumentIntegrator(userToken, docID);
        integration.execute();

    }

}
