package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;

/*
 * Export Document Service Test
 * 
 * BubbleDocs Exporting Service Test
 * 
 * @author: Luis Ribeiro Gomes
 * 
 */

public class ExportDocumentServiceTest extends BubbleDocsServiceTest {
	
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

        ExportDocumentService service = new ExportDocumentService(ownerToken, docID);
        service.execute();

        assertNotNull(service.getResult());

    }

    @Test
    public void successReader() {

    	addAccess(getUserFromSession(userToken), getSpreadSheet("doc"), "reader");

        ExportDocumentService service = new ExportDocumentService(userToken, docID);
        service.execute();

        assertNotNull(service.getResult());

    }

    @Test
    public void successWriter() {

    	addAccess(getUserFromSession(userToken), getSpreadSheet("doc"), "writer");

        ExportDocumentService service = new ExportDocumentService(userToken, docID);
        service.execute();

        assertNotNull(service.getResult());

    }

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

		ExportDocumentService service = new ExportDocumentService("error", docID);
        service.execute();

    }

    @Test(expected = InvalidAccessException.class)
    public void InvalidAccess() {

		ExportDocumentService service = new ExportDocumentService(userToken, docID);
        service.execute();

    }
	
    @Test(expected = DocumentDoesNotExistException.class)
    public void InvalidDocument() {

		ExportDocumentService service = new ExportDocumentService(ownerToken, error);
        service.execute();

    }

}
