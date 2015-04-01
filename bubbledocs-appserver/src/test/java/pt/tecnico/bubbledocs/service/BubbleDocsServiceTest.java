package pt.tecnico.bubbledocs.service;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import org.junit.After;
import org.junit.Before;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.core.WriteOnReadError;

// add needed import declarations

import pt.tecnico.bubbledocs.domain.SpreadSheet;

/*
 * Added by Calisto
 */

import pt.tecnico.bubbledocs.domain.*;

import pt.tecnico.bubbledocs.exception.UserDoesNotExistException;
import pt.tecnico.bubbledocs.exception.UserIsNotRootException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;

public class BubbleDocsServiceTest {

	@Before
	public void setUp() throws Exception {

		try {
			FenixFramework.getTransactionManager().begin(false);
			populate4Test();
		} catch (WriteOnReadError | NotSupportedException | SystemException e1) {
			e1.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		try {
			FenixFramework.getTransactionManager().rollback();
		} catch (IllegalStateException | SecurityException | SystemException e) {
			e.printStackTrace();
		}
	}

	// should redefine this method in the subclasses if it is needed to specify
	// some initial state
	public void populate4Test() {
	}

	// auxiliary methods that access the domain layer and are needed in the test
	// classes
	// for defining the iniital state and checking that the service has the
	// expected behavior
	public User createUser(String username, String password, String name) {
		BubbleDocs bd = BubbleDocs.getInstance();

		User user = bd.createUser(username, password, name);
		return user;
	}

	public SpreadSheet createSpreadSheet(User user, String name, int row,
			int column) {
		BubbleDocs bd = BubbleDocs.getInstance();

		SpreadSheet spread = bd.createSpreadSheet(user, name, row, column);
		return spread;
	}

	// returns a spreadsheet whose name is equal to name
	public SpreadSheet getSpreadSheet(String name) {
		BubbleDocs bd = BubbleDocs.getInstance();

		SpreadSheet spread = bd.getSpreadSheetByName(name).get(0);
		return spread;
	}

	// returns the user registered in the application whose username is equal to
	// username
	User getUserFromUsername(String username) {
		BubbleDocs bd = BubbleDocs.getInstance();

		User user = bd.getUserByUserName(username);
		return user;
	}

	// put a user into session and returns the token associated to it
	String addUserToSession(String username) {
		return null;
	}

	// remove a user from session given its token
	void removeUserFromSession(String token) {
		// add code here
	}

	// return the user registered in session whose token is equal to token
	User getUserFromSession(String token) {
		// add code here
		return null;
	}

}
