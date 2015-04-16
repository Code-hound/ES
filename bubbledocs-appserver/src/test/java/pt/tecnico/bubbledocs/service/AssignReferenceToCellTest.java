package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.SpreadSheet;

import pt.tecnico.bubbledocs.exception.UserCantWriteException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.CellNotInSpreadSheetException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;

/* 
 * A testar:
 * Sucesso: utilizadores owner e writer tentam fazer assign
 * Falhanço: utilizadores reader, none e invalid tentam fazer assign
 * Falhanço: utilizador qualquer tenta fazer assign a uma sheet inexistente
 * Falhanço: utilizador com write/owner tenta fazer assign a uma cell inexistente
 */

public class AssignReferenceToCellTest extends BubbleDocsServiceTest {

	// User-Owner
	private final String USERNAME_OWNER = "username_owner";
	private final String PASSWORD_OWNER = "password_owner";
	private final String NAMEUSER_OWNER = "nameuser_owner";
	private final String EMAIL_OWNER = "email_owner";
	private User OWNER;
	private String OWNER_TOKEN;

	// User-Write
	private final String USERNAME_WRITE = "username_write";
	private final String PASSWORD_WRITE = "password_write";
	private final String NAMEUSER_WRITE = "nameuser_write";
	private final String EMAIL_WRITE = "email_write";
	private User WRITE;
	private String WRITE_TOKEN;

	// User-Read
	private final String USERNAME_READ = "username_read";
	private final String PASSWORD_READ = "password_read";
	private final String NAMEUSER_READ = "nameuser_read";
	private final String EMAIL_READ = "email_read";
	private User READ;
	private String READ_TOKEN;

	// User-No-Access
	private final String USERNAME_NO_ACCESS = "username_no_access";
	private final String PASSWORD_NO_ACCESS = "password_no_access";
	private final String NAMEUSER_NO_ACCESS = "nameuser_no_access";
	private final String EMAIL_NO_ACCESS = "email_no_access";
	private User NO_ACCESS;
	private String NO_ACCESS_TOKEN;

	// User-Invalid
	private String INVALID_TOKEN;

	// Document
	private final String NAME = "sheet";
	private final int ROW_NUMBER = 10;
	private final int COLUMN_NUMBER = 10;
	private SpreadSheet DOC;

	@Override
	public void populate4Test() {
		BubbleDocs bd = BubbleDocs.getInstance();
		OWNER = createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER, EMAIL_OWNER);
		WRITE = createUser(USERNAME_WRITE, PASSWORD_WRITE, NAMEUSER_WRITE, EMAIL_WRITE);
		READ = createUser(USERNAME_READ, PASSWORD_READ, NAMEUSER_READ, EMAIL_READ);
		NO_ACCESS = createUser(USERNAME_NO_ACCESS, PASSWORD_NO_ACCESS, NAMEUSER_NO_ACCESS, EMAIL_NO_ACCESS);
		//INVALID = createUser(USERNAME_INVALID, PASSWORD_INVALID,
		//		NAMEUSER_INVALID);

		OWNER_TOKEN = addUserToSession(USERNAME_OWNER);
		WRITE_TOKEN = addUserToSession(USERNAME_WRITE);
		READ_TOKEN = addUserToSession(USERNAME_READ);
		NO_ACCESS_TOKEN = addUserToSession(USERNAME_NO_ACCESS);
		//INVALID_TOKEN = addUserToSession(USERNAME_INVALID);

		DOC = createSpreadSheet(OWNER, NAME, ROW_NUMBER, COLUMN_NUMBER);
		bd.addAccessToSpreadSheet(WRITE, DOC, "writer");
		bd.addAccessToSpreadSheet(READ, DOC, "reader");
		
	}
	
	@Test 
	public void success() {
		//Owner assigns to cell A "1;1" a reference to cell "1;2" 
		AssignReferenceToCell service_owner = new AssignReferenceToCell
				(OWNER_TOKEN, DOC.getId(), "1;1", "1;2");
		AssignLiteralToCell service_aux = new AssignLiteralToCell
				(OWNER_TOKEN, DOC.getId(), "1;2", "4");
		service_owner.execute();
		service_aux.execute();
		
		//Writer assigns the reference "1;1" to cell B "2;2"
		AssignReferenceToCell service_writer = new AssignReferenceToCell
				(WRITE_TOKEN, DOC.getId(), "2;2", "1;1");
		service_writer.execute();
		
		//Checks if the value returned by the service is the referenced cell ID
		assertEquals("1;2", service_owner.getResult());
		//Checks if the value in 1;1 is the value in 1;2
		assertEquals(4, DOC.getCell(1,1).getValue());
		
		assertEquals(service_writer.getResult(), "1;1");
		assertEquals(DOC.getCell(2,2).getValue(), 4);
	}
	
	@Test (expected = UserCantWriteException.class)
	public void assignWithuNoAccessUser() {
		AssignReferenceToCell service_unauthorized = new AssignReferenceToCell
				(NO_ACCESS_TOKEN, DOC.getId(), "1;1", "1;2");
		service_unauthorized.execute();
	}
	
	@Test (expected = UserCantWriteException.class)
	public void assignWithReader() {
		AssignReferenceToCell service_reader = new AssignReferenceToCell
				(READ_TOKEN, DOC.getId(), "1;1", "1;2");
		service_reader.execute();
	}
	
	@Test (expected = UserNotInSessionException.class)
	public void assignWithInvalidUser() {
		AssignReferenceToCell service_invalid = new AssignReferenceToCell
				(INVALID_TOKEN, DOC.getId(), "1;1", "1;2");
		service_invalid.execute();
	}
	
	@Test (expected = DocumentDoesNotExistException.class)
	public void assignToInvalidSpreadSheet() {
		AssignReferenceToCell service_invalid_sheet = new AssignReferenceToCell 
				(OWNER_TOKEN, 17000, "1;1", "1;2");
		service_invalid_sheet.execute();
	}
	
	@Test (expected = CellNotInSpreadSheetException.class)
	public void assignToOutOfRangeCell() {
		AssignReferenceToCell service_invalid_cell = new AssignReferenceToCell 
				(OWNER_TOKEN, DOC.getId(),"1;2" , "20;40");
		service_invalid_cell.execute();
	}
}