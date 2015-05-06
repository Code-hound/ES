package store.cli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.registry.JAXRException;

import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import uddi.UDDINaming;

public class StoreFrontend {
	
	/** Number of necessary clients **/
	private int numberOfClients;
	/** Array of server URLs **/
	private String[] endpointAddresses;
	/** Replica managers **/
	private ArrayList<StoreClient> clients;
	/** Webservice name **/
	private String wsName;
	/** URL of the UDDI Registry **/
    private String uddiURL;
    /** UDDI connector **/
    private UDDINaming uddiNaming;
	
	
	public StoreFrontend(String uddiURL, String wsName, int multiplicity) 
			throws JAXRException, StoreClientException {
		this.numberOfClients = multiplicity;
		this.wsName = wsName;
		this.uddiURL = uddiURL;
		this.clients = new ArrayList<StoreClient>();
		
		setEndpointAddresses();
		createReplicaManagers();
	}
	
	public void createDoc(String username, String docID)
			throws DocAlreadyExists_Exception {
		for (StoreClient client : clients) {
			client.createDoc(createPair(username, docID));
		}
	}
	
	public List<String> listDocs(String username)
			throws UserDoesNotExist_Exception {
		List<List<String>> docs = new ArrayList<List<String>>();
		for (StoreClient client : clients) {
			docs.add(client.listDocs(username)); //OBVIOUSLY WRONG
		}
		return docs.get(0);
	}

	public void store(String username, String docID, byte[] contents)
			throws CapacityExceeded_Exception, DocDoesNotExist_Exception,
			UserDoesNotExist_Exception {
		for (StoreClient client : clients) {
			client.store(createPair(username, docID), contents);
		}
	}

	public byte[] load(String username, String docID)
			throws DocDoesNotExist_Exception, UserDoesNotExist_Exception {
		List<byte[]> content = new ArrayList<byte[]>();
		for (StoreClient client : clients) {
			content.add(client.load(createPair(username, docID)));
		}
		return content.get(0);
	}
	
	private void createReplicaManagers() 
			throws JAXRException, StoreClientException {
		for (int i=0; i<numberOfClients; i++) {
			clients.add(new StoreClient(endpointAddresses[i], i+1));
		}
	}
	
	private void setEndpointAddresses() 
			throws JAXRException, StoreClientException {
    	this.endpointAddresses = StoreClient.uddiLookup(uddiURL, wsName, numberOfClients);
    }
	
	private DocUserPair createPair(String username, String docID) {
		DocUserPair pair = new DocUserPair();
		pair.setUserId(username);
		pair.setDocumentId(docID);
		return pair;
	}
}
