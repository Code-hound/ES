package pt.ulisboa.tecnico.sdis.store.cli.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore_Service;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import store.cli.StoreClient;
import store.cli.StoreClientException;

public class StoreClientRealTest {
	
	//private static SDStore port;
	//private static DocUserPair pair;
	private static StoreClient client;
	private static DocUserPair pair;
	private static final String USERNAME = "orwell";
	private static final String DOC_ID1 = "1984";
	private static final String DOC_ID2 = "Animal Farm";
	private static final String DOC_ID3 = "Home To Catalonia";
	private static final String CONTENT = 
			"Word had gone round during the day that old Major, the prize Middle White"
			+ " boar, had had a strange dream on the previous night and wished to "
			+ "communicate it to the other animals.";
	
	
	
	@Before
    public void setUp() throws Exception {
		client = new StoreClient("http://localhost:8081", "SD-Store");
		pair = new DocUserPair();
		pair.setUserId(USERNAME);
		/*
		 SDStore_Service service = new SDStore_Service();
	     port = service.getSDStoreImplPort();
	     
	     pair = new DocUserPair();
	     pair.setUserId("username");
	     pair.setDocumentId("docId");
	     */
    }
	
	@Test
	public void checkIfThreeEndpoints() {
		String[] endpoints = client.getEndpointAddresses();
		assertTrue (endpoints.length == 3);
		/*
		for (String endpoint : endpoints) {
			System.out.println(endpoint);
		}
		*/
	}
	
	@Test
	public void createAndList() 
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception {
		pair.setDocumentId(DOC_ID1);
		client.createDoc(pair);
		
		List<String> files = client.listDocs(USERNAME);
		//We can't be sure there aren't more files stored from previous tests
		assertTrue(files.size() >= 1);
		//assertEquals(DOC_ID1+".txt",files.get(0));
	}
	
	@Test
	public void createStoreAndLoad() 
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception, 
			CapacityExceeded_Exception, DocDoesNotExist_Exception, 
			UnsupportedEncodingException {
		pair.setDocumentId(DOC_ID2);
		client.createDoc(pair);
		
		byte[] contentToBytes = CONTENT.getBytes();
		client.store(pair, contentToBytes);
		
		String loadedContent = new String (client.load(pair), "UTF-8");
		assertEquals(CONTENT, loadedContent);
	}
	
	@Test (expected = StoreClientException.class)
	public void wrongUDDIUrl() 
			throws DocAlreadyExists_Exception, UserDoesNotExist_Exception, 
			StoreClientException {
		client = new StoreClient("www.4chan.org/mlp", "SD-Store");
		pair.setDocumentId(DOC_ID3);
		client.createDoc(pair);
	}
	
	@Test (expected = StoreClientException.class)
	public void wrongServiceName() 
			throws StoreClientException, DocAlreadyExists_Exception {
		client = new StoreClient("http://localhost:8081", "UberCarReserve");
		pair.setDocumentId(DOC_ID3);
		client.createDoc(pair);
	}
	
	@Test (expected = DocDoesNotExist_Exception.class)
	public void storeWithWrongDocID() 
			throws DocAlreadyExists_Exception, CapacityExceeded_Exception, 
			DocDoesNotExist_Exception, UserDoesNotExist_Exception {
		byte[] contentToBytes = CONTENT.getBytes();
		pair.setDocumentId("doesNotExist");
		client.store(pair, contentToBytes);
	}
	
	@Test (expected = DocAlreadyExists_Exception.class)
	public void createDuplicateDoc() 
			throws StoreClientException, DocAlreadyExists_Exception {
		client = new StoreClient("http://localhost:8081", "SD-Store");
		pair.setDocumentId(DOC_ID3);
		client.createDoc(pair);
		client.createDoc(pair);
	}

    @After
    public void tearDown() {
    	client = null;
    	pair = null;
    }
	/*
    @Test
    public void success() throws DocAlreadyExists_Exception {
    	client.createDoc(pair);
    }
	*/
    
    
}
