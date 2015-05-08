package store.ws;

import javax.xml.ws.Endpoint;

import uddi.UDDINaming;

// To confirm correct service publication:
// http://localhost:8081/juddi-gui/businessBrowse.jsp

public class StoreMain {
	private static int numberServers;
	private static String uddiURL;
    private static String name;
	private static String[] urls;
    
	public static void main (String[] args) {
		
		if (!checkArguments(args)) {
			printError();
			return;
		}
        
        Endpoint[] endpoints = new Endpoint[numberServers];
        /*
        System.out.println("Number of servers: "+numberServers);
        System.out.println("URLs:");
        for (String s : urls) {
        	System.out.printf("%s", s);
        }
        */
        //Endpoint endpoint = null;
        UDDINaming uddiNaming = null;
        try {
        	
        	for (int i=0; i<numberServers; i++) {
        		System.out.printf("Creating replica #%d%n", i+1);
        		endpoints[i] = Endpoint.create(new StoreImpl(i+1));
        		System.out.printf("Publishing replica #%d to %s%n", i+1, urls[i]);
        		endpoints[i].publish(urls[i]);
        	}
            
            // publish to UDDI
            System.out.printf("Publishing '%s' with %d replicas to UDDI at %s%n", 
            		name, numberServers, uddiURL);
            uddiNaming = new UDDINaming(uddiURL);
            for (int i=0; i<numberServers; i++) {
            	uddiNaming.bind(name, urls[i]);
            }

            // wait
            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
            System.in.read();

        } catch(Exception e) {
            System.out.printf("Caught exception: %s%n", e);
            e.printStackTrace();
        } finally {
            try {
                if (endpoints[0] != null) {
                    // stop endpoint
                    for (int i=0; i<numberServers; i++) {
                    	endpoints[i].stop();
                    	System.out.printf("Stopped %s%n", urls[i]);
                    }   
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

    }
	
	//Despite what it may seem at first
	//these condition checks are not possible to group together
	private static boolean checkArguments (String[] args) {
		if (args.length<4) {
			return false;
		}
		try {
			numberServers = Integer.parseInt(args[2]);
		} catch (NumberFormatException ex) {
			return false;
		}
		
		if (args.length != numberServers+3) {
            return false;
        }
		
		uddiURL = args[0];
		name = args[1];
		
        urls = new String[numberServers];
        for (int i=0, arg=3; i<numberServers; i++, arg++) {
        	if (!args[arg].startsWith("http")) {
        		return false;
        	}
        	urls[i] = args[arg];
        }
        return true;
	}
	
	private static void printError() {
		 System.err.println("Incorrect number or type of arguments");
         System.err.println("Make sure the number of wsURLs matches "
         		+ "the number of desired replicas and that they are "
        		+ "in the proper URL format");
         System.err.printf("Usage: java %s uddiURL wsName "
          		+ "numberOfReplicas [wsURLs]%n", StoreMain.class.getName());
	}
}