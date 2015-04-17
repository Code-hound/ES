package id.cli;

import static org.junit.Assert.*;
import mockit.*;

import javax.xml.ws.WebServiceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jdom2.Element;

import java.util.List;
import id.ws.IdImpl;

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
        @Mocked final SDId port)
        throws Exception {

        // an "expectation block"
        // One or more invocations to mocked types, causing expectations to be recorded.
        new Expectations() {{
            new SDId_Service();
            service.getSDIdImplPort();
            result = port;
            port.renewPassword(anyUser);
            result = new WebServiceException("fabricated");
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
            port.renewPassword(anyUser);
            // first call to sum returns the result
            result = "aaa1";
            // second call throws an exception
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
            client.renewPassword("alice");
            fail();
        } catch(WebServiceException e) {
            // exception is expected
            assertEquals("fabricated", e.getMessage());
        }
    }

    /**
     *  In this test the server is mocked to
     *  test the divide by zero exception propagation.
     */
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
            port.removeUser(anyUser);
            // first call to intdiv returns any number
            result = null;
            // second call throws an exception
            result = new UserDoesNotExist_Exception("fabricated", new UserDoesNotExist_ExceptionType());
        }};


        // Unit under test is exercised.
        IdClient client = new IdClient();

        // first call to mocked server
        client.removeUser("alice");

        // second call to mocked server
        try {
            client.removeUser("alice");
            fail();
        } catch(UserDoesNotExist_Exception e) {
            // exception is expected
            assertEquals("fabricated", e.getMessage());
        }


        // a "verification block"
        // One or more invocations to mocked types, causing expectations to be verified.
        new Verifications() {{
            // Verifies that zero or one invocations occurred, with the specified argument value:
            port.removeUser(anyUser); maxTimes = 2;
        }};
    }


}
