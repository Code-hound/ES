package store.ws;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import store.ws.StoreImpl;

public class StoreAndLoadTest {
	
	private static final String USERNAME = "francisco";
	private static final String BAD_USERNAME = "pocoyo";
	private static final String SHEET_ID = "2205";
	private static final String BAD_SHEET_ID = "1096";
	private static DocUserPair pair = new DocUserPair();
	private static DocUserPair bad_pair = new DocUserPair();
	private static StoreImpl store = new StoreImpl(0);
	private static final String CONTENT = "This project is getting really hard";
	private static final byte[] STORED_BYTES = CONTENT.getBytes();
	private static final String CONTENT_NEW = "Oh look, it's getting easier";
	private static final byte[] STORED_NEW_BYTES = CONTENT_NEW.getBytes();
	
	@Before
	public void setup() throws Exception {
		pair.setUserId(USERNAME);
		pair.setDocumentId(SHEET_ID);
		store.createDoc(pair);
	}
	
	@After
	public void tearDown() throws Exception {
		store.reset();
	}
	
	@Test
	public void success() 
			throws CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception, 
			UnsupportedEncodingException {
		store.store(pair, STORED_BYTES);
		
		byte[] loaded_bytes = store.load(pair);
		String loaded_string = new String (loaded_bytes, "UTF-8");
		
		assertEquals(CONTENT, loaded_string);
	}
	
	@Test
	public void successStoreAgain() 
			throws CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception, 
			UnsupportedEncodingException {
		store.store(pair, STORED_BYTES);
		store.store(pair, STORED_NEW_BYTES);
		
		byte[] loaded_bytes = store.load(pair);
		String loaded_string = new String (loaded_bytes, "UTF-8");
		
		assertEquals(CONTENT_NEW, loaded_string);
	}
	
	@Test
	public void successStoreAgainEmptyArray() 
			throws CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception, 
			UnsupportedEncodingException {
		store.store(pair, STORED_BYTES);
		
		byte[] empty = new byte[0];
		store.store(pair, empty);
		
		byte[] loaded_bytes = store.load(pair);
		String loaded_string = new String (loaded_bytes, "UTF-8");
		
		assertTrue(store.listDocs(USERNAME).size()==1);
		assertEquals("", loaded_string);
	}
	

	@Test (expected = DocDoesNotExist_Exception.class)
	public void storeInexistentDocument()
			throws CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception {
		bad_pair.setUserId(USERNAME);
		bad_pair.setDocumentId(BAD_SHEET_ID);
		
		store.store(bad_pair, STORED_BYTES);
	}
	
	@Test (expected = UserDoesNotExist_Exception.class)
	public void storeInexistentUser()
			throws CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception {
		bad_pair.setUserId(BAD_USERNAME);
		bad_pair.setDocumentId(SHEET_ID);
		
		store.store(bad_pair, STORED_BYTES);
	}
	
	@Test (expected = DocDoesNotExist_Exception.class)
	public void loadInexistentDocument()
			throws CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception {
		bad_pair.setUserId(USERNAME);
		bad_pair.setDocumentId(BAD_SHEET_ID);
		
		store.load(bad_pair);
	}
	
	@Test (expected = UserDoesNotExist_Exception.class)
	public void loadInexistentUser()
			throws CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception {
		bad_pair.setUserId(BAD_USERNAME);
		bad_pair.setDocumentId(SHEET_ID);
		
		store.load(bad_pair);
	}
}
