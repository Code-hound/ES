package store.cli;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.registry.JAXRException;
import javax.xml.ws.BindingProvider;

import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore_Service;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import uddi.UDDINaming;
import store.cli.*;

public class StoreClient {

	public StoreClient(String uddiURL, String wsName, int multiplicity) 
			throws JAXRException, StoreClientException {
		this.wsName = wsName;
		this.uddiURL = uddiURL;
		String[] addresses = uddiLookup(uddiURL, wsName, multiplicity);
		frontend = new StoreFrontend(addresses, multiplicity);
		//System.out.println("Size: "+endpointAddresses.length);
		//for (String s : endpointAddresses) System.out.println(s);
	}

	// PROXY METHODS - TO BE USED
	public void createDoc(String username, String docID)
			throws DocAlreadyExists_Exception {
		DocUserPair docUser = createPair(username, docID);
		frontend.createDoc(docUser);
	}

	public List<String> listDocs(String userId)
			throws UserDoesNotExist_Exception {
		return frontend.listDocs(userId);
	}

	public void store(String username, String docID, byte[] contents)
			throws CapacityExceeded_Exception, DocDoesNotExist_Exception,
			UserDoesNotExist_Exception {
		DocUserPair docUser = createPair(username, docID);
		frontend.store(docUser, contents);
	}

	public byte[] load(String username, String docID)
			throws DocDoesNotExist_Exception, UserDoesNotExist_Exception {
		DocUserPair docUser = createPair(username, docID);
		return frontend.load(docUser);
	}
	
	// WEBSERVICE COMMUNICATION FUNCTIONALITIES
	private StoreFrontend frontend;
	/** Webservice name **/
	private String wsName;
	/** URL of the UDDI Registry **/
    private String uddiURL;
    /** UDDI connector **/
    private UDDINaming uddiNaming;
    /** output option **/
    private boolean verbose = false;
    
    /*
    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    */
    
    public String[] uddiLookup
    		(String uddiURL, String wsName, int multiplicity)
    		throws JAXRException, StoreClientException {
    	try {
	    	System.out.printf("Contacting UDDI at %s%n", uddiURL);
	    	uddiNaming = new UDDINaming(uddiURL);
	    	;
	    	
	    	System.out.printf("Looking for '%s'%n", wsName);
	    	Collection<String> addresses = uddiNaming.list(wsName);
	    	String[] addressesArray = addresses.toArray(new String[addresses.size()]);
	    	
	    	//Checks if any endpoints were found
	    	if (addressesArray.length == 0 || addressesArray[0].equals("")) {
	    		throw new StoreClientException("Could not find endpoints for service "
	    				+wsName);
	    	}
	    	// Checks if the number of required clients isn't larger than
	    	// the number of servers
	    	if (addressesArray.length < multiplicity) {
	    		throw new StoreClientException("The multiplicity for the "
	    				+wsName+" service currently can be no larger than "
	    				+addresses.size());
	    	}
	    	return addressesArray;
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
}
