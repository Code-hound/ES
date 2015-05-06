package store.cli;

//import java.util.Collection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.registry.JAXRException;
import javax.xml.ws.*;

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
	/** WS service */
	private SDStore_Service storeService = null;
	/** WS port (interface) */
	//Where the magic happens
	private SDStore storeInterface = null;
	/** Webservice name **/
	private String wsName;
    private String uddiURL;
    /** output option **/
    private boolean verbose = false;
    
    private UDDINaming uddiNaming;
    private String[] endpointAddresses = null;
    
    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    /*
    public StoreClient() throws StoreClientException {
    	createStub();
    }
    */
    public StoreClient(String uddiURL, String wsName) 
    		throws StoreClientException {
    	this.uddiURL = uddiURL;
    	this.wsName = wsName;
    	try {
    		uddiLookup();
    		//System.out.println("Size: "+endpointAddresses.length);
    		//for (String s : endpointAddresses) System.out.println(s);
    	} catch (JAXRException | IllegalArgumentException ex) {
    		throw new StoreClientException("Failed UDDI lookup at "+uddiURL);
    	}
    	if (endpointAddresses.length == 0 || endpointAddresses[0].equals("")) {
    		throw new StoreClientException("Could not find endpoints for service "+wsName);
    	}
    	createStub();
    }
    
    private void uddiLookup() throws JAXRException {
    	System.out.printf("Contacting UDDI at %s%n", uddiURL);
    	uddiNaming = new UDDINaming(this.uddiURL);
    	
    	System.out.printf("Looking for '%s'%n", wsName);
    	Collection<String> addresses = uddiNaming.list(wsName);
        endpointAddresses = addresses.toArray(new String[addresses.size()]);
    }
    
    private void createStub() {
    	if (verbose)
            System.out.println("Creating stub ...");
    	storeService = new SDStore_Service();
        storeInterface = storeService.getSDStoreImplPort();

        if (uddiURL != null) {
            if (verbose)
                System.out.println("Setting endpoint address ...");
            BindingProvider bindingProvider = (BindingProvider) storeInterface;
            Map<String, Object> requestContext = bindingProvider.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            		endpointAddresses[0]);
        }
    }
    
    public String[] getEndpointAddresses() {
    	return endpointAddresses;
    }
}
