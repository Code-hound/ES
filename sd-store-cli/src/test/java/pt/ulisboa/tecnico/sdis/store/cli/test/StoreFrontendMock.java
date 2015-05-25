package pt.ulisboa.tecnico.sdis.store.cli.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.joda.time.DateTime;

import pt.ulisboa.tecnico.sdis.store.ws.CapacityExceeded_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;
import pt.ulisboa.tecnico.sdis.store.ws.DocUserPair;
import pt.ulisboa.tecnico.sdis.store.ws.SDStore;
import pt.ulisboa.tecnico.sdis.store.ws.UserDoesNotExist_Exception;
import mockit.MockUp;
import store.cli.StoreFrontend;
import store.cli.result.ListDocsResult;
import store.cli.result.LoadResult;
import ws.handler.HeaderHandler;

public class StoreFrontendMock extends MockUp<StoreFrontend> {
	
	private ArrayList<SDStore> endpoints;
	
	public StoreFrontendMock () {
		super();
	}

	public void setEndpoints(ArrayList<SDStore> endpoints) {
		this.endpoints = endpoints;
	}
		
	public byte[] load(DocUserPair docUser)
			throws DocDoesNotExist_Exception, UserDoesNotExist_Exception {
		List<LoadResult> results = new ArrayList<LoadResult>();
		for (SDStore endpoint : endpoints) {
			byte[] content = endpoint.load(docUser);
	    	String timestamp = getTime();
	    	String clientID = getID();
	    	System.out.println("Received load result: "+new String(content)+" | timestamp:"+new DateTime(timestamp)+" | clientID:"+clientID);
	    	results.add(new LoadResult(content, timestamp, clientID));	
		}
		if (!results.isEmpty()) {
			byte[] finalResult = LoadResult.quorumDecider(results);
			System.out.println("Final load result: "+(new String(finalResult)));
			return finalResult;
		}
		return null;
	}
	
	public List<String> listDocs(String username)
			throws UserDoesNotExist_Exception {
		List<ListDocsResult> results = new ArrayList<ListDocsResult>();	
		for (SDStore endpoint : endpoints) {
			List<String> docs = endpoint.listDocs(username);
			
        	String timestamp = getTime();
        	String clientID = getID();
        	results.add(new ListDocsResult(docs, timestamp, clientID));
		}
		if (!results.isEmpty())
			return ListDocsResult.quorumDecider(results);
		return null;
	}
	
	public String getTime() {
		return null;
	}
	
	public String getID() {
		return null;
	}
}