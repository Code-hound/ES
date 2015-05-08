package store.cli.old;

//import java.util.Collection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.registry.JAXRException;
import javax.xml.ws.*;

import store.cli.StoreClientException;
import uddi.UDDINaming;
import pt.ulisboa.tecnico.sdis.store.ws.*;


public class StoreClient implements SDStore {
	
	// PROXY METHODS - TO BE USED
	public void createDoc(DocUserPair docUserPair)
			throws DocAlreadyExists_Exception {
		storeInterface.createDoc(docUserPair);
	}

	public List<String> listDocs(String userId)
			throws UserDoesNotExist_Exception {
		return storeInterface.listDocs(userId);
	}

	public void store(DocUserPair docUserPair, byte[] contents)
			throws CapacityExceeded_Exception, DocDoesNotExist_Exception,
			UserDoesNotExist_Exception {
		storeInterface.store(docUserPair, contents);
	}

	public byte[] load(DocUserPair docUserPair)
			throws DocDoesNotExist_Exception, UserDoesNotExist_Exception {
		return storeInterface.load(docUserPair);
	}
	
	// WEBSERVICE COMMUNICATION FUNCTIONALITIES
	private int ID;
	/** WS service */
	private SDStore_Service storeService = null;
	/** WS port (interface) */
	//Where the magic happens
	private SDStore storeInterface = null;
	
    /** output option **/
    private boolean verbose = false;
    
    private static UDDINaming uddiNaming;
    private String endpointAddress = null;
    private static String[] endpoints = null;
    
    
    
    
    public StoreClient(String endpoint, int ID) 
			throws JAXRException, StoreClientException {
		this.ID = ID;
		this.endpointAddress = endpoint;
		//uddiLookup();
		//System.out.println("Size: "+endpointAddresses.length);
		//for (String s : endpointAddresses) System.out.println(s);
		createStubs();
	}

	public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public static String[] uddiLookup
    		(String uddiURL, String wsName, int multiplicity)
    		throws JAXRException, StoreClientException {
    	try {
	    	System.out.printf("Contacting UDDI at %s%n", uddiURL);
	    	uddiNaming = new UDDINaming(uddiURL);
	    	
	    	System.out.printf("Looking for '%s'%n", wsName);
	    	Collection<String> addresses = uddiNaming.list(wsName);
	    	endpoints = addresses.toArray(new String[addresses.size()]);
	    	
	    	//Checks if any endpoints were found
	    	if (endpoints.length == 0 || endpoints[0].equals("")) {
	    		throw new StoreClientException("Could not find endpoints for service "
	    				+wsName);
	    	}
	    	// Checks if the number of required clients isn't larger than
	    	// the number of servers
	    	if (endpoints.length < multiplicity) {
	    		throw new StoreClientException("The multiplicity for the "
	    				+wsName+" service currently can be no larger than "
	    				+endpoints.length);
	    	}
	    	
	    	return endpoints;
	    } catch (JAXRException | IllegalArgumentException ex) {
			throw new StoreClientException("Failed UDDI lookup at "+uddiURL);
		}
    	
    }
    
    private void createStubs() {
    	if (verbose)
            System.out.println("Creating stub ...");
    	storeService = new SDStore_Service();
        storeInterface = storeService.getSDStoreImplPort();

        //if (uddiURL != null) {
            if (verbose)
                System.out.println("Setting endpoint address ...");
            BindingProvider bindingProvider = (BindingProvider) storeInterface;
            Map<String, Object> requestContext = bindingProvider.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            		this.endpointAddress);
        //}   
    }
}
