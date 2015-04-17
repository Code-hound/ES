package pt.ulisboa.tecnico.sdis.store.cli.test;

import static org.junit.Assert.*;
import pt.ulisboa.tecnico.sdis.store.cli.*;

import org.junit.*;
import org.junit.runner.RunWith;

import mockit.*;
import mockit.integration.junit4.JMockit;

import javax.xml.ws.WebServiceException;

import pt.ulisboa.tecnico.sdis.store.ws.*;

@RunWith(JMockit.class)
public class StoreClientMockTest {
	
	/*
	 * The mocked server fails on the first invocation
	 */
    @Test
    public void mockServerExceptionOnFirstLoadCall(
    		@Mocked final SDStore_Service service,
    		@Mocked final SDStore port,
    		@Mocked final DocUserPair pair)
        throws Exception {
    	
        // The expectations block contains the expected behaviour
        new Expectations() {{
            new SDStore_Service();
            service.getSDStoreImplPort();
            	result = port;
            
            port.load(pair);
            	result = new WebServiceException("Could not connect for some reason");
        }};

        // Unit under test is created and makes a call to the mocked server
        StoreClient client = new StoreClient();
        try {
        	client.load(pair);
        } catch (WebServiceException e) {
        	assertEquals("Could not connect for some reason", e.getMessage());
        }
    }
    
    
    /*
	 * The mocked server fails on the second invocation
	 */
    @Test
    public void mockServerExceptionOnSecondLoadCall(
    		@Mocked final SDStore_Service service,
    		@Mocked final SDStore port,
    		@Mocked final DocUserPair pair1,
    		@Mocked final DocUserPair pair2,
    		@Mocked final byte[] byteArray)
        throws Exception {
    	
        // The expectations block contains the expected behaviour
        new Expectations() {{
            new SDStore_Service();
            service.getSDStoreImplPort();
            	result = port;
            
            port.load(pair1);
            	result = byteArray;
            port.load(pair2);
            	result = new DocDoesNotExist_Exception
            			("The document does not exist", new DocDoesNotExist());
        }};

        // Unit under test is created and makes a call to the mocked server
        StoreClient client = new StoreClient();
        try {
        	client.load(pair1);
        } catch (WebServiceException e) {
        	fail();
        }
        try {
        	client.load(pair2);
        } catch (WebServiceException e) {
        	assertEquals("The document does not exist", e.getMessage());
        }
    }
    
    
    /*
	 * The mocked server fails on the second invocation
	 */
    @Test
    public void mockServerExceptionOnStoreCall(
    		@Mocked final SDStore_Service service,
    		@Mocked final SDStore port,
    		@Mocked final DocUserPair pair1,
    		@Mocked final byte[] byteArray)
        throws Exception {
    	
        // The expectations block contains the expected behaviour
        new Expectations() {{
            new SDStore_Service();
            service.getSDStoreImplPort();
            	result = port;
            
            port.store(pair1, byteArray);
            	result = new CapacityExceeded_Exception
            			("The repository has no further capacity", new CapacityExceeded());
        }};

        // Unit under test is created and makes a call to the mocked server
        StoreClient client = new StoreClient();
        try {
        	client.store(pair1, byteArray);
        } catch (CapacityExceeded_Exception e) {
        	assertEquals("The repository has no further capacity", e.getMessage());
        }
    }
}
