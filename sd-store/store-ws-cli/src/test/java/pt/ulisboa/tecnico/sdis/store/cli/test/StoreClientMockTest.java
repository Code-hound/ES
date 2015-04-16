package pt.ulisboa.tecnico.sdis.store.cli.test;

import org.junit.*;
import org.junit.runner.RunWith;

import pt.ulisboa.tecnico.sdis.store.cli.*;
import mockit.*;
import mockit.integration.junit4.JMockit;

import javax.xml.ws.WebServiceException;

import pt.ulisboa.tecnico.sdis.store.cli.*;
import pt.ulisboa.tecnico.sdis.store.ws.*;

@RunWith(JMockit.class)
public class StoreClientMockTest {
	
    @Test(expected=WebServiceException.class)
    public void mockServerException(
    		@Mocked final SDStore_Service service,
    		@Mocked final SDStore port,
    		@Mocked final DocUserPair pair)
        throws Exception {
    	
        // an "expectation block"
        // One or more invocations to mocked types, causing expectations to be recorded.
        new Expectations() {{
            new SDStore_Service();
            
            service.getSDStoreImplPort();
            	result = port;
            
            port.load(pair);
            	result = new WebServiceException("Could not connect for some reason");
        }};


        // Unit under test is exercised.
        StoreClient client = new StoreClient();
        // call to mocked server
        client.load(pair);
    }
}
