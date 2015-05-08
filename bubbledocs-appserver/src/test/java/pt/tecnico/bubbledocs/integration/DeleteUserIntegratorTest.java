package pt.tecnico.bubbledocs.integration;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import pt.tecnico.bubbledocs.domain.User;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.exception.UnauthorizedOperationException;
import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.UnavailableServiceException;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;

// add needed import declarations

public class DeleteUserIntegratorTest extends BubbleDocsIntegratorTest {

    private static final String USERNAME_TO_DELETE = "smf";
    private static final String USERNAME = "ars";
    private static final String PASSWORD = "ars";
    private static final String ROOT_USERNAME = "root";
    private static final String USERNAME_DOES_NOT_EXIST = "no-one";
    private static final String SPREADSHEET_NAME = "spread";

    // the tokens for user root
    private String root;

    @Override
    public void populate4Test() {
        createUser(USERNAME, PASSWORD, "António Rito Silva", "email@email.email");
        User smf = createUser(USERNAME_TO_DELETE, "smf", "Sérgio Fernandes", "email@email.email");
        createSpreadSheet(smf, USERNAME_TO_DELETE, 20, 20);

        root = addUserToSession(ROOT_USERNAME);
    };

    @Test
    public void success() {
        RemoveUserIntegrator integration = new RemoveUserIntegrator(root, USERNAME_TO_DELETE);
        integration.execute();

        boolean deleted = getUserFromUsername(USERNAME_TO_DELETE) == null;

        assertTrue("user was not deleted", deleted);

        assertNull("Spreadsheet was not deleted",
                getSpreadSheet(SPREADSHEET_NAME));
    }

    @Test(expected = UnauthorizedOperationException.class)
    public void notRootUser() {
        String ars = addUserToSession(USERNAME);
        new RemoveUserIntegrator(ars, USERNAME_TO_DELETE).execute();
    }

    @Test(expected = UserNotInSessionException.class)
    public void rootNotInSession() {
        removeUserFromSession(root);

        new RemoveUserIntegrator(root, USERNAME_TO_DELETE).execute();
    }

    @Test(expected = UserNotInSessionException.class)
    public void notInSessionAndNotRoot() {
        String ars = addUserToSession(USERNAME);
        removeUserFromSession(ars);

        new RemoveUserIntegrator(ars, USERNAME_TO_DELETE).execute();
    }

    @Test(expected = UserNotInSessionException.class)
    public void accessUserDoesNotExist() {
        new RemoveUserIntegrator(USERNAME_DOES_NOT_EXIST, USERNAME_TO_DELETE).execute();
    }

    @Test(expected = UnavailableServiceException.class)
    public void InvalidService() {
    	new MockUp<IDRemoteServices>() {
    		@Mock
    		public void removeUser(String username)
    				throws LoginBubbleDocsException, RemoteInvocationException {
    			throw new RemoteInvocationException();
    		}
    	};
    	success();
    }

}
