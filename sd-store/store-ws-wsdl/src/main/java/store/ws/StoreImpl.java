package store.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.*;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.joda.time.DateTime;

import pt.ulisboa.tecnico.sdis.store.ws.*;
import store.ws.handler.HeaderHandler;


/*
 * Interface methods:
 * -createDoc (nothing on return header)
 * -listDocs (clientID+timestamp on return header)
 * -store (nothing on return header)
 * -load (clientID+timestamp on return header)
 */

@WebService(
	endpointInterface="pt.ulisboa.tecnico.sdis.store.ws.SDStore", 
	wsdlLocation="SD-STORE.1_1.wsdl",
	name="SDStore",
	portName="SDStoreImplPort",
	targetNamespace="urn:pt:ulisboa:tecnico:sdis:store:ws",
	serviceName="SDStore"
)

@HandlerChain(
	file="/handler-chain.xml"
)

public class StoreImpl implements SDStore {
	private int ID;
	private HashMap<String, Repository> userRepositories = 
			new HashMap<String, Repository>();
	@Resource
    private WebServiceContext webServiceContext;
	
	public StoreImpl(int id) {
		this.ID = id;
	}
	
	/*
     * From WSDL: <!-- Creates a new document in the provided user's repository.
     * In case this is the first operation on that user, a new repository is
     * created for the new user. Faults: a document already exists with the same
     * id -->
     */
    public void createDoc(DocUserPair docUserPair)
            throws DocAlreadyExists_Exception {
    	MessageContext messageContext = webServiceContext.getMessageContext();
    	//System.out.println("[STOREIMPL] Message: "+messageContext.toString());
    	String clientID = (String) messageContext.get(HeaderHandler.getIDProperty());
    	String timestamp = (String) messageContext.get(HeaderHandler.getTimeProperty());
    	//System.out.println("[STOREIMPL] ClientID:" + clientID+"   Timestamp: "+timestamp);
    	
    	if(docUserPair.getUserId() != null || docUserPair.getUserId() != "" ||
    			docUserPair.getDocumentId() != null || docUserPair.getDocumentId() != "") {
    	
	        Repository rep = userRepositories.get(docUserPair.getUserId());
	
	        if (rep == null) {
	            rep = new Repository();
	            userRepositories.put(docUserPair.getUserId(), rep);
	        }
	
	        if (rep.addNewDocument(docUserPair.getDocumentId(), clientID, timestamp) == false) {
	            DocAlreadyExists faultInfo = new DocAlreadyExists();
	            faultInfo.setDocId(docUserPair.getDocumentId());
	            throw new DocAlreadyExists_Exception("Document already exists", faultInfo);
	        }
    	}
    }

    /*
     * From WSDL: <!-- Lists the document ids of the user's repository. Faults:
     * user does not exist -->
     */
    public List<String> listDocs(String userId)
            throws UserDoesNotExist_Exception {
    	MessageContext messageContext = webServiceContext.getMessageContext();
    	
    	if(userId == null || userId.equalsIgnoreCase("") || userRepositories.get(userId) == null) {    	
            UserDoesNotExist faultInfo = new UserDoesNotExist();
            faultInfo.setUserId(userId);
            // fi.setMessage("User does not exist");
            throw new UserDoesNotExist_Exception("User does not exist **", faultInfo);    	
    	}
    	//= document.getLastChangedTime().toString();
    	//messageContext.put(HeaderHandler.getIDProperty(), etc);
    	
        Repository userRepository = userRepositories.get(userId);
        List<String> documentIDs = userRepository.listDocs();
        List<String> timestamps = userRepository.listTimestamps();
       
        messageContext.put(HeaderHandler.getTimeProperty(), timestamps);
        return documentIDs;
    }

