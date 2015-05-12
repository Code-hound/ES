package pt.ulisboa.tecnico.sdis.id.cli;

import java.util.Arrays;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.ws.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import pt.ulisboa.tecnico.sdis.id.ws.*;
import pt.ulisboa.tecnico.sdis.id.ws.crypto.*;
import pt.ulisboa.tecnico.sdis.id.exception.IdClient_Exception;



/**
*
*  ID CLIENT
*  
*  ID Client for the Client Service.
*
*  @author: Francisco Maria Calisto
*
*/

public class IdClient {
	
	/** WS service */
	private SDId_Service idService = null;
	
	/** WS port (interface) */
	private SDId idInterface = null;
	
	/** WS endpoint address */
	private String wsURL = null;
	
	/** IdClient name */
	private String name = null;
    
	/** output option **/
    private boolean verbose = false;
    
    /** Client key **/
    private Key clientKey;
    
    /** UTF-8 Variable */
    private final String utf8 = "UTF-8";
    
    /** Client ID **/
    private String clientID = "1";
    
    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public IdClient() throws IdClient_Exception {
    	createStub();
    }
    
    public IdClient(String wsURL, String name) throws IdClient_Exception, NoSuchAlgorithmException {
    	this.wsURL = wsURL;
    	this.name = name;
    	
        createStub();
    }
	
    public void createStub() {
    	if (verbose)
            System.out.println("Creating stub ...");
        idService = new SDId_Service();
        idInterface = idService.getSDIdImplPort();

        if (wsURL != null) {
            if (verbose)
                System.out.println("Setting endpoint address ...");
            BindingProvider bindingProvider = (BindingProvider) idInterface;
            Map<String, Object> requestContext = bindingProvider.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsURL);
        }
    }
    /*
    public void generateKey() throws NoSuchAlgorithmException {
    	SecureRandom random = new SecureRandom();
        this.clientKey = SymKey.getKey(random);
    }
	*/
	public void createUser(String userId, String emailAddress)
			throws EmailAlreadyExists_Exception, InvalidEmail_Exception,
			InvalidUser_Exception, UserAlreadyExists_Exception {
		idInterface.createUser(userId, emailAddress);
	}

	public void renewPassword(String userId) throws UserDoesNotExist_Exception {
		idInterface.renewPassword(userId);
	}

	public void removeUser(String userId) throws UserDoesNotExist_Exception {
		idInterface.removeUser(userId);
	}

	public byte[] requestAuthentication(String userId, String password, String serverURL)
			throws Exception {
		SymCrypto crypto = new SymCrypto();
		byte[] passwordEncrypted = crypto.encrypt(password);
		byte[] serverURLBytes = crypto.encrypt(serverURL);
		byte[] random = "lalala".getBytes();
		//byte[] reserved = new byte[] {passwordEncrypted, serverURLBytes};
		byte[] reserved = new byte[passwordEncrypted.length + serverURLBytes.length];
		
		byte[] response = idInterface.requestAuthentication(userId, passwordEncrypted);
		
		return response;
	}
		/*
		String password = "It's a secret!  Make sure it's long enough (24 bytes)";
    	byte[] keyBytes = Arrays.copyOf(password.getBytes(utf8), 24);
    	
    	try {
	    	SecretKey key = new SecretKeySpec(keyBytes, "TripleDES");
	
	    	// Your vector must be 8 bytes long
	    	String vector = "ABCD1234";
	    	IvParameterSpec iv = new IvParameterSpec(vector.getBytes(utf8));
	
	    	// Make an encrypter
	    	Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
	    	encrypt.init(Cipher.ENCRYPT_MODE, key, iv);
	
	    	// Make a decrypter
	    	Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
	    	decrypt.init(Cipher.DECRYPT_MODE, key, iv);
			
			// Example use
	    	String message = new String(reserved, utf8);
	    	byte[] messageBytes = message.getBytes(utf8);
	    	byte[] encryptedByted = encrypt.doFinal(messageBytes);
	    	byte[] decryptedBytes = decrypt.doFinal(encryptedByted);
	
	    	// You can re-run the example to see taht the encrypted bytes are consistent
	    	String messageString = new String(messageBytes, utf8);
	    	String encryptedString = new String(encryptedByted, utf8);
	    	String decryptedString = new String(decryptedBytes, utf8);
	    	
	    	System.out.println(messageString);
	    	System.out.println(encryptedString);
	    	System.out.println(decryptedString);
	    	
	    	if (messageString.equals(decryptedString)) {
	    		System.out.println("The message is equal after encryoted and before decrypted message");
	    	}
    	} catch ( UnsupportedEncodingException |
    			  NoSuchAlgorithmException |
    			  NoSuchPaddingException |
    			  InvalidKeyException |
    			  InvalidAlgorithmParameterException |
    			  IllegalBlockSizeException |
    			  BadPaddingException e) {
    		System.out.println("Could now create key");
    		return null;
    	}
		return idInterface.requestAuthentication(userId, reserved);
	}*/
}