package id.ws;

import javax.xml.ws.Endpoint;
import javax.naming.*;
import javax.naming.directory.*;
import javax.security.auth.login.*;
import javax.security.auth.Subject;

import java.util.Hashtable;
import javax.security.auth.callback.*;

import uddi.UDDINaming;

public class IdMain {

    public static void main(String[] args) {
        // Check arguments
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL wsName wsURL%n", IdMain.class.getName());
            return;
        }

        String uddiURL = args[0];
        String name = args[1];
        String url = args[2];
        
        Endpoint endpoint = null;
        UDDINaming uddiNaming = null;

        try {
            endpoint = Endpoint.create(new IdImpl(args));

            // publish endpoint
            System.out.printf("Starting %s%n", url);
            endpoint.publish(url);
            
            // publish to UDDI
            System.out.printf("Publishing '%s' to UDDI at %s%n", name, uddiURL);
            uddiNaming = new UDDINaming(uddiURL);
            uddiNaming.rebind(name, url);            

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
                    System.out.printf("Stopped %s%n", url);
                }
            } catch(Exception e) {
                System.out.printf("Caught exception when stopping: %s%n", e);
            }
            try {
                if (uddiNaming != null) {
                    // delete from UDDI
                    uddiNaming.unbind(name);
                    System.out.printf("Deleted '%s' from UDDI%n", name);
                }
            } catch(Exception e) {
                System.out.printf("Caught exception when deleting: %s%n", e);
            }
        }
        
        /**
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
    	Subject.doAs(lc.getSubject(), new IdImpl(args));

    }

}