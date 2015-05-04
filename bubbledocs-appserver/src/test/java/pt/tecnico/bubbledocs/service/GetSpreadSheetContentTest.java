package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.*;
import org.junit.Test;

import pt.tecnico.bubbledocs.exception.BubbleDocsException;
import pt.tecnico.bubbledocs.service.GetSpreadSheetContent;

public class GetSpreadSheetContentTest extends BubbleDocsServiceTest {
	
	/*
	 * Expected SpreadSheet
	 *  -----------------------
	 * | 1 |   | 6 |   |   | 10| 
	 * | 6 | 17| 2 | 9 | 10|276|
	 * |   |   | 0 | 0 |   |   |
	 *  -----------------------
	 */
	
    // the tokens
	private String userToken;
	private int docId;
	
	//User-Owner
	private final String USERNAME_OWNER = "owner";
	private final String PASSWORD_OWNER = "password_owner";
	private final String NAMEUSER_OWNER = "nameuser_owner";
	private final String EMAIL_OWNER = "email_owner";
	
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
		createUser(USERNAME_NO_ACCESS, PASSWORD_NO_ACCESS, NAMEUSER_NO_ACCESS, EMAIL_NO_ACCESS);
	
		this.docId = createSpreadSheet(createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER, EMAIL_OWNER),NAME, ROW, COLUMN).getId();
		this.userToken = addUserToSession(USERNAME_OWNER);
		
		AssignLiteralToCell serviceAssignLiteral;
		AssignReferenceToCell serviceAssignReference;
		
		try {
			serviceAssignLiteral = new AssignLiteralToCell
					(userToken, docId, "1;1", "1");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCell
					(userToken, docId, "1;3", "6");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCell
					(userToken, docId, "1;6", "10");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCell
					(userToken, docId, "2;2", "17");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCell
					(userToken, docId, "2;3", "2");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCell
					(userToken, docId, "2;4", "9");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCell
					(userToken, docId, "3;3", "0");
					serviceAssignLiteral.execute();
			serviceAssignLiteral = new AssignLiteralToCell
					(userToken, docId, "2;6", "276");
					serviceAssignLiteral.execute();
			serviceAssignReference = new AssignReferenceToCell
					(userToken, docId, "2;1", "1;3");
					serviceAssignReference.execute();
			serviceAssignReference = new AssignReferenceToCell
					(userToken, docId, "2;5", "1;6");
					serviceAssignReference.execute();
			serviceAssignReference = new AssignReferenceToCell
					(userToken, docId, "3;4", "3;3");
					serviceAssignReference.execute();
		} catch (BubbleDocsException ex) {
			System.out.println(ex.getMessage());
		}
    }
	
	@Test
	public void success() {
		GetSpreadSheetContent service = new GetSpreadSheetContent(userToken, docId);
		service.execute();
		content = service.getResult();
		
		for (int i=0; i<ROW; i++) {
			for (int j=0; j<COLUMN; j++) {
				assertEquals(expected[i][j], content[i][j]);
				//System.out.printf(content[i][j]+" | ");
			}
			//System.out.println();
		}
	}
}
