package store.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.registry.JAXRException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Response;

import org.joda.time.DateTime;

import pt.ulisboa.tecnico.sdis.id.ws.crypto.SymCrypto;
import pt.ulisboa.tecnico.sdis.store.ws.*;
import ws.handler.HeaderHandler;
import store.cli.result.*;

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
	/** Thresholds **/
	private int readThreshold;
	private int writeThreshold;
	
	
	public StoreFrontend(String[] addresses, int multiplicity, int clientID,
			int readThreshold, int writeThreshold) 
			throws JAXRException, StoreClientException {
		this.multiplicity = multiplicity;
		this.ID = clientID;
		this.readThreshold = readThreshold;
		this.writeThreshold = writeThreshold;
		setEndpointAddresses(addresses);
		for (String address : endpointAddresses) {
			System.out.println(address);
		}
	}
	
	public void createDoc(DocUserPair docUser)
			throws DocAlreadyExists_Exception {
		for (SDStore endpoint : endpoints) {
			setHeaders(endpoint);
			endpoint.createDoc(docUser);
		}
	}
	
	/*
	public void createDoc(DocUserPair docUser)
			throws DocAlreadyExists_Exception, InterruptedException {
		List<Response<CreateDocResponse>> responses = 
				new ArrayList<Response<CreateDocResponse>>();
		for (SDStore endpoint : endpoints) {
			setHeaders(endpoint);
			Response<CreateDocResponse> response = endpoint.createDocAsync(docUser);
			responses.add(response);
		}
		int replied = 0;
		while (replied < writeThreshold) {
			for (Response<CreateDocResponse> response : responses) {
				if (response!=null && response.isDone()) {
					response = null;
					replied+=1;
					System.out.println("Created doc in "+replied+" replicas");
					if (replied == writeThreshold) { 
						System.out.println("Created doc in "+replied+" replicas - final");
						return;
					}
				}
			}
			Thread.sleep(100);
		}
	}
	*/
	
	public List<String> listDocs(String username)
			throws UserDoesNotExist_Exception {
		List<ListDocsResult> results = new ArrayList<ListDocsResult>();	
		for (SDStore endpoint : endpoints) {
			setHeaders(endpoint);
			List<String> docs = endpoint.listDocs(username);
			
			BindingProvider bindingProvider = (BindingProvider) endpoint;
        	Map<String, Object> requestContext = bindingProvider.getResponseContext();
        	
        	String timestamp = (String) requestContext.get(HeaderHandler.getTimeProperty());
        	String clientID = (String) requestContext.get(HeaderHandler.getIDProperty());
        	results.add(new ListDocsResult(docs, timestamp, clientID));
        	if (results.size() == this.readThreshold) {
        		break;
        	}
		}
		if (!results.isEmpty()) {
			return ListDocsResult.quorumDecider(results);
		}
		return null;
	}
	
	public void store(DocUserPair docUser, byte[] contents)
			throws Exception {
		for (SDStore endpoint : endpoints) {
			System.out.println("User wants to store plaintext: ");
			System.out.println(new String(contents));
			SymCrypto cryptographer = new SymCrypto();
			String encryptedContent = cryptographer.encryptDocument(new String(contents));
			System.out.println("Sending encrypted: ");
			System.out.println(encryptedContent);
			setHeaders(endpoint);
			endpoint.store(docUser, encryptedContent.getBytes());
		}
	}
	
	/*
	public void store(DocUserPair docUser, byte[] contents)
			throws CapacityExceeded_Exception, DocDoesNotExist_Exception,
			UserDoesNotExist_Exception, InterruptedException {
		List<Response<StoreResponse>> responses = 
				new ArrayList<Response<StoreResponse>>();
		for (SDStore endpoint : endpoints) {
			setHeaders(endpoint);
			Response<StoreResponse> response = endpoint.storeAsync(docUser, contents);
			responses.add(response);
		}
		int replied = 0;
		while (replied < writeThreshold) {
			for (Response<StoreResponse> response : responses) {
				if (response!=null && response.isDone()) {
					response = null;
					replied+=1;
					System.out.println("Wrote to "+replied+" replicas");
					if (replied == writeThreshold) { 
						System.out.println("Wrote to "+replied+" replicas - final");
						return;
					}
				}
			}
			Thread.sleep(100);
		}
	}
	*/
	/*
	public byte[] load(DocUserPair docUser)
			throws DocDoesNotExist_Exception, 
			UserDoesNotExist_Exception, 
			InterruptedException, 
			ExecutionException {
		List<Response<LoadResponse>> responses = 
				new ArrayList<Response<LoadResponse>>();
		List<LoadResult> results = new ArrayList<LoadResult>();
		for (SDStore endpoint : endpoints) {
			setHeaders(endpoint);
			byte[] content = endpoint.load(docUser);
			
			BindingProvider bindingProvider = (BindingProvider) endpoint;
        	Map<String, Object> responseContext = bindingProvider.getResponseContext();
        	
        	String timestamp = (String) responseContext.get(HeaderHandler.getTimeProperty());
        	String clientID = (String) responseContext.get(HeaderHandler.getIDProperty());
        	
        	results.add(new LoadResult(content, timestamp, clientID));	
        	
		}
		if (!results.isEmpty()) {
			return LoadResult.quorumDecider(results);
		}
		return null;
	}
	*/
	public byte[] load(DocUserPair docUser)
			throws Exception {
		//List<byte[], String> content = new ArrayList<byte[]>();
		List<LoadResult> results = new ArrayList<LoadResult>();
		for (SDStore endpoint : endpoints) {
			setHeaders(endpoint);
			byte[] encryptedContent = endpoint.load(docUser);
			System.out.println("Server returned encrypted:");
			System.out.println(new String(encryptedContent));
			SymCrypto cryptographer = new SymCrypto();
			String decryptedContent = cryptographer.decryptDocument(new String(encryptedContent));
			System.out.println("Returning plaintext:");
			System.out.println(decryptedContent);
			BindingProvider bindingProvider = (BindingProvider) endpoint;
        	Map<String, Object> responseContext = bindingProvider.getResponseContext();
        	
        	String timestamp = (String) responseContext.get(HeaderHandler.getTimeProperty());
        	String clientID = (String) responseContext.get(HeaderHandler.getIDProperty());
        	
        	results.add(new LoadResult(decryptedContent.getBytes(), timestamp, clientID));
        	if (results.size() == this.readThreshold) {
        		break;
        	}
		}
		if (!results.isEmpty()) {
			return LoadResult.quorumDecider(results);
		}
		return null;
	}
	
	private void setEndpointAddresses(String[] addresses) 
			throws JAXRException, StoreClientException {
		this.endpoints = new ArrayList<SDStore>();
    	this.endpointAddresses = addresses;
    }
	
	public void createStubs() {
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
        }
        System.out.println("ENDPOINTS:");
        for (SDStore endpoint : endpoints) {
        	System.out.println(endpoint);
        }
    }
	
	private void setHeaders(SDStore endpoint) {
		BindingProvider bindingProvider = (BindingProvider) endpoint;
    	Map<String, Object> requestContext = bindingProvider.getRequestContext();
		requestContext.put(HeaderHandler.getIDProperty(), 
    			Integer.toString(this.ID));
    	requestContext.put(HeaderHandler.getTimeProperty(),
    			new DateTime().toString());
	}
}
