package pt.ulisboa.tecnico.sdis.store.cli.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.registry.JAXRException;

import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore_Service;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import store.cli.StoreClientException;
import store.cli.StoreClient;

import org.junit.After;
import org.junit.Before;

import store.cli.StoreClientException;

public class StoreClientRealTest {
	
	private static final String uddiURL = "http://localhost:8081";
	private static final String wsName = "SD-STORE";
	private static final int multiplicity = 3;
	private static StoreClient client;
	
	private static final String USERNAME = "orwell";
	private static final String DOC_ID1 = "1984";
	private static final String DOC_ID2 = "Animal Farm";
	private static final String DOC_ID3 = "Homage To Catalonia";
	private static final String DOC_ID4 = "Coming Up For Air";
	private static final String DOC_ID5 = "A Clergyman's Daughter";
	private static final String CONTENT = 
			"Word had gone round during the day that old Major, the prize Middle White"
			+ " boar, had had a strange dream on the previous night and wished to "
			+ "communicate it to the other animals.";
	
	@Before
	public void setup() 
			throws JAXRException, StoreClientException {
		client = new StoreClient(uddiURL, wsName, multiplicity);
	}
	
	@After
	public void teardown() {
		client = null;
	}
	
	/*
	private void printDocs(String USERNAME) throws UserDoesNotExist_Exception {
		System.out.println("Printing "+USERNAME+"'s documents:");
		for (String s : client.listDocs(USERNAME)){
			System.out.println(s);
		}
	}
	*/
	
	@Test
	public void numberOfAddresses() throws JAXRException, StoreClientException {
		String[] addresses = client.getEndpointAddresses();
		assertTrue(addresses.length == 3);
		
		client = new StoreClient(uddiURL, wsName, 1);
		addresses = client.getEndpointAddresses();
		assertTrue(addresses.length == 1);
	}
	
	@Test
	public void createAndList() 
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception, InterruptedException {
		client.createDoc(USERNAME, DOC_ID1);
		
		List<String> files = client.listDocs(USERNAME);
		//We can't be sure there aren't more files stored from previous tests
		//So we check if there's AT LEAST 1 file stored
		assertTrue(files.size() >= 1);
		//assertEquals(DOC_ID1+".txt",files.get(0));
	}
	
	@Test
	public void createStoreAndLoad() 
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception, 
			CapacityExceeded_Exception, DocDoesNotExist_Exception, 
			UnsupportedEncodingException, InterruptedException, ExecutionException {
		client.createDoc(USERNAME, DOC_ID2);
		
		byte[] contentToBytes = CONTENT.getBytes();
		client.store(USERNAME, DOC_ID2, contentToBytes);
		
		String loadedContent = new String (client.load(USERNAME, DOC_ID2), "UTF-8");
		assertEquals(CONTENT, loadedContent);
	}
	
	@Test (expected = StoreClientException.class)
	public void wrongUDDIUrl() 
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception, 
			StoreClientException, JAXRException, InterruptedException {
		client = new StoreClient("www.4chan.org/mlp", wsName, 1);
		client.createDoc(USERNAME, DOC_ID3);
	}
	
	@Test
	public void wrongMultiplicityWith3Endpoints() 
			throws StoreClientException, DocAlreadyExists_Exception, JAXRException, InterruptedException {
		try {
			client = new StoreClient(uddiURL,wsName, 100);
			client.createDoc(USERNAME, DOC_ID3);
		} catch (StoreClientException e) {
			assertTrue(e.getMessage().startsWith("The multiplicity for the SD-STORE service "+
						"currently can be no larger than"));
		}
	}
	
	@Test (expected = DocDoesNotExist_Exception.class)
	public void storeWithWrongDocID() 
			throws DocAlreadyExists_Exception, CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, UserDoesNotExist_Exception, InterruptedException {
		byte[] contentToBytes = CONTENT.getBytes();
		client.createDoc(USERNAME, DOC_ID4); 
		client.store(USERNAME, "Fake ID", contentToBytes); 
	}
	
	@Test (expected = DocAlreadyExists_Exception.class)
	public void createDuplicateDoc() 
			throws StoreClientException, DocAlreadyExists_Exception, JAXRException, InterruptedException {
		client.createDoc(USERNAME, DOC_ID5);
		client.createDoc(USERNAME, DOC_ID5);
	}
}
