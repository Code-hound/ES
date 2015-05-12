//package pt.tecnico.bubbledocs.service;
// 
//import static org.junit.Assert.assertEquals;
//import mockit.Mock;
//import mockit.MockUp;
// 
//import org.junit.Test;
// 
//import pt.tecnico.bubbledocs.domain.BubbleDocs;
//import pt.tecnico.bubbledocs.domain.SpreadSheet;
//import pt.tecnico.bubbledocs.domain.User;
// 
//public class CreateSpreadSheetServiceTest extends BubbleDocsServiceTest {
//       
//        /*
//         *
//         * author: Aline Caliente
//         */
//       
//    // the tokens
//        private String TOKEN_OWNER;
//        private User owner;
//               
//        //User-Owner
//        private final String USERNAME_OWNER     = "owner";
//        private final String PASSWORD_OWNER     = "password_owner";
//        private final String NAMEUSER_OWNER     = "nameuser_owner";
//        private final String EMAIL_OWNER                = "email_owner";
// 
//        private final int ROWS          = 3;
//        private final int COLUMNS       = 6;
//        private final String NAME       = "sheet";
//       
//        @Override
//        public void populate4Test() {
//               
//                owner = createUser(USERNAME_OWNER, PASSWORD_OWNER, NAMEUSER_OWNER, EMAIL_OWNER);
//                this.TOKEN_OWNER = addUserToSession(USERNAME_OWNER);           
//               
//        }
// 
//        @Test
//        public void success() {
//                CreateSpreadSheetService service = new CreateSpreadSheetService(TOKEN_OWNER, NAME, ROWS, COLUMNS);
//                service.execute();
//               
//                SpreadSheet sheet = BubbleDocs.getInstance().getSpreadSheetByOwnerAndName(owner, NAME);
//                assertEquals(TOKEN_OWNER, FIXME);
//                assertEquals(NAME, FIXME);
//                assertEquals(ROWS, FIXME);
//                assertEquals(COLUMNS, FIXME);
//               
//        }
//       
//}
//*//