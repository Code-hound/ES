package store.ws;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import store.ws.SDStoreImpl;
import pt.ulisboa.tecnico.sdis.store.ws.*;

public class CreateDocument {
	private final String USERNAME = "username";
	private final String SHEET_ID = "2205";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		DocUserPair pair = new DocUserPair();
		pair.setUserId(USERNAME);
		pair.setDocumentId(SHEET_ID);
		
		SDStoreImpl store = new SDStoreImpl();
		try {
			store.createDoc(pair);
		} catch (DocAlreadyExists_Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
