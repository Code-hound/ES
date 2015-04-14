package store.cli;

import java.util.List;
import java.util.Map;
import javax.xml.ws.*;

import pt.ulisboa.tecnico.sdis.store.ws.*;

public class StoreClient implements SDStore {
	/** WS service */
	private SDStore_Service storeService = null;
	/** WS port (interface) */
	private SDStore storeInterface = null;
	/** WS endpoint address */
    // default value is defined by WSDL
    private String wsURL = null;
    /** output option **/
    private boolean verbose = false;
    
    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public StoreClient() throws StoreClientException {
    	createStub();
    }
    
    public StoreClient(String wsURL) throws StoreClientException {
    	this.wsURL = wsURL;
        createStub();
    }
    
    public void createStub() {
    	if (verbose)
            System.out.println("Creating stub ...");
        storeService = new SDStore_Service();
        storeInterface = storeService.getSDStoreImplPort();

        if (wsURL != null) {
            if (verbose)
                System.out.println("Setting endpoint address ...");
            BindingProvider bindingProvider = (BindingProvider) storeInterface;
            Map<String, Object> requestContext = bindingProvider.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsURL);
        }
    }
	
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

}
