package store.cli;

import java.util.ArrayList;
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
import store.ws.handler.HeaderHandler;

public class StoreFrontend {

	/** WS service */
	private SDStore_Service storeService = null;
	/** Number of requested endpoints **/
	private int multiplicity;
	/** This frontend's ID **/
	private int ID;
	/** Array of server URLs **/
	private String[] endpointAddresses;
	/** Replica managers **/
	private ArrayList<SDStore> endpoints;
	
	
	public StoreFrontend(String[] addresses, int multiplicity, int clientID) 
			throws JAXRException, StoreClientException {
		this.multiplicity = multiplicity;
		this.ID = clientID;
		setEndpointAddresses(addresses);
		createStubs();
	}
	
	public void createDoc(DocUserPair docUser)
			throws DocAlreadyExists_Exception {
		for (SDStore endpoint : endpoints) {
			endpoint.createDoc(docUser);
		}
	}
	
	public List<String> listDocs(String username)
			throws UserDoesNotExist_Exception {
		List<List<String>> docs = new ArrayList<List<String>>();
		for (SDStore endpoint : endpoints) {
			docs.add(endpoint.listDocs(username)); //OBVIOUSLY WRONG
		}
		return docs.get(0);
	}

	public void store(DocUserPair docUser, byte[] contents)
			throws CapacityExceeded_Exception, DocDoesNotExist_Exception,
			UserDoesNotExist_Exception {
		for (SDStore endpoint : endpoints) {
			endpoint.store(docUser, contents);
		}
	}

	public byte[] load(DocUserPair docUser)
			throws DocDoesNotExist_Exception, UserDoesNotExist_Exception {
		List<byte[]> content = new ArrayList<byte[]>();
		for (SDStore endpoint : endpoints) {
			content.add(endpoint.load(docUser));
		}
		return content.get(0);
	}
	
	private void setEndpointAddresses(String[] addresses) 
			throws JAXRException, StoreClientException {
		this.endpoints = new ArrayList<SDStore>();
    	this.endpointAddresses = addresses;
    }
	
	private void createStubs() {
        System.out.println("Creating stub ...");
    	storeService = new SDStore_Service();
    	
        System.out.println("Setting endpoint addresses ...");
        for (int i=0; i<multiplicity; i++) {
        	SDStore endpoint = storeService.getSDStoreImplPort();
        	BindingProvider bindingProvider = (BindingProvider) endpoint;
        	Map<String, Object> requestContext = bindingProvider.getRequestContext();
        	requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
        		this.endpointAddresses[i]);
        	endpoints.add(endpoint);
        	requestContext.put(HeaderHandler.CONTEXT_PROPERTY, "This is my message");
        }
        for (SDStore endpoint : endpoints) {
        	System.out.println(endpoint);
        }
    }
}
