package pt.ulisboa.tecnico.sdis.id.ws.impl;

import javax.xml.ws.Endpoint;
import javax.naming.*;
import javax.naming.directory.*;
import javax.security.auth.login.*;
import javax.security.auth.callback.*;
import javax.security.auth.Subject;

import java.util.*;

import uddi.UDDINaming;

@SuppressWarnings("unused")
public class IdMain {

    public static void main(String[] args) throws Exception {
    	
    	/*
    	 * 
    	 * Minimal Implementation by Professor
    	 * 
    	 */
    	
        // Check arguments
		if (args.length == 0 || args.length == 2) {
			System.err.println("Argument(s) missing!");
			System.err.println("Usage: java " + IdMain.class.getName()
					+ " wsURL OR uddiURL wsName wsURL");
			return;
		}
		
		String uddiURL = null;
		String wsName = null;
		String wsURL = null;
		
		IdImpl impl = null;
		
		if (args.length == 1) {
			wsURL = args[0];
			impl = new IdImpl(wsURL);
		} else if (args.length >= 3) {
			uddiURL = args[0];
			wsName = args[1];
			wsURL = args[2];
			impl = new IdImpl(uddiURL, wsName, wsURL);
		}
        
        Endpoint endpoint = null;
        UDDINaming uddiNaming = null;

        TestControl testImpl = null;
        
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
            
            // publish to UDDI
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

    }
    
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
    
    

}