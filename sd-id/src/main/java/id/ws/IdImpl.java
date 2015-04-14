package id.ws;

import javax.jws.*;

import pt.ulisboa.tecnico.sdis.id.ws.*; // classes generated from WSDL

@WebService(
    endpointInterface="pt.ulisboa.tecnico.sdis.id.ws.SDId", 
    wsdlLocation="SD-ID.1_1.wsdl",
    name="SdId",//MESMO NOME D POM.XML done
    portName="SDIdImplPort",
    targetNamespace="urn:pt:ulisboa:tecnico:sdis:id:ws",
    serviceName="SDId"
    
    /*  
  xmlns:tns="urn:pt:ulisboa:tecnico:sdis:id:ws"
  xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
  xmlns:soaphttp="http://schemas.xmlsoap.org/soap/http"
  xmlns:wsamd="http://www.w3.org/2007/05/addressing/metadata">

     * procurar onde meter isto 
     * 
     * 
     * */
)
public class IdImpl implements SDId {

    public String sayHello(String name) {
        return "Hello " + name + "!";
    }

	public void createUser(String userId, String emailAddress)
			throws EmailAlreadyExists_Exception, InvalidEmail_Exception,
			InvalidUser_Exception, UserAlreadyExists_Exception {
		// TODO Auto-generated method stub
		
	}

	public void renewPassword(String userId) throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
		
	}

	public void removeUser(String userId) throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
		
	}

	public byte[] requestAuthentication(String userId, byte[] reserved)
			throws AuthReqFailed_Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
