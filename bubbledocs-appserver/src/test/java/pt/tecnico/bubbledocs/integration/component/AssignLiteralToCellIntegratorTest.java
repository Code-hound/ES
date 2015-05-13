package pt.tecnico.bubbledocs.integration.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.CellNotInSpreadSheetException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;
import pt.tecnico.bubbledocs.integration.AssignLiteralToCellIntegrator;

/* 
 * A testar:
 * Sucesso: utilizadores owner e writer tentam fazer assign
 * Falhanço: utilizadores reader, none e invalid tentam fazer assign
 * Falhanço: utilizador qualquer tenta fazer assign a uma sheet inexistente
 * Falhanço: utilizador com write/owner tenta fazer assign a uma cell inexistente
 */

public class AssignLiteralToCellIntegratorTest extends BubbleDocsIntegratorTest {

	// User-Owner
	private final String USERNAME_OWNER = "owner";
	private final String PASSWORD_OWNER = "password_owner";
	private final String NAMEUSER_OWNER = "nameuser_owner";
	private final String EMAIL_OWNER = "email_owner";
	private User OWNER;
	private String OWNER_TOKEN;

	// User-Write
	private final String USERNAME_WRITE = "write";
	private final String PASSWORD_WRITE = "password_write";
	private final String NAMEUSER_WRITE = "nameuser_write";
	private final String EMAIL_WRITE = "email_write";
	private User WRITE;
	private String WRITE_TOKEN;

	// User-Read
	private final String USERNAME_READ = "read";
	private final String PASSWORD_READ = "password_read";
	private final String NAMEUSER_READ = "nameuser_read";
	private final String EMAIL_READ = "email_read";
	private User READ;
	private String READ_TOKEN;

	// User-No-Access
	private final String USERNAME_NO_ACCESS = "noaccess";
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
		//Owner assigns the value 5 to cell "10;10"
		AssignLiteralToCellIntegrator integration_owner = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "10;10", "5");
		integration_owner.execute();
		assertEquals("5",integration_owner.getResult());
		assertEquals(5,DOC.getCell(10,10).getValue());
		
		//Owner assigns the value 9 to cell "10;6"
		AssignLiteralToCellIntegrator integration_owner3 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "10;6", "9");
		integration_owner3.execute();
		assertEquals("9",integration_owner3.getResult());
		assertEquals(9,DOC.getCell(10,6).getValue());
		
		//Owner assigns the value 3 to cell "3;10"
		AssignLiteralToCellIntegrator integration_owner2 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;10", "3");
		integration_owner2.execute();
		assertEquals("3",integration_owner2.getResult());
		assertEquals(3,DOC.getCell(3,10).getValue());
	
		//Writer assigns the value 7 to cell "2;2"
		AssignLiteralToCellIntegrator integration_writer = new AssignLiteralToCellIntegrator
				(WRITE_TOKEN, DOC.getId(), "2;2", "7");
		integration_writer.execute();
		assertEquals("7",integration_writer.getResult());
		assertEquals(7,DOC.getCell(2,2).getValue());
		
		//Owner assigns the value -2 to cell "1;7"
		AssignLiteralToCellIntegrator integration_owner4 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "1;7", "-2");
		integration_owner4.execute();
		assertEquals("-2", integration_owner4.getResult());
		assertEquals(-2, DOC.getCell(1,7).getValue());
		
		//Writer assigns the value 23 to cell "2;2"
		AssignLiteralToCellIntegrator integration_writer1 = new AssignLiteralToCellIntegrator
				(WRITE_TOKEN, DOC.getId(), "2;2", "23");
		integration_writer1.execute();
		assertEquals("23", integration_writer1.getResult());
		assertEquals(23, DOC.getCell(2,2).getValue());
	}
	
	@Test (expected = InvalidAccessException.class)
	public void assignWithuNoAccessUser() {
		AssignLiteralToCellIntegrator integration_unauthorized = new AssignLiteralToCellIntegrator
				(NO_ACCESS_TOKEN, DOC.getId(), "1;1", "5");
		integration_unauthorized.execute();
	}
	
	@Test (expected = InvalidAccessException.class)
	public void assignWithReader() {
		AssignLiteralToCellIntegrator integration_reader = new AssignLiteralToCellIntegrator
				(READ_TOKEN, DOC.getId(), "1;1", "5");
		integration_reader.execute();
	}
	
	@Test (expected = UserNotInSessionException.class)
	public void assignWithInvalidUser() {
		AssignLiteralToCellIntegrator integration_invalid = new AssignLiteralToCellIntegrator
				(INVALID_TOKEN, DOC.getId(), "1;1", "5");
		integration_invalid.execute();
	}
	
	@Test (expected = DocumentDoesNotExistException.class)
	public void assignToInvalidSpreadSheet() {
		AssignLiteralToCellIntegrator integration_invalid_sheet = new AssignLiteralToCellIntegrator 
				(OWNER_TOKEN, 17000, "1;1", "5");
		integration_invalid_sheet.execute();
	}
	
	@Test (expected = CellNotInSpreadSheetException.class)
	public void assignToOutOfRangeCell() {
		AssignLiteralToCellIntegrator integration_invalid_cell = new AssignLiteralToCellIntegrator 
				(OWNER_TOKEN, DOC.getId(), "20;40", "5");
		integration_invalid_cell.execute();
	}
	
	@Test (expected = CellNotInSpreadSheetException.class)
	public void assignToOutOfRangeCell2() {
		AssignLiteralToCellIntegrator integration_invalid_cell = new AssignLiteralToCellIntegrator 
				(OWNER_TOKEN, DOC.getId(), "0;0", "5");
		integration_invalid_cell.execute();
	}
	
	@Test (expected = CellNotInSpreadSheetException.class)
	public void assignToOutOfRangeCell3() {
		AssignLiteralToCellIntegrator integration_invalid_cell = new AssignLiteralToCellIntegrator 
				(OWNER_TOKEN, DOC.getId(), "11;11", "5");
		integration_invalid_cell.execute();
	}
	
}
