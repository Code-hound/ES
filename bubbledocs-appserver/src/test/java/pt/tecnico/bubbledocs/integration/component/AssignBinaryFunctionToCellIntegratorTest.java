package pt.tecnico.bubbledocs.integration.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.InvalidBinaryFunctionException;
import pt.tecnico.bubbledocs.exception.InvalidValueException;
import pt.tecnico.bubbledocs.exception.ProtectedCellException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.CellNotInSpreadSheetException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;
import pt.tecnico.bubbledocs.integration.AssignBinaryFunctionToCellIntegrator;
import pt.tecnico.bubbledocs.integration.AssignLiteralToCellIntegrator;
import pt.tecnico.bubbledocs.integration.AssignReferenceToCellIntegrator;
import pt.tecnico.bubbledocs.service.AssignBinaryFunctionToCellService;
import pt.tecnico.bubbledocs.service.AssignLiteralToCellService;
import pt.tecnico.bubbledocs.service.AssignReferenceToCellService;


/* 
 * A testar:
 * Sucesso:  utilizadores owner e writer tentam fazer assign
 * Falhanço: utilizadores reader, none e invalid tentam fazer assign
 * Falhanço: utilizador qualquer tenta fazer assign a uma sheet inexistente
 * Falhanço: utilizador com write/owner tenta fazer assign a uma cell inexistente
 * Falhanço: utilizador tenta fazer assign a celula fora da spreadsheet
 * Falhanço: utilizador indica funcao binaria que nao existe
 */

