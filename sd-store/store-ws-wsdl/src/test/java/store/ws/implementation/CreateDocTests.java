package store.ws.implementation;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import store.ws.StoreImpl;

public class CreateDocTests {
	
	private static final String USERNAME = "test_username";
	private static final String SHEET_ID = "2205";
	private static DocUserPair pair = new DocUserPair();
	private static StoreImpl store = new StoreImpl();
	
	@Before
	public void setup() throws Exception {
		pair.setUserId(USERNAME);
		pair.setDocumentId(SHEET_ID);
	}
	
	@After
	public void tearDown() throws Exception {
		store.destroyRepository(USERNAME);
	}
	
	@Test
	public void success()
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception {
		store.createDoc(pair);
		assertTrue(store.listDocs(USERNAME).size()==1);
	}
	
	@Test
	public void successThreeFiles() 
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception {
		DocUserPair pair2 = new DocUserPair();
			pair2.setUserId(USERNAME);
			pair2.setDocumentId("652");
		DocUserPair pair3 = new DocUserPair();
			pair3.setUserId(USERNAME);
			pair3.setDocumentId("1093");
		
		store.createDoc(pair);
		store.createDoc(pair2);
		store.createDoc(pair3);
		assertTrue(store.listDocs(USERNAME).size()==3);
	}
	
	@Test
	public void successCreateDeletedFile() 
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception {
		DocUserPair pair2 = new DocUserPair();
			pair2.setUserId(USERNAME);
			pair2.setDocumentId("652");
		
		store.createDoc(pair);
		assertTrue(store.listDocs(USERNAME).size()==1);
		store.destroyFile(pair);
		assertTrue(store.listDocs(USERNAME).size()==0);
		store.createDoc(pair);
		assertTrue(store.listDocs(USERNAME).size()==1);
		store.listDocs(USERNAME);
	}
	
	@Test (expected = DocAlreadyExists_Exception.class)
	public void createDuplicateDocument() throws DocAlreadyExists_Exception {
		store.createDoc(pair);
		store.createDoc(pair);
	}
}
