package id.ws;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.id.ws.exception;
import id.ws.IdImpl;

public class ImplementationTests {
	private static final String USERNAME = "username";
	private static final String SHEET_ID = "2205";
	private static DocUserPair pair = new DocUserPair();
	private static IdImpl id = new IdImpl();
	private static final String CONTENT = "This project is getting really hard";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pair.setUserId(USERNAME);
		pair.setDocumentId(SHEET_ID);
		
		try {
			id.createDoc(pair);
		} catch (DocAlreadyExists_Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@Test
	public void createUser() throws CapacityExceeded_Exception, DocDoesNotExist_Exception, 
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
		id.destroyFile(SHEET_ID);
	}
}
