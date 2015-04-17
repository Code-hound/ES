package id.cli;

import org.junit.*;

import static org.junit.Assert.*;
import mockit.*;
import mockit.integration.junit4.JMockit;

import javax.xml.ws.WebServiceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jdom2.Element;

import java.util.List;

import pt.ulisboa.tecnico.sdis.id.ws.*;
//import id.ws.IdImpl;

@RunWith(JMockit.class)
public class MockTests {
	
    // static members
    // one-time initialization and clean-up

    @BeforeClass
    public static void oneTimeSetUp() {
    }

    @AfterClass
    public static void oneTimeTearDown() {
    }	

    // members
    // initialization and clean-up for each test

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    // tests

    /**
     *  In this test the server is mocked to
     *  simulate a communication exception.
     */
    @Test(expected=WebServiceException.class)
    public void testMockServerException(
        @Mocked final SDId_Service service,
        @Mocked final SDId port,
        @Mocked final String username)
        throws Exception {

        // an "expectation block"
        // One or more invocations to mocked types, causing expectations to be recorded.
        new Expectations() {{
            new SDId_Service();
            service.getSDIdImplPort();
            result = port;
            port.renewPassword("alice");
            result = new WebServiceException("fabricated");
            
            //idService = new SDId_Service();
            //idInterface = idService.getSDIdImplPort();
        }};


        // Unit under test is exercised.
        IdClient client = new IdClient();
        // call to mocked server
        client.renewPassword("alice");
    }

    /**
     *  In this test the server is mocked to
     *  simulate a communication exception on a second call.
     */
    @Test
    public void testMockServerExceptionOnSecondCall(
        @Mocked final SDId_Service service,
        @Mocked final SDId port)
        throws Exception {

        // an "expectation block"
        // One or more invocations to mocked types, causing expectations to be recorded.
        new Expectations() {{
            new SDId_Service();
            service.getSDIdImplPort(); 
            result = port;
            port.renewPassword("alice");
            // first call mocks success, has no return
            
            // second call throws an exception
            port.renewPassword("miguel");
            result = new WebServiceException("fabricated");
        }};


        // Unit under test is exercised.
        IdClient client = new IdClient();

        // first call to mocked server
        try {
            client.renewPassword("alice");
        } catch(WebServiceException e) {
            // exception is not expected
            fail();
        }

        // second call to mocked server
        try {
            client.renewPassword("miguel");
            //fail();
        } catch(WebServiceException e) {
            // exception is expected
            assertEquals("fabricated", e.getMessage());
        }
    }

    
    @Test
    public void testMockServer(
        @Mocked final SDId_Service service,
        @Mocked final SDId port)
        throws Exception {

        // an "expectation block"
        // One or more invocations to mocked types, causing expectations to be recorded.
        new Expectations() {{
            new SDId_Service();
            service.getSDIdImplPort(); 
            result = port;
            port.removeUser("alice");
            // first call
            result = null;
            // second call throws an exception
            port.removeUser("miguel");
            result = new UserDoesNotExist_Exception("fabricated", new UserDoesNotExist());
        }};


        // Unit under test is exercised.
        IdClient client = new IdClient();

        // first call to mocked server
        try {
        	client.removeUser("alice");
        } catch(WebServiceException e) {
            // exception is not expected
            fail();
        }

        // second call to mocked server
        try {
            client.removeUser("miguel");
            fail();
        } catch(UserDoesNotExist_Exception e) {
            // exception is expected
            assertEquals("fabricated", e.getMessage());
        }


        // a "verification block"
        // One or more invocations to mocked types, causing expectations to be verified.
        new Verifications() {{
            // Verifies that zero or one invocations occurred, with the specified argument value:
            port.removeUser("alice"); maxTimes = 2;
        }};
    }


}
