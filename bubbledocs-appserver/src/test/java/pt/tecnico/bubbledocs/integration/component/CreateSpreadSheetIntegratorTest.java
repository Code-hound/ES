package pt.tecnico.bubbledocs.integration.component;
 
import static org.junit.Assert.assertEquals;
 
import org.junit.Test;

import pt.tecnico.bubbledocs.integration.CreateSpreadSheetIntegrator;

import pt.tecnico.bubbledocs.exception.UserNotInSessionException;
import pt.tecnico.bubbledocs.exception.UserAlreadyHasThisDocumentException;

import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.domain.SpreadSheet;
import pt.tecnico.bubbledocs.domain.User;
 
public class CreateSpreadSheetIntegratorTest extends BubbleDocsIntegratorTest {
       
        /*
         *
         * author: Aline Caliente
         * author: Luis Ribeiro Gomes
         */
       
    // the tokens
        private String TOKEN_OWNER;
        private User owner;
               
        //User-Owner
        private final String USERNAME_OWNER     = "owner";
        private final String PASSWORD_OWNER     = "password_owner";
        private final String NAMEUSER_OWNER     = "nameuser_owner";
        private final String EMAIL_OWNER        = "email_owner";
 
        private final int ROWS          = 3;
        private final int COLUMNS       = 6;
        private final String NAME       = "sheet";
       
        @Override
        public void populate4Test() {
               
                owner = createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER, EMAIL_OWNER);
                this.TOKEN_OWNER = addUserToSession(USERNAME_OWNER);           
               
        }
 
        @Test
        public void success() {

                CreateSpreadSheetIntegrator integration = new CreateSpreadSheetIntegrator(TOKEN_OWNER, NAME, ROWS, COLUMNS);
                integration.execute();
               
                SpreadSheet sheet = BubbleDocs.getInstance().getSpreadSheetByOwnerAndName(owner, NAME);
                assertEquals(USERNAME_OWNER, sheet.getOwnerUsername());
                assertEquals(NAME          , sheet.getSpreadSheetName());
                assertEquals(ROWS          , sheet.getNumberRows());
                assertEquals(COLUMNS       , sheet.getNumberColumns());

        }
        
        @Test(expected = UserNotInSessionException.class)
        public void InvalidUser() {

                CreateSpreadSheetIntegrator integration = new CreateSpreadSheetIntegrator("error", NAME, ROWS, COLUMNS);
                integration.execute();

        }
        
        @Test(expected = UserAlreadyHasThisDocumentException.class)
        public void DocumentAlreadyExists() {

                CreateSpreadSheetIntegrator integration = new CreateSpreadSheetIntegrator(TOKEN_OWNER, NAME, ROWS, COLUMNS);
                integration.execute();
                integration.execute();

        }
       
}