package pt.tecnico.bubbledocs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.jdom2.Element;

import pt.tecnico.bubbledocs.domain.Cell;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.SpreadSheet;

import pt.tecnico.bubbledocs.exception.ExportException;
import pt.tecnico.bubbledocs.exception.ImportException;
import pt.tecnico.bubbledocs.exception.AccessException;

public class AssignLiteralToCellTest extends BubbleDocsServiceTest {

	// the tokens
	private String userToken;
	private String sheetId;
	private String root;
	private String ars;

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
	private final String USERNAME_INVALID = null;
	private final String PASSWORD_INVALID = null;
	private final String NAMEUSER_INVALID = null;
	private User INVALID;
	private String INVALID_TOKEN;

	// Document
	private final String NAME = "sheet";
	private final int ROW = 10;
	private final int COLUMN = 10;
	private SpreadSheet DOC;

	// Document-Invalid
	private final User OWNER_INVALID = null;
	private final String NAME_INVALID = null;
	private final int ROW_INVALID = -1;
	private final int COLUMN_INVALID = -1;
	private SpreadSheet DOC_INVALID;

	// Cell-Samples
	private final Cell CELL_A = null;
	private final Cell CELL_B = null;
	private final Cell CELL_C = null;
	private final Cell CELL_D = null;
	private final Cell CELL_E = null;
	private final Cell CELL_F = null;

	@Override
	public void populate4Test() {
		OWNER = createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER);
		WRITE = createUser(USERNAME_WRITE, PASSWORD_WRITE, NAMEUSER_WRITE);
		READ = createUser(USERNAME_READ, PASSWORD_READ, NAMEUSER_READ);
		NO_ACCESS = createUser(USERNAME_NO_ACCESS, PASSWORD_NO_ACCESS,
				NAMEUSER_NO_ACCESS);
		INVALID = createUser(USERNAME_INVALID, PASSWORD_INVALID,
				NAMEUSER_INVALID);

		OWNER_TOKEN = addUserToSession(USERNAME_OWNER);
		WRITE_TOKEN = addUserToSession(USERNAME_WRITE);
		READ_TOKEN = addUserToSession(USERNAME_READ);
		NO_ACCESS_TOKEN = addUserToSession(USERNAME_NO_ACCESS);
		INVALID_TOKEN = addUserToSession(USERNAME_INVALID);

		DOC = createSpreadSheet(OWNER, NAME, ROW, COLUMN);
		DOC_INVALID = createSpreadSheet(OWNER_INVALID, NAME_INVALID,
				ROW_INVALID, COLUMN_INVALID);
	}

}