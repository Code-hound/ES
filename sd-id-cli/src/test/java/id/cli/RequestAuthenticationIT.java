package id.cli;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import pt.ulisboa.tecnico.sdis.id.exception.AuthReqFailed_Exception;

/**
 * Test suite
 */
public class RequestAuthenticationIT extends AbstractIdIT {

    // tests
    // assertEquals(expected, actual);

    // public byte[] requestAuthentication(
    // String userId,
    // byte[] reserved)
    // throws AuthReqFailed_Exception {

    // O SD-ID confirma se os argumentos recebidos estao corretos (utilizador
    // existe e sua senha atual e
    // a fornecida) e retorna um valor booleano a indicar se houve ou nao
    // sucesso.
	/*
    @Test
    public void testRequestAuthN() throws Exception {
        // assumes user alice exists
        final String userId = "alice";
        final byte[] reserved = "Aaa1".getBytes();

        byte[] credentials = ID_CLIENT.requestAuthentication(userId, reserved);
        assertNotNull(credentials);
        // TODO further validation of provided credentials
    }
    
    @Test
    public void testRequestAuthReturn() throws Exception {
        // assumes user alice exists
        final String userId = "alice";
        final byte[] reserved = "Aaa1".getBytes();

        byte[] credentials = ID_CLIENT.requestAuthentication(userId, reserved);
        byte[] OK = { (byte) 1 };
        assertArrayEquals(OK, credentials);
        // TODO further validation of provided credentials
    }

    @Test(expected = AuthReqFailed_Exception.class)
    public void testWrongPassword() throws Exception {
        final String userId = "alice";
        final byte[] reserved = "wrongpassword".getBytes();

        ID_CLIENT.requestAuthentication(userId, reserved);
    }

    @Test(expected = AuthReqFailed_Exception.class)
    public void testNullPassword() throws Exception {
        final String userId = "alice";
        final byte[] reserved = null;

        ID_CLIENT.requestAuthentication(userId, reserved);
    }

    @Test(expected = AuthReqFailed_Exception.class)
    public void testEmptyPassword() throws Exception {
        final String userId = "alice";
        final byte[] reserved = "".getBytes();

        ID_CLIENT.requestAuthentication(userId, reserved);
    }
    
    @Test(expected = AuthReqFailed_Exception.class)
    public void testEmptyUsername() throws Exception {
        final String userId = "";
        final byte[] reserved = "Aaa1".getBytes();

        ID_CLIENT.requestAuthentication(userId, reserved);
    }
    
    @Test(expected = AuthReqFailed_Exception.class)
    public void testNullUsername() throws Exception {
        final String userId = null;
        final byte[] reserved = "Aaa1".getBytes();

        ID_CLIENT.requestAuthentication(userId, reserved);
    }
    
    @Test(expected = AuthReqFailed_Exception.class)
    public void testWrongUsername() throws Exception {
        final String userId = "wrongusername";
        final byte[] reserved = "Aaa1".getBytes();

        ID_CLIENT.requestAuthentication(userId, reserved);
    }
    
    @Test(expected = AuthReqFailed_Exception.class)
    public void testBothNull() throws Exception {
        final String userId = null;
        final byte[] reserved = null;

        ID_CLIENT.requestAuthentication(userId, reserved);
    }
    
    @Test(expected = AuthReqFailed_Exception.class)
    public void testBothEmpty() throws Exception {
        final String userId = "";
        final byte[] reserved = "".getBytes();

        ID_CLIENT.requestAuthentication(userId, reserved);
    }
	*/
}
