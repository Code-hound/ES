package pt.tecnico.bubbledocs.integration.system;

import java.io.UnsupportedEncodingException;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

import mockit.Mock;
import mockit.MockUp;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.TransactionManager;
import pt.tecnico.bubbledocs.exception.CannotStoreDocumentException;
import pt.tecnico.bubbledocs.exception.ExportDocumentException;
import pt.tecnico.bubbledocs.exception.LoginBubbleDocsException;
import pt.tecnico.bubbledocs.exception.RemoteInvocationException;
import pt.tecnico.bubbledocs.integration.*;
import pt.tecnico.bubbledocs.service.remote.IDRemoteServices;
import pt.tecnico.bubbledocs.service.remote.StoreRemoteServices;

public class LocalSystemTest {

	public static void main(String[] args) {
		/*
		new MockUp<IDRemoteServices>() {
			@Mock
			public void createUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
    		public void removeUser(String username)
    				throws LoginBubbleDocsException, RemoteInvocationException {
    		}
			public void loginUser(String username, String password)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
			public void renewPassword(String username)
					throws LoginBubbleDocsException, RemoteInvocationException {
			}
		};
		
    	new MockUp<StoreRemoteServices>() {
    		@Mock
    		public void storeDocument(String username, String SpreadSheetName, byte[] result)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    		public byte[] loadDocument(String username, String SpreadSheetName)
    				throws CannotStoreDocumentException, RemoteInvocationException {
    		}
    	};
		
		LoginUserIntegrator lui = new LoginUserIntegrator("root", "password1");
		lui.execute();

		String root = lui.getUserToken();

		CreateUserIntegrator cui = new CreateUserIntegrator(root, "LJackson", "yosemite@hotmail.com", "Samwise");
		cui.execute();
		
		String bugs = cui.getUsername();
		
		CreateSpreadSheetIntegrator csi = CreateSpreadSheetIntegrator(root, "doc", 10, 10);
		csi.execute();
		
		int ID = csi.getId();
		
		AssignLiteralToCellIntegrator alci = AssignLiteralToCellIntegrator(root, ID, "1;2", "3456");
		alci.execute();
		
		int value = alci.getResult();
		
		AssignReferenceToCellIntegrator arci = AssignReferenceToCellIntegrator(root, ID, "1;7", "1;2");
		arci.execute();
		
		AssignBinaryFunctionToCellIntegrator abfci = AssignBinaryFunctionToCellIntegrator(root, ID, "2;3", "ADD(1;2,1;7)");
		
		Reference local = arci.getResult();
		
		ExportDocumentIntegrator edi = new ExportDocumentIntegrator(root, ID);
		edi.execute();
		
		ImportDocumentIntegrator idi = new ImportDocumentIntegraor(root, ID);
		idi.execute();
		
		GetSpreadSheerContentIntegrator gsci = new GetSpreadSheerContentIntegrator(root, ID);
		gsci.execute();
		
		SpreadSheet doc1 = idi.getResult();
		SpreadSheet doc2 = gsci.getResult();
		
		if ( doc1.toString() == doc2.toString() ) {
			RenewPasswordIntegrator rpi = new RenewPasswordIntegrator(root);
			rpi.execute();
		}

		RemoveUserIntegrator rui = new RemoveUserIntegrator(root, bugs);
		rui.execute();
		*/

	}

}
