package store.cli;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.registry.JAXRException;
import javax.xml.ws.BindingProvider;

import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore_Service;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import ws.uddi.UDDINaming;
import store.cli.*;

public class StoreClient {
	
	/** Client identifier **/
	private int clientID;
	/** Counter for Client identifiers **/
	private static int nextClientID = 1;
	/** Service frontend to communicate with servers **/
	private StoreFrontend frontend;
	/** Webservice name **/
	private String wsName;
	/** URL of the UDDI Registry **/
    private String uddiURL;
    /** UDDI connector **/
    private UDDINaming uddiNaming;
    /** output option **/
    private boolean verbose = false;
    /** Array of server URLs **/
	private String[] endpointAddresses;
	
	public StoreClient(String uddiURL, String wsName, int multiplicity,
			int readThreshold, int writeThreshold) 
			throws JAXRException, StoreClientException {
		
		this.wsName = wsName;
		this.uddiURL = uddiURL;
		
		uddiLookup(uddiURL, wsName, multiplicity);
		frontend = new StoreFrontend
				(endpointAddresses, multiplicity, 
				this.clientID, readThreshold, writeThreshold);
		frontend.createStubs();
	}
	
	public StoreClient(String uddiURL, String wsName, int multiplicity) 
			throws JAXRException, StoreClientException {
		this(uddiURL, wsName, multiplicity, 2, 2);
	}

	// PROXY METHODS - TO BE USED
	public void createDoc(String username, String docID)
			throws DocAlreadyExists_Exception, InterruptedException {
		DocUserPair docUser = createPair(username, docID);
		frontend.createDoc(docUser);
	}

	public List<String> listDocs(String userId)
			throws UserDoesNotExist_Exception {
		return frontend.listDocs(userId);
	}

	public void store(String username, String docID, byte[] contents)
			throws Exception {
		DocUserPair docUser = createPair(username, docID);
		frontend.store(docUser, contents);
	}

	public byte[] load(String username, String docID)
			throws Exception {
		DocUserPair docUser = createPair(username, docID);
		return frontend.load(docUser);
	}
	
	
	// WEBSERVICE COMMUNICATION FUNCTIONALITIES
	
    private void uddiLookup
    		(String uddiURL, String wsName, int multiplicity)
    		throws JAXRException, StoreClientException {
    	try {
	    	System.out.printf("Contacting UDDI at %s%n", uddiURL);
	    	uddiNaming = new UDDINaming(uddiURL);
	    	endpointAddresses = new String[multiplicity];
	    	
	    	System.out.printf("Looking for '%s'%n", wsName);
	    	Collection<String> addresses = uddiNaming.list(wsName);
	    	if (addresses.size() == 0) {
	    		throw new StoreClientException("Couldn't find any endpoints for the "
	    				+wsName+" service.");
	    	}
	    	if (endpointAddresses.length > addresses.size()) {
	    		throw new StoreClientException("The multiplicity for the "
	    				+wsName+" service currently can be no larger than "
	    				+addresses.size());
	    	}
	    	
	    	String[] addressesArray = addresses.toArray(new String[addresses.size()]);
	    	for (int i=0; i<multiplicity; i++) {
	    		endpointAddresses[i] = addressesArray[i];
	    	} 
	    	//Even though this looks excruciantingly bad, any other way can result in
	    	//endpointAddresses having length>multiplicity
	    	
	    	//Checks if any endpoints were found
	    	if (endpointAddresses.length == 0 || endpointAddresses[0].equals("")) {
	    		throw new StoreClientException("Could not find endpoints for service "
	    				+wsName);
	    	}
	    	// Checks if the number of required clients isn't larger than
	    	// the number of servers
	    	
	    	//return endpointAddresses;
	    } catch (JAXRException | IllegalArgumentException ex) {
			throw new StoreClientException("Failed UDDI lookup at "+uddiURL);
		}
    }
    
    private DocUserPair createPair(String username, String docID) {
		DocUserPair pair = new DocUserPair();
		pair.setUserId(username);
		pair.setDocumentId(docID);
		return pair;
	}
    
    public String[] getEndpointAddresses() {
    	return this.endpointAddresses;
    }
    
    public synchronized void incrementClientIDCounter() {
        nextClientID += 1;
    }
}
