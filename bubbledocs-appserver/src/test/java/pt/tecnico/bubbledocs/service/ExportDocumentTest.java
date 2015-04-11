package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.jdom2.output.XMLOutputter;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class ExportDocumentTest extends BubbleDocsServiceTest {

	private static XMLOutputter xml = new XMLOutputter();
	
    // the tokens
	private String ownerToken;
	private String noAccessToken;
	private SpreadSheet sheet;
	private int sheetId;
	private byte[] result;

	//User-Owner
	private final String USERNAME_OWNER = "username_owner";
	private final String PASSWORD_OWNER = "password_owner";
	private final String NAMEUSER_OWNER = "nameuser_owner";

	//User-No-Access
	private final String USERNAME_NO_ACCESS = "username_no_access";
	private final String PASSWORD_NO_ACCESS = "password_no_access";
	private final String NAMEUSER_NO_ACCESS = "nameuser_no_access";

	//Document
	private final String NAME = "sheet";
	private final int ROW     = 10;
	private final int COLUMN  = 10;
	private final int SHEETID_INVALID = -1;
	private final String USERTOKEN_INVALID = "error";

    @Override
    public void populate4Test() {
		User owner = createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER);
		createUser(USERNAME_NO_ACCESS, PASSWORD_NO_ACCESS, NAMEUSER_NO_ACCESS);
	
		this.ownerToken    = addUserToSession(USERNAME_OWNER);
		this.noAccessToken = addUserToSession(USERNAME_NO_ACCESS);
		
		this.sheet   = createSpreadSheet(owner, NAME, ROW, COLUMN);
		this.sheetId = this.sheet.getId();
		try {
			this.result  = xml.outputString(this.sheet.exportToXML()).getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
		}
    }

    @Test
    public void success() {
        ExportDocument service = new ExportDocument(this.ownerToken, this.sheetId);
        service.execute();
        assertEquals(service.getResult(), this.result);
    }

	@Test (expected = UserNotInSessionException.class)
    public void InvalidUser() {
        ExportDocument service = new ExportDocument(USERTOKEN_INVALID, this.sheetId);
        service.execute();
    }
    
    @Test(expected = DocumentDoesNotExistException.class)
    public void InvalidDocument() {
        ExportDocument service = new ExportDocument(this.ownerToken, SHEETID_INVALID);
        service.execute();
    }

    @Test(expected = InvalidAccessException.class)
    public void InvalidAccess() {
        ExportDocument service = new ExportDocument(this.noAccessToken, this.sheetId);
        service.execute();
    }


}
