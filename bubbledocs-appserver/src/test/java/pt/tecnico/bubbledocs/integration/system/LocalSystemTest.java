package pt.tecnico.bubbledocs.integration.system;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.core.WriteOnReadError;
import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.integration.*;
import pt.tecnico.bubbledocs.domain.BubbleDocs;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;
import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;

public class LocalSystemTest {

	@Before
	public void setUp() throws Exception {

		try {
			FenixFramework.getTransactionManager().begin();
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
	
	@Test
	public void main() {

		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};
		new MockUp<IDRemoteServices>() {
			@Mock
    		public void removeUser(String username)
    				throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};
		new MockUp<IDRemoteServices>() {
			@Mock
			public void loginUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};
		new MockUp<IDRemoteServices>() {
			@Mock
			public void renewPassword(String username)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};
		
    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    	};
    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    			LoginUserIntegrator lui = new LoginUserIntegrator("root", "password1");
    			lui.execute();
    			String root = lui.getUserToken();
    			int ID = BubbleDocs.getInstance().getSpreadSheetByOwnerAndName(BubbleDocs.getInstance().getUserByUsername("root"), "doc").getId();
    			ExportDocumentIntegrator edi = new ExportDocumentIntegrator(root, ID);
    			edi.execute();
    			return edi.getResult();
    		}
    	};
		
		LoginUserIntegrator lui = new LoginUserIntegrator("root", "password1");
		lui.execute();

		String root = lui.getUserToken();

		CreateUserIntegrator cui = new CreateUserIntegrator(root, "LJackson", "yosemite@hotmail.com", "Samwise");
		cui.execute();
		
		String bugs = cui.getUsername();
		
		CreateSpreadSheetIntegrator csi = new CreateSpreadSheetIntegrator(root, "doc", 10, 10);
		csi.execute();
		
		int ID = csi.getId();
		
		AssignLiteralToCellIntegrator alci = new AssignLiteralToCellIntegrator(root, ID, "1;2", "3456");
		alci.execute();
		

		AssignReferenceToCellIntegrator arci = new AssignReferenceToCellIntegrator(root, ID, "1;7", "1;2");
		arci.execute();
		
		AssignBinaryFunctionToCellIntegrator abfci = new AssignBinaryFunctionToCellIntegrator(root, ID, "2;3", "=ADD(1,1;7)");
		abfci.execute();
		
		ExportDocumentIntegrator edi = new ExportDocumentIntegrator(root, ID);
		edi.execute();
		
		ImportDocumentIntegrator idi = new ImportDocumentIntegrator(root, ID);
		idi.execute();
		
		GetSpreadSheetContentIntegrator gsci = new GetSpreadSheetContentIntegrator(root, ID);
		gsci.execute();
		
		RenewPasswordIntegrator rpi = new RenewPasswordIntegrator(root);
		rpi.execute();

		RemoveUserIntegrator rui = new RemoveUserIntegrator(root, bugs);
		rui.execute();

	}

}