public class AssignBinaryFunctionToCellIntegratorTest extends BubbleDocsIntegratorTest {

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
		bd.addAccessToSpreadSheet(READ, DOC, "reader");
		
	}
	
	@Test 
	public void successADD() {
		//Owner assigns a binaryFunction =ADD(1,1;2) to cell "1;1" 
		AssignLiteralToCellIntegrator service_aux1 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "1;2", "444");
		AssignBinaryFunctionToCellIntegrator service_owner1 = new AssignBinaryFunctionToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "1;1", "=ADD(1,1;2)");
		service_aux1.execute();
		service_owner1.execute();
			//Checks if the value returned by the service is the value assigned
		assertEquals("445",service_owner1.getResult().toString());
			//Checks if the value in cell 1;1 is the value in assigned
		assertEquals(445,DOC.getCell(1,1).getValue());
	}	
		
	@Test 
	public void successADD2() {
		//Owner assigns a binaryFunction =ADD(1,1;2) to cell "1;1" 
		AssignLiteralToCellIntegrator service_aux1 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "1;2", "444");
		AssignLiteralToCellIntegrator service_aux2 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "1;3", "-44");
		AssignBinaryFunctionToCellService service_owner1 = new AssignBinaryFunctionToCellService
				(OWNER_TOKEN, DOC.getId(), "1;1", "=ADD(1;3,1;2)");
		service_aux1.execute();
		service_aux2.execute();
		service_owner1.execute();
			//Checks if the value returned by the service is the value assigned
		assertEquals("400",service_owner1.getResult().toString());
			//Checks if the value in cell 1;1 is the value in assigned
		assertEquals(400,DOC.getCell(1,1).getValue());
	}	
		
		
	@Test 
	public void successSUB() {	
		//Owner assigns a binaryFunction =SUB(1,1;6) to cell "5;5" 
		AssignLiteralToCellIntegrator service_aux2 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "1;6", "2");
		AssignBinaryFunctionToCellIntegrator service_owner2 = new AssignBinaryFunctionToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "5;5", "=SUB(7,1;6)");
		service_aux2.execute();
		service_owner2.execute();
			//Checks if the value returned by the service is the value assigned

		assertEquals("5",service_owner2.getResult());
			//Checks if the value in cell 5;5 is the value in assigned
		assertEquals(5,DOC.getCell(5,5).getValue());
	}
		
		
		
	@Test 
	public void successMUL() {
		//Writer assigns a binaryFunction =MUL(3,9;2) to cell "10;7"
		AssignLiteralToCellIntegrator service_aux3 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "9;2", "4");
		AssignBinaryFunctionToCellIntegrator service_writer3 = new AssignBinaryFunctionToCellIntegrator
				(WRITE_TOKEN, DOC.getId(), "10;7", "=MUL(3,9;2)");
		service_aux3.execute();
		service_writer3.execute();
			//Checks if the value returned by the service is the value assigned

		assertEquals("12",service_writer3.getResult());
			//Checks if the value in cell 10;7 is the value in assigned
		assertEquals(12,DOC.getCell(10,7).getValue());
	}
		
	@Test 
	public void successMULby0() {
		//Writer assigns a binaryFunction =MUL(3,9;2) to cell "10;7"
		AssignBinaryFunctionToCellIntegrator service_writer3 = new AssignBinaryFunctionToCellIntegrator
				(WRITE_TOKEN, DOC.getId(), "10;7", "=MUL(3,0)");
		service_writer3.execute();
			//Checks if the value returned by the service is the value assigned
		assertEquals("0",service_writer3.getResult());
			//Checks if the value in cell 10;7 is the value in assigned
		assertEquals(0,DOC.getCell(10,7).getValue());
	}	
			
	@Test 
	public void successDIV() {	
		//Writer assigns a binaryFunction =DIV(2,10;10) to cell "7;10"
		AssignLiteralToCellIntegrator service_aux5 = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "9;2", "4");
		AssignReferenceToCellIntegrator service_aux4 = new AssignReferenceToCellIntegrator
				(WRITE_TOKEN, DOC.getId(), "10;10", "9;2");
		AssignBinaryFunctionToCellIntegrator service_writer4 = new AssignBinaryFunctionToCellIntegrator
				(WRITE_TOKEN, DOC.getId(), "7;10", "=DIV(4,10;10)");
		service_aux5.execute();
		service_aux4.execute();
		service_writer4.execute();
			//Checks if the value returned by the service is the value assigned
		assertEquals("1",service_writer4.getResult());
			//Checks if the value in cell 7;10 is the value in assigned
		assertEquals(1,DOC.getCell(7,10).getValue());
	}
	
	@Test (expected = InvalidAccessException.class)
	public void assignWithuNoAccessUser() {
		AssignLiteralToCellIntegrator service_error = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;2", "4");
		AssignBinaryFunctionToCellIntegrator service_unauthorized = new AssignBinaryFunctionToCellIntegrator
				(NO_ACCESS_TOKEN, DOC.getId(), "1;1", "=ADD(1,3;2)");
		service_error.execute();
		service_unauthorized.execute();
	}
	
	@Test (expected = InvalidAccessException.class)
	public void assignWithReader() {
		AssignLiteralToCellIntegrator service_error = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;2", "4");
		AssignBinaryFunctionToCellService service_reader = new AssignBinaryFunctionToCellService
				(READ_TOKEN, DOC.getId(), "1;1", "=ADD(1,3;2)");
		service_error.execute();
		service_reader.execute();
	}
	
	@Test (expected = UserNotInSessionException.class)
	public void assignWithInvalidUser() {
		AssignLiteralToCellIntegrator service_error = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;2", "4");
		AssignBinaryFunctionToCellIntegrator service_invalid = new AssignBinaryFunctionToCellIntegrator
				(INVALID_TOKEN, DOC.getId(), "1;1", "=ADD(1,3;2)");
		service_error.execute();
		service_invalid.execute();
	}
	
	@Test (expected = DocumentDoesNotExistException.class)
	public void assignToInvalidSpreadSheet() {
		AssignLiteralToCellIntegrator service_error = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;2", "4");
		AssignBinaryFunctionToCellIntegrator service_invalid_sheet = new AssignBinaryFunctionToCellIntegrator 
				(OWNER_TOKEN, 17000, "1;1", "=ADD(1,3;2)");
		service_error.execute();
		service_invalid_sheet.execute();
	}
	
	@Test (expected = CellNotInSpreadSheetException.class)
	public void assignToOutOfRangeCell() {
		AssignLiteralToCellIntegrator service_error = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;2", "4");
		AssignBinaryFunctionToCellIntegrator service_invalid_cell = new AssignBinaryFunctionToCellIntegrator 
				(OWNER_TOKEN, DOC.getId(),"20;20" , "=ADD(1,3;2)");
		service_error.execute();
		service_invalid_cell.execute();
	}

	
	@Test (expected = InvalidValueException.class)
	public void assignDividedByZero() {
		AssignLiteralToCellIntegrator service_error = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;2", "4");
		AssignBinaryFunctionToCellIntegrator service_invalid_cell = new AssignBinaryFunctionToCellIntegrator 
				(OWNER_TOKEN, DOC.getId(),"1;1" , "=DIV(3;2,0)");
		service_error.execute();
		service_invalid_cell.execute();
		DOC.getCell(1,1).getValue();
	}
	
	@Test (expected = InvalidBinaryFunctionException.class)
	public void assignNonExistentBinaryFunction() {
		AssignLiteralToCellIntegrator service_error = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;2", "4");
		AssignBinaryFunctionToCellIntegrator service_invalid_cell = new AssignBinaryFunctionToCellIntegrator 
				(OWNER_TOKEN, DOC.getId(),"1;1" , "=ZEF(3;2,0)");
		service_error.execute();
		service_invalid_cell.execute();
		DOC.getCell(1,1).getValue();
	}
	
	@Test (expected = ProtectedCellException.class)
	public void assignToCellProtected() {
		AssignLiteralToCellIntegrator service_error = new AssignLiteralToCellIntegrator
				(OWNER_TOKEN, DOC.getId(), "3;2", "4");
		AssignBinaryFunctionToCellIntegrator service_invalid_cell = new AssignBinaryFunctionToCellIntegrator
				(OWNER_TOKEN, DOC.getId(),"4;4" , "=ADD(1,3;2)");
		service_error.execute();
		DOC.getCell(4,4).toogleProtection();
		service_invalid_cell.execute();
	}
	
	
}
	