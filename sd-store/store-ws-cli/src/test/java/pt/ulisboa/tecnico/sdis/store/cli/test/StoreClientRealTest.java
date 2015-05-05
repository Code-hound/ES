package pt.ulisboa.tecnico.sdis.store.cli.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore_Service;
import store.cli.StoreClient;

public class StoreClientRealTest {
	
	//private static SDStore port;
	//private static DocUserPair pair;
	private static StoreClient client;
	private static DocUserPair pair;
	
	@Before
    public void setUp() throws Exception {
		client = new StoreClient("http://localhost:8081", "SD-Store");
		pair = new DocUserPair();
		pair.setDocumentId("docId");
		pair.setUserId("username");
		/*
		 SDStore_Service service = new SDStore_Service();
	     port = service.getSDStoreImplPort();
	     
	     pair = new DocUserPair();
	     pair.setUserId("username");
	     pair.setDocumentId("docId");
	     */
    }
	
	@Test
	public void atLeastThreeEndpoints() {
		String[] endpoints = client.getEndpointAddresses();
		assertTrue (endpoints.length == 3);
		for (String endpoint : endpoints) {
			System.out.println(endpoint);
		}
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
