package pt.ulisboa.tecnico.sdid.id.cli;

import java.util.List;
import java.util.Map;

import javax.xml.ws.*;

import pt.ulisboa.tecnico.sdis.id.ws.*;

public class IdClient implements SDId {
	/** WS service */
	private SDId_Service idService = null;
	/** WS port (interface) */
	private SDId idInterface = null;
	/** WS endpoint address */
	private String wsURL = null;
    /** output option **/
    private boolean verbose = false;
    
    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public IdClient() throws IdClientException {
    	createStub();
    }
    
    public IdClient(String wsURL) throws IdClientException {
    	this.wsURL = wsURL;
        createStub();
    }
	
    public void createStub() {
    	if (verbose)
            System.out.println("Creating stub ...");
        idService = new SDId_Service();
        idInterface = idService.getSDIdImplPort();

        if (wsURL != null) {
            if (verbose)
                System.out.println("Setting endpoint address ...");
            BindingProvider bindingProvider = (BindingProvider) idInterface;
            Map<String, Object> requestContext = bindingProvider.getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsURL);
        }
    }
	
	public void createUser(String userId, String emailAddress)
			throws EmailAlreadyExists_Exception, InvalidEmail_Exception,
			InvalidUser_Exception, UserAlreadyExists_Exception {
		idInterface.createUser(userId, emailAddress);
	}

	public void renewPassword(String userId) throws UserDoesNotExist_Exception {
		idInterface.renewPassword(userId);
	}

	public void removeUser(String userId) throws UserDoesNotExist_Exception {
		idInterface.removeUser(userId);
	}

	public byte[] requestAuthentication(String userId, byte[] reserved)
			throws AuthReqFailed_Exception {
		return idInterface.requestAuthentication(userId, reserved);
	}
}