package pt.tecnico.bubbledocs.integration.component;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.User;

import pt.tecnico.bubbledocs.service.AssignLiteralToCellService;
import pt.tecnico.bubbledocs.service.AssignReferenceToCellService;

import pt.tecnico.bubbledocs.integration.GetSpreadSheetContentIntegrator;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.exception.DocumentDoesNotExistException;
import pt.tecnico.bubbledocs.exception.InvalidAccessException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;

public class GetSpreadSheetContentIntegratorTest extends BubbleDocsIntegratorTest {
	
	/*
	 * Expected SpreadSheet
	 *  -----------------------
	 * | 1 |   | 6 |   |   | 10| 
	 * | 6 | 17| 2 | 9 | 10|276|
	 * |   |   | 0 | 0 |   |   |
	 *  -----------------------
	 */
	
    // the tokens
	private String TOKEN_OWNER;
	private String TOKEN_NO_ACCESS;
	private String TOKEN_READER;
	
	private int docId;
	
	//User-Owner
	private final String USERNAME_OWNER = "owner";
	private final String PASSWORD_OWNER = "password_owner";
	private final String NAMEUSER_OWNER = "nameuser_owner";
	private final String EMAIL_OWNER = "email_owner";
	
	//User-Reader
	private final String USERNAME_READER = "reader";
	private final String PASSWORD_READER = "password_reader";
	private final String NAMEUSER_READER = "nameuser_reader";
	private final String EMAIL_READER = "email_reader";
	
	//User-No-Access
	private final String USERNAME_NO_ACCESS = "noaccess";
	private final String PASSWORD_NO_ACCESS = "password_no_access";
	private final String NAMEUSER_NO_ACCESS = "nameuser_no_access";
	private final String EMAIL_NO_ACCESS = "email_no_access";
	
	//Document
	private final String NAME = "sheet";
	private final int ROW     = 3;
	private final int COLUMN  = 6;
	private static String[][] content;
	private static String[][] expected = 
		{{"1", "", "6", "", "", "10"},
		{"6", "17", "2", "9", "10", "276"},
		{"", "", "0", "0", "", ""}};
	
	@Override
    public void populate4Test() {

		User owner = createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER, EMAIL_OWNER);
		User no_access = createUser(USERNAME_NO_ACCESS, PASSWORD_NO_ACCESS, NAMEUSER_NO_ACCESS, EMAIL_NO_ACCESS);
		User reader = createUser(USERNAME_READER, PASSWORD_READER, NAMEUSER_READER, EMAIL_READER);
		
		SpreadSheet sheet = createSpreadSheet(owner,NAME, ROW, COLUMN);
		this.docId = sheet.getId();
		addAccess(reader, sheet, "writer");
		this.TOKEN_OWNER = addUserToSession(USERNAME_OWNER);
		this.TOKEN_NO_ACCESS = addUserToSession(USERNAME_NO_ACCESS);
		this.TOKEN_READER= addUserToSession(USERNAME_READER);
		
		AssignLiteralToCellService serviceAssignLiteral;
		AssignReferenceToCellService serviceAssignReference;
		
		try {
			serviceAssignLiteral = new AssignLiteralToCellService
					(TOKEN_OWNER, docId, "1;1", "1");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCellService
					(TOKEN_OWNER, docId, "1;3", "6");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCellService
					(TOKEN_OWNER, docId, "1;6", "10");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCellService
					(TOKEN_OWNER, docId, "2;2", "17");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCellService
					(TOKEN_OWNER, docId, "2;3", "2");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCellService
					(TOKEN_OWNER, docId, "2;4", "9");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCellService
					(TOKEN_OWNER, docId, "3;3", "0");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCellService
					(TOKEN_OWNER, docId, "2;6", "276");
					serviceAssignLiteral.execute();
			serviceAssignReference = new AssignReferenceToCellService
					(TOKEN_OWNER, docId, "2;1", "1;3");
					serviceAssignReference.execute();
			serviceAssignReference = new AssignReferenceToCellService
					(TOKEN_OWNER, docId, "2;5", "1;6");
					serviceAssignReference.execute();
			serviceAssignReference = new AssignReferenceToCellService
					(TOKEN_OWNER, docId, "3;4", "3;3");
					serviceAssignReference.execute();
		} catch (BubbleDocsException ex) {
			System.out.println(ex.getMessage());
		}

    }
	
	@Test
	public void success() {

		GetSpreadSheetContentIntegrator integrator = new GetSpreadSheetContentIntegrator
				(TOKEN_READER, docId);
		integrator.execute();
		content = integrator.getResult();
		
		for (int i=0; i<ROW; i++) {
			for (int j=0; j<COLUMN; j++) {
				assertEquals(expected[i][j], content[i][j]);
			}
		}

	}

	@Test (expected = UserNotInSessionException.class)
	public void invalidUser() {

		GetSpreadSheetContentIntegrator integrator = new GetSpreadSheetContentIntegrator
				("", docId);
		integrator.execute();

	}

	@Test (expected = DocumentDoesNotExistException.class)
	public void invalidDocument() {

		GetSpreadSheetContentIntegrator integrator = new GetSpreadSheetContentIntegrator
				(TOKEN_OWNER, -1);
		integrator.execute();

	}

	@Test (expected = InvalidAccessException.class)
	public void invalidAccess() {

		GetSpreadSheetContentIntegrator integrator = new GetSpreadSheetContentIntegrator
				(TOKEN_NO_ACCESS, docId);
		integrator.execute();

	}

}