    /*
     * From WSDL: <!-- Replaces the entire contents of the document by the
     * contents provided as argument. Faults: document does not exist, user does
     * not exist, repository capacity is exceeded. -->
     */
    public void store(DocUserPair docUserPair, byte[] newContents)
    		throws DocDoesNotExist_Exception,
            UserDoesNotExist_Exception {
    	MessageContext messageContext = webServiceContext.getMessageContext();
    	String clientID = (String) messageContext.get(HeaderHandler.getIDProperty());
    	String timestamp = (String) messageContext.get(HeaderHandler.getTimeProperty());
    	
    	String userId = docUserPair.getUserId();
    	String docId = docUserPair.getDocumentId();
    	
    	Repository rep = userRepositories.get(userId);
    	if (rep == null) {
    		throw new UserDoesNotExist_Exception("User \""+userId+"\" does not exist",
    				new UserDoesNotExist());
    	}
    	
    	Document document = rep.getDocument(docId);
    	if (document == null) {
    		throw new DocDoesNotExist_Exception
    				("User \""+userId+"\" has no document with ID \""+docId+"\"",
    				new DocDoesNotExist());
    	}
    	DateTime lastChange = document.getLastTimeChanged();
    	DateTime thisChange = new DateTime(timestamp);
    	int lastClientID = Integer.parseInt(document.getLastClientChanging());
    	int thisClientID = Integer.parseInt(clientID);
    	if (lastChange.isBefore(new DateTime(timestamp))
    			|| (lastChange.isEqual(thisChange) && lastClientID<thisClientID)) {
    		document.setNewContents(newContents, clientID, timestamp);
    	}
    }

    /*
     * From WSDL: <!-- Returns the current contents of the document. Fault: user
     * or document do not exist -->
     */
    public byte[] load(DocUserPair docUserPair)
            throws DocDoesNotExist_Exception, UserDoesNotExist_Exception {
    	MessageContext messageContext = webServiceContext.getMessageContext();
    	String userId = docUserPair.getUserId();
    	String docId = docUserPair.getDocumentId();
    	
    	Repository rep = userRepositories.get(userId);
    	if (rep == null) {
    		throw new UserDoesNotExist_Exception("User \""+userId+"\" does not exist",
    				new UserDoesNotExist());
    	}
    	
    	Document document = rep.getDocument(docId);
    	if (document == null) {
    		throw new DocDoesNotExist_Exception
    				("User \""+userId+"\" has no document with ID \""+docId+"\"",
    				new DocDoesNotExist());
    	}
    	
    	String timestamp = document.getLastTimeChanged().toString();
    	
    	//messageContext.put(HeaderHandler.getIDProperty(), etc);
    	messageContext.put(HeaderHandler.getTimeProperty(), timestamp);
    	
    	return document.getContents();
    }
    
    public void deleteFile(DocUserPair docUserPair)
            throws DocDoesNotExist_Exception, UserDoesNotExist_Exception {
    	String userId = docUserPair.getUserId();
    	String docId = docUserPair.getDocumentId();
    	
    	Repository rep = userRepositories.get(userId);
    	if (rep == null) {
    		throw new UserDoesNotExist_Exception("User \""+userId+"\" does not exist",
    				new UserDoesNotExist());
    	}
    	
    	if (!rep.delete(docId)) {
    		throw new DocDoesNotExist_Exception
			("User \""+userId+"Does not have a file \""+docId+"\"",
			new DocDoesNotExist());
    	}
    }

    // for testing

    protected void reset() {
        userRepositories.clear();
        // as specified in:
        // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2014-2015/labs/proj/test.html
        {
            Repository rep = new Repository();
            userRepositories.put("alice", rep);
        }
        {
            Repository rep = new Repository();
            userRepositories.put("bruno", rep);
        }
        {
            Repository rep = new Repository();
            userRepositories.put("carla", rep);
        }
        {
            Repository rep = new Repository();
            userRepositories.put("dimas", rep);
        }
    }
    
    private String[] getHeaderValues () {
    	return null;
    }
}