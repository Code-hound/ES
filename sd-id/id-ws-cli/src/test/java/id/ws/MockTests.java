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
        @Mocked final SDId idInterface port)
        throws Exception {

        // an "expectation block"
        // One or more invocations to mocked types, causing expectations to be recorded.
        new Expectations() {{
            new SDId_Service();
            service.getCalcPort(); //FIX
            result = port;
            port.renewPassword(anyUser);
            result = new WebServiceException("fabricated");
        }};


        // Unit under test is exercised.
        IdClient client = new IdClient();
        // call to mocked server
        client.renewPassword("Alice");
    }

    /**
     *  In this test the server is mocked to
     *  simulate a communication exception on a second call.
     */
    @Test
    public void testMockServerExceptionOnSecondCall(
        @Mocked final CalcService service,
        @Mocked final CalcPortType port)
        throws Exception {

        // an "expectation block"
        // One or more invocations to mocked types, causing expectations to be recorded.
        new Expectations() {{
            new SDId_Service();
            service.getCalcPort(); 
            result = port;
            port.renewPassword(anyUser);
            // first call to sum returns the result
            result = 3; // nao interessa, apenas a excepçao na second call
            // second call throws an exception
            result = new WebServiceException("fabricated");
        }};


        // Unit under test is exercised.
        IdClient client = new IdClient();

        // first call to mocked server
        try {
            client.sum(1,2);
        } catch(WebServiceException e) {
            // exception is not expected
            fail();
        }

        // second call to mocked server
        try {
            client.sum(1,2);
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
        @Mocked final CalcService service,
        @Mocked final CalcPortType port)
        throws Exception {

        // an "expectation block"
        // One or more invocations to mocked types, causing expectations to be recorded.
        new Expectations() {{
            new CalcService();
            service.getCalcPort(); result = port;
            port.intdiv(anyInt, anyInt);
            // first call to intdiv returns any number
            result = anyInt;
            // second call throws an exception
            result = new DivideByZero("fabricated", new DivideByZeroType());
        }};


        // Unit under test is exercised.
        CalcClient client = new CalcClient();

        // first call to mocked server
        client.intdiv(10,5);

        // second call to mocked server
        try {
            client.intdiv(10,5);
            fail();
        } catch(DivideByZero e) {
            // exception is expected
            assertEquals("fabricated", e.getMessage());
        }


        // a "verification block"
        // One or more invocations to mocked types, causing expectations to be verified.
        new Verifications() {{
            // Verifies that zero or one invocations occurred, with the specified argument value:
            port.intdiv(anyInt, anyInt); maxTimes = 2;
        }};
    }


}
