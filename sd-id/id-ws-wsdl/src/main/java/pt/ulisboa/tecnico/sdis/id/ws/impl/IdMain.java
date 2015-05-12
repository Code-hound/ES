package pt.ulisboa.tecnico.sdis.id.ws.impl;

import java.util.*;
import java.security.Key;

import javax.xml.ws.Endpoint;
import javax.naming.*;
import javax.naming.directory.*;
import javax.security.auth.login.*;
import javax.security.auth.callback.*;
import javax.security.auth.Subject;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import uddi.UDDINaming;

@SuppressWarnings("unused")
public class IdMain {

    public static void main(String[] args) throws Exception {
    	
    	/*
    	 * 
    	 * With Minimal Implementation by Professor
    	 * 
    	 */
		
		String uddiURL = null;
		String wsName = null;
		String wsURL = null;
		
		IdImpl impl = null;
        
        Endpoint endpoint = null;
        UDDINaming uddiNaming = null;

        TestControl testImpl = null;
    	
    	final String plainText = args[0];
        final byte[] plainBytes = plainText.getBytes();
    	
        // Check arguments and get plaintext
		if (args.length == 0 || args.length == 2) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + IdMain.class.getName()
					+ " wsURL OR uddiURL wsName wsURL");
			System.err.println("args: (text)");
			
			return;
		}
		
		if (args.length == 1) {
			wsURL = args[0];
			impl = new IdImpl(wsURL);
		} else if (args.length >= 3) {
			uddiURL = args[0];
			wsName = args[1];
			wsURL = args[2];
			impl = new IdImpl(uddiURL, wsName, wsURL);
		}
        
        try {
        	
        	impl.start();
        	
        	if ("true".equalsIgnoreCase(System.getProperty("ws.test"))) {
        		IdImpl.reset();
        		
        		testImpl = new TestControl(wsURL + "/test");
        		testImpl.start();
        	}
        	impl.awaitConnections();
        } finally {
            impl.stop();
            
            if (testImpl != null) {
                testImpl.stop();
            }
        }
        
		try {
        	
            endpoint = Endpoint.create(new IdImpl(args));

            // publish endpoint
            System.out.printf("Starting %s%n", wsURL);
            endpoint.publish(wsURL);
            
            // publish to UDDItype filter text
            System.out.printf("Publishing '%s' to UDDI at %s%n", wsName, uddiURL);
            uddiNaming = new UDDINaming(uddiURL);
            uddiNaming.rebind(wsName, wsURL);            

            // wait
            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
            System.in.read();

        } catch(Exception e) {
            System.out.printf("Caught exception: %s%n", e);
            e.printStackTrace();

        } finally {
            try {
                if (endpoint != null) {
                    // stop endpoint
                    endpoint.stop();
                    System.out.printf("Stopped %s%n", wsURL);
                }
            } catch(Exception e) {
                System.out.printf("Caught exception when stopping: %s%n", e);
            }
            try {
                if (uddiNaming != null) {
                    // delete from UDDI
                    uddiNaming.unbind(wsName);
					System.out.printf("Deleted '%s' from UDDI%n", wsName);
                }
            } catch(Exception e) {
                System.out.printf("Caught exception when deleting: %s%n", e);
            }
        }
    }  
        /*
         * 
         * Demonstrate how to create an initial context to the SD-ID Server
         * using Kerberos V5.
         * 
         * @usage: java
         * @version: 1.0.0
         * @author: Francisco Maria Calisto
         * 
         */
		
		
        /*
        // 1. Log in (to Kerberos)
        LoginContext lc = null;
        
        try {
        	lc = new LoginContext(IdMain.class.getName()); //TODO
        	
        	// Attempt authentication
    	    // We might want to do this in a "for" loop to give
    	    // user more than one chance to enter correct username/password
    	    lc.login(); //TODO
    	    
        } catch (LoginException le) {
    	    System.err.println("Authentication attempt failed" + le);
    	    System.exit(-1);
    	}
        
        // 2. Perform JNDI work as logged in subject
    	//Subject.doAs(lc.getSubject(), new IdImpl(args));
    
	    */
	    
	    /*
		 * 
		 * This is a Java Web Service that supports asynchronous operations.
	     *
	     * The code is identical to other contract-first Web Services.
	     *
	     * The service is defined by the Java code with annotations
	     * (code-first approach, also called bottom-up approach).
	     *
	     * The service runs in a standalone HTTP server.
		 * 
		 */
	    
	    /* ----- ATE AQUI TUDO BEM ----- */
	/*	
		 System.out.println("Text:");
	     System.out.println(plainText);
	     System.out.println("Bytes:");
	     System.out.println(printHexBinary(plainBytes));
	     

	     // get a DES private key
	     System.out.println("Generating DES key ...");
	     KeyGenerator keyGen = KeyGenerator.getInstance("TripleDES");
	     keyGen.init(168);
	     Key key = keyGen.generateKey();
	     System.out.println("Key:");
	     System.out.println(printHexBinary(key.getEncoded()));

	     // get a DES cipher object and print the provider
	     Cipher cipher = Cipher.getInstance("TripleDES/ECB/PKCS5Padding");
	     System.out.println(cipher.getProvider().getInfo());

	     // encrypt using the key and the plaintext
	     System.out.println("Text:");
	     System.out.println(plainText);
	     System.out.println("Bytes:");
	     System.out.println(printHexBinary(plainBytes));

	     System.out.println("Ciphering ...");
	     cipher.init(Cipher.ENCRYPT_MODE, key);
	     byte[] cipherBytes = cipher.doFinal(plainBytes);
	     System.out.println("Result:");
	     System.out.println(printHexBinary(cipherBytes));

	     // decrypt the ciphertext using the same key
	     System.out.println("Deciphering..." );
	     cipher.init(Cipher.DECRYPT_MODE, key);
	     byte[] newPlainBytes = cipher.doFinal(cipherBytes);
	     System.out.println("Result:");
	     System.out.println(printHexBinary(newPlainBytes));

	     System.out.println("Text:");
	     String newPlainText = new String(newPlainBytes); 
	     System.out.println(newPlainText);
		
    }
	*/
}