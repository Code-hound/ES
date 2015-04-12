package pt.tecnico.bubbledocs.service;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.core.WriteOnReadError;

// add needed import declarations

import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.domain.BubbleDocs;

/*
 * Added by Calisto
 */

import pt.tecnico.bubbledocs.exception.UnknownBubbleDocsUserException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.UserAlreadyExistsException;
import pt.tecnico.bubbledocs.exception.BubbleDocsException;

public class BubbleDocsServiceTest {
	
	protected String rootToken;

	@Before
	public void setUp() throws Exception {

		try {
			FenixFramework.getTransactionManager().begin();
			//loginRoot();
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
	public User createUser(String username, String password, String name, String email) {
		BubbleDocs bd = BubbleDocs.getInstance();

		User user = bd.createUser(username, password, name, email);
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

		List<SpreadSheet> sheetList = bd.getSpreadSheetByName(name);
		if (sheetList.size()>0)
				return sheetList.get(0);
		return null;
	}

	// returns the user registered in the application whose username is equal to
	// username
	User getUserFromUsername(String username) {
		BubbleDocs bd = BubbleDocs.getInstance();
		
		User user = bd.getUserByUsername(username);
		return user;
	}

	// put a user into session and returns the token associated to it
	String addUserToSession(String username) {
		BubbleDocs bd = BubbleDocs.getInstance();
		User user = bd.getUserByUsername(username);
		String userToken = bd.addUserToSession(user);
		return userToken;
	}

	// remove a user from session given its token
	void removeUserFromSession(String token) {
		BubbleDocs bd = BubbleDocs.getInstance();
		User user = bd.getUserLoggedInByToken(token);
		bd.removeUserFromSession(user);
	}

	// return the user registered in session whose token is equal to token
	User getUserFromSession(String token) {
		BubbleDocs bd = BubbleDocs.getInstance();
		User user = bd.getUserLoggedInByToken(token);
		return user;
	}

}
