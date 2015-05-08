package pt.ulisboa.tecnico.sdis.id.cli;

import java.util.List;
import java.util.Map;

import javax.xml.ws.*;

import pt.ulisboa.tecnico.sdis.id.ws.*;
import pt.ulisboa.tecnico.sdis.id.exception.IdClient_Exception;

/**
*
*  ID CLIENT
*  
*  ID Client for the Client Service.
*
*  @author: Francisco Maria Calisto
*
*/

public class IdClient implements SDId {
	/** WS service */
	private SDId_Service idService = null;
	/** WS port (interface) */
	private SDId idInterface = null;
	/** WS endpoint address */
	private String wsURL = null;
	/** IdClient name */
	private String name = null;
    /** output option **/
    private boolean verbose = false;
    
    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public IdClient() throws IdClient_Exception {
    	createStub();
    }
    
    public IdClient(String wsURL, String name) throws IdClient_Exception {
    	this.wsURL = wsURL;
    	this.name = name;
    	
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
	
    static void reset() {
        users.clear();
        // as specified in:
        // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2014-2015/labs/proj/test.html
        users.put("alice", "Aaa1".getBytes());
        users.put("bruno", "Bbb2".getBytes());
        users.put("carla", "Ccc3".getBytes());
        users.put("duarte", "Ddd4".getBytes());
        users.put("eduardo", "Eee5".getBytes());
    }
}