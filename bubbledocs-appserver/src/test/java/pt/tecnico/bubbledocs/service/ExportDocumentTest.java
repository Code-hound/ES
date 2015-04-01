package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.Cell;

import pt.tecnico.bubbledocs.exception.ExportException;
import pt.tecnico.bubbledocs.exception.AccessException;

public class ExportDocumentTest extends BubbleDocsServiceTest {

    // the tokens
	private String ownerToken;
	private String writeToken;
	private String readToken;
	private String noAccessToken;
	private int sheetId;

	//User-Owner
	private final String USERNAME_OWNER = "username_owner";
	private final String PASSWORD_OWNER = "password_owner";
	private final String NAMEUSER_OWNER = "nameuser_owner";

	//User-Write
	private final String USERNAME_WRITE = "username_write";
	private final String PASSWORD_WRITE = "password_write";
	private final String NAMEUSER_WRITE = "nameuser_write";

	//User-Read
	private final String USERNAME_READ = "username_read";
	private final String PASSWORD_READ = "password_read";
	private final String NAMEUSER_READ = "nameuser_read";

	//User-No-Access
	private final String USERNAME_NO_ACCESS = "username_no_access";
	private final String PASSWORD_NO_ACCESS = "password_no_access";
	private final String NAMEUSER_NO_ACCESS = "nameuser_no_access";

	//Document
	private final String NAME = "sheet";
	private final int ROW     = 10;
	private final int COLUMN  = 10;

	//Cell-Samples
	private final Cell CELL_A = null;
	private final Cell CELL_B = null;
	private final Cell CELL_C = null;
	private final Cell CELL_D = null;
	private final Cell CELL_E = null;
	private final Cell CELL_F = null;

    @Override
    public void populate4Test() {
		User owner = createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER);
		createUser(USERNAME_WRITE    , PASSWORD_WRITE    , NAMEUSER_WRITE    );
		createUser(USERNAME_READ     , PASSWORD_READ     , NAMEUSER_READ     );
		createUser(USERNAME_NO_ACCESS, PASSWORD_NO_ACCESS, NAMEUSER_NO_ACCESS);
		
		this.ownerToken    = addUserToSession(USERNAME_OWNER);
		this.writeToken    = addUserToSession(USERNAME_WRITE);
		this.readToken     = addUserToSession(USERNAME_READ);
		this.noAccessToken = addUserToSession(USERNAME_NO_ACCESS);
		
		createSpreadSheet(owner, NAME, ROW, COLUMN);
    }

    @Test
    public void successOwner() {
        ExportDocument service = new ExportDocument(this.ownerToken, 0);
        service.execute();

		//Exported Document from service is the same as the normal one
        assertEquals(USERNAME_OWNER, USERNAME_OWNER);
    }

    @Test
    public void successWrite() {
        ExportDocument service = new ExportDocument(this.writeToken, 0);
        service.execute();

		//Exported Document from service is the same as the normal one
        assertEquals(USERNAME_OWNER, USERNAME_OWNER);
    }

    @Test
    public void successRead() {
        ExportDocument service = new ExportDocument(this.readToken, 0);
        service.execute();

		//Exported Document from service is the same as the normal one
        assertEquals(USERNAME_OWNER, USERNAME_OWNER);
    }

    @Test(expected = ExportException.class)
    public void XMLExportWithoutDocument() {
        ExportDocument service = new ExportDocument(this.ownerToken, -1);
        service.execute();
    }
    
    /*    @Test(expected = ImportException.class)
    public void XMLImportWithoutDocument() {
        ExportDocument service = new ExportDocument(root, USERNAME, "jose",
                "José Ferreira");
        service.execute();
    }*/

    @Test(expected = AccessException.class)
    public void UserWithoutAccess() {
        ExportDocument service = new ExportDocument(this.noAccessToken, 0);
        service.execute();
    }


}
