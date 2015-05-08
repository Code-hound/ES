package store.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist;
import pt.ulisboa.tecnico.sdis.store.ws.DocDoesNotExist_Exception;

public class Repository {
	HashMap<String, Document> documents;
	
	public Repository() {
        documents = new HashMap<String, Document>();
    }
	
	public boolean addNewDocument(String docId) {
        if (documents.get(docId) != null)
            return false;
        documents.put(docId, new Document());
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

    public List<String> listDocs(String userId) {
        return new ArrayList<String>(documents.keySet());
    }
    
    public boolean delete(String docId) throws DocDoesNotExist_Exception {
    	Document doc = this.documents.remove(docId);
    	if (doc != null) {
    		return true;
    	}
    	return false;
    }
}
