package store.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;

public class Repository {
	HashMap<String, Document> documents;
	
	public Repository() {
		// Each repository is of type documentID:Document
        documents = new HashMap<String, Document>();
    }
	
	public boolean addNewDocument(String docId, String clientID, String timestamp) {
        if (documents.get(docId) != null)
            return false;
        documents.put(docId, new Document(clientID, timestamp));
        return true;
    }

    public Document getDocument(String docId) {
        return documents.get(docId);
    }

    public int getTotalSize() {
        int sizeInBytes = 0;
        for (String docIdKey : documents.keySet()) {
        	sizeInBytes += documents.get(docIdKey).getSize();
        }
        return sizeInBytes;
    }

    public int getTotalSizeWithoutDocument(String docId) {
    	int sizeInBytes = 0;
        for (String docIdKey : documents.keySet()) {
        	if (docIdKey != docId) {
        		sizeInBytes += documents.get(docId).getSize();
        	}
        }
        return sizeInBytes;
    }

    public List<String> listDocs() {
        return new ArrayList<String>(documents.keySet());
    }
    
    public List<String> listTimestamps() {
    	Document document;
    	List<String> timestamps = new ArrayList<String>();
    	for (String docIdKey : documents.keySet()) {
    		document = documents.get(docIdKey);
    		timestamps.add(document.getLastTimeChanged().toString());
    	}
    	return timestamps;
    }
    
    public boolean delete(String docId) throws DocDoesNotExist_Exception {
    	Document doc = this.documents.remove(docId);
    	if (doc != null) {
    		return true;
    	}
    	return false;
    }
}
