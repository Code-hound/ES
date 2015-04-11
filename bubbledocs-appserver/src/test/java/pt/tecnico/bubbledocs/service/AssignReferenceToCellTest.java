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
	private User OWNER;
	private String OWNER_TOKEN;

	// User-Write
	private final String USERNAME_WRITE = "username_write";
	private final String PASSWORD_WRITE = "password_write";
	private final String NAMEUSER_WRITE = "nameuser_write";
	private User WRITE;
	private String WRITE_TOKEN;

	// User-Read
	private final String USERNAME_READ = "username_read";
	private final String PASSWORD_READ = "password_read";
	private final String NAMEUSER_READ = "nameuser_read";
	private User READ;
	private String READ_TOKEN;

	// User-No-Access
	private final String USERNAME_NO_ACCESS = "username_no_access";
	private final String PASSWORD_NO_ACCESS = "password_no_access";
	private final String NAMEUSER_NO_ACCESS = "nameuser_no_access";
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
		OWNER = createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER);
		WRITE = createUser(USERNAME_WRITE, PASSWORD_WRITE, NAMEUSER_WRITE);
		READ = createUser(USERNAME_READ, PASSWORD_READ, NAMEUSER_READ);
		NO_ACCESS = createUser(USERNAME_NO_ACCESS, PASSWORD_NO_ACCESS,
				NAMEUSER_NO_ACCESS);
		//INVALID = createUser(USERNAME_INVALID, PASSWORD_INVALID,
		//		NAMEUSER_INVALID);

		OWNER_TOKEN = addUserToSession(USERNAME_OWNER);
		WRITE_TOKEN = addUserToSession(USERNAME_WRITE);
		READ_TOKEN = addUserToSession(USERNAME_READ);
		NO_ACCESS_TOKEN = addUserToSession(USERNAME_NO_ACCESS);
		//INVALID_TOKEN = addUserToSession(USERNAME_INVALID);

		DOC = createSpreadSheet(OWNER, NAME, ROW_NUMBER, COLUMN_NUMBER);
		bd.addAccessToSpreadSheet(WRITE, DOC, "writer");
		//System.out.println("Test received permission level "+DOC.getUserPermissionLevel(USERNAME_OWNER));
		bd.addAccessToSpreadSheet(READ, DOC, "reader");
		/*
		Cell A = DOC.getCell(1,1);
		Cell B = DOC.getCell(2,2);
		Cell C = DOC.getCell(3,3);
		Cell D = DOC.getCell(4,4);
		Cell E = DOC.getCell(5,5);
		Cell F = DOC.getCell(6,6);
		*/
		//DOC_INVALID = createSpreadSheet(OWNER_INVALID, NAME_INVALID,
		//		ROW_INVALID, COLUMN_INVALID);
		
	}
	
	@Test 
	public void success() {
		//Owner assigns the reference "1;2" to cell A "1;1"
		AssignReferenceToCell service_owner = new AssignReferenceToCell
				(OWNER_TOKEN, DOC.getId(), "1;2", "1;1");
		service_owner.execute();
		
		//Writer assigns the reference "1;1" to cell B "2;2"
		/*AssignReferenceToCell service_writer = new AssignReferenceToCell
				(WRITE_TOKEN, DOC.getId(), "2;2", "1;1");
		service_writer.execute();
		*/
		assertEquals(service_owner.getResult(), "1;1");
		assertEquals(DOC.getCell(1,2).getValue(), "1;1");
		//assertEquals(service_writer.getResult(), "1;1");
		//assertEquals(DOC.getCell(2,2).getValue(), "1;1");
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