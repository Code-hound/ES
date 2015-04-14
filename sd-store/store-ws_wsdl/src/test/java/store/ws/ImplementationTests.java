package store.ws;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import store.ws.StoreImpl;

public class ImplementationTests {
	private static final String USERNAME = "username";
	private static final String SHEET_ID = "2205";
	private static DocUserPair pair = new DocUserPair();
	private static StoreImpl store = new StoreImpl();
	private static final String CONTENT = "This project is getting really hard";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pair.setUserId(USERNAME);
		pair.setDocumentId(SHEET_ID);
		
		try {
			store.createDoc(pair);
		} catch (DocAlreadyExists_Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@Test
	public void storeContent() throws CapacityExceeded_Exception, DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception {
		byte[] content_bytes = CONTENT.getBytes();
		//System.out.println(content_bytes);
		store.store(pair, content_bytes);
	}
	
	@Test
	public void listFiles() throws CapacityExceeded_Exception, DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception {
		List<String> docs = store.listDocs(USERNAME);
		//System.out.println(docs);
		assertTrue("File number does not match the expected",
				(store.listDocs(USERNAME).size()==1));
	}
	
	@Test
	public void getFileContent() throws CapacityExceeded_Exception, DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception {
		this.storeContent();
		byte[] contentInBytes = store.load(pair);
		String contentString = new String(contentInBytes);
		assertTrue("Stored content does not match loaded content", 
				(CONTENT.equals(contentString)));
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		store.destroyFile(SHEET_ID);
	}
}
