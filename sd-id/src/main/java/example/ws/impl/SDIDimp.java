package example.ws.impl;

import javax.jws.*;

import pt.ulisboa.tecnico.sdis.id.ws.*; // classes generated from WSDL

@WebService(
    endpointInterface="example.ws.Hello", 
    wsdlLocation="Hello.wsdl",
    name="Hello",//MESMO NOME D POM.XML
    portName="HelloImplPort",
    targetNamespace="http://ws.example/",
    serviceName="HelloImplService"
)
public class SDIDimp implements SDId {

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
