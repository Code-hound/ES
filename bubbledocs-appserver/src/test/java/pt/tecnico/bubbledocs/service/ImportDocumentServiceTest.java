package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;

import org.omg.CORBA.portable.InputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.ImportDocumentException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;

public class ImportDocumentServiceTest extends BubbleDocsServiceTest {

    // the tokens
	private String      ownerToken;
	private String      userToken;
	private SpreadSheet doc;
	private byte[]      xml;

    @Override
    public void populate4Test() {

		createUser("admin"    , "password1", "nameowner da Costa" , "rnl@tecnico.ulisboa.pt"           );
    	createUser("usrnm666" , "password" , "nameuser da Costa"  , "nameuser.costa@tecnico.ulisboa.pt");
	
    	ownerToken = addUserToSession("admin");
    	userToken  = addUserToSession("usrnm666");
		doc        = createSpreadSheet(getUserFromSession(ownerToken) , "doc", 10, 10);
		
		//note: Actually, the fact the username666 being or writer or reader
		//      or the admin being the owner is not that relevant.
		addAccess(getUserFromSession(userToken), getSpreadSheet("doc"), "reader");
		
		ExportDocumentService service = new ExportDocumentService(userToken, doc.getId());
		service.execute();
        
        this.xml = service.getResult();

    }

    @Test
    public void success() {

    	ImportDocumentService service = new ImportDocumentService(userToken, xml);
    	service.execute();

        assertEquals(doc,service.getResult());

    }

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {

    	ImportDocumentService service = new ImportDocumentService("error", xml);
    	service.execute();

    }
    
    @Test(expected = InvalidAccessException.class)
    public void InvalidAccess() {

    	ImportDocumentService service = new ImportDocumentService(ownerToken, xml);
    	service.execute();

    }

}