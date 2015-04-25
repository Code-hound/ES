package id.ws;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.Random;

import java.io.IOException;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.*;

import pt.ulisboa.tecnico.sdis.id.ws.*; // classes generated from WSDL

/*
 * Interface methods:
 * -createUser
 * -renewPassword
 * -removeUser
 * -requestAuthentication
 */

@WebService(
    endpointInterface="pt.ulisboa.tecnico.sdis.id.ws.SDId", 
    wsdlLocation="SD-ID.1_1.wsdl",
    name="SdId",
    portName="SDIdImplPort",
    targetNamespace="urn:pt:ulisboa:tecnico:sdis:id:ws",
    serviceName="SDId"
)

public class IdImpl implements SDId {
	
    private static String idUsername;
    private static String idPassword;
    private static String idEmail;

    private ArrayList<String[]> listData = new ArrayList<String[]>();
    
    
    /**
     * 
     * SD-ID IMPLEMENTATION
     *
     * Construtor da classe.
     * 
     * @param idUsername o username do utilizador com permissoes necessarias.
     * @param idPassord a password desse utilizador.
     * @param idEmail o email desse utilizador.
     * 
     * @author: Francisco Maria Calisto
     * @author: Joao Pedro Zeferino
     * 
     */

	public void createUser(String userId, String emailAddress)
			throws EmailAlreadyExists_Exception,
			       InvalidEmail_Exception,
			       InvalidUser_Exception,
			       UserAlreadyExists_Exception {
		
		// Gera senha alfanumerica e armazena em String.
		// Apresenta a senha na consola de serviço.

		if (userId == null || userId.length() == 0)
			throw new InvalidUser_Exception("Invalid username.", new InvalidUser());
		String str = userId + "";
		String userLetter1 = "" + str.charAt(0);

		int userIndex = listData.size() +1;
		
	    String userPassword = 
	    		userLetter1.toUpperCase() + userLetter1 + userLetter1 + userIndex;
	    
	    String[] auxEmail = emailAddress.split("@");
		String auxEmail1 = auxEmail[0];
		String auxEmail2 = auxEmail[1];
		
		
		
		if(auxEmail1.equals("") || auxEmail2.equals(""))
			throw new InvalidEmail_Exception("Invalid email", new InvalidEmail());
		
	    //verifica se o user id ou emailAdress ja existem
        for(int i = 0; i < listData.size(); i++) {
    		
    		if(userId.equals(listData.get(i)[0])) {
    			throw new UserAlreadyExists_Exception
    			("Invalid user ID", new UserAlreadyExists());
    		}
    		
    		if(emailAddress.equals(listData.get(i)[1])) {
    			throw new EmailAlreadyExists_Exception
    			("Invalid email", new EmailAlreadyExists());
    		}
    	}
        
    	String[] listUser = new String[3];
    	
    	listUser[0]=userId;
    	listUser[1]=emailAddress;
    	listUser[2]=userPassword;
    	
    	listData.add(listUser);
    }

	public void renewPassword(String userId)
			throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
		// Apresenta nova senha na consola de serviço.
		
		String oldPass;
		for (int i=0; i<listData.size(); i++) {
			if(userId.equals(listData.get(i)[0])) {
				oldPass = listData.get(i)[2];
				listData.get(i)[2] = oldPass + oldPass.substring(3,4);
				System.out.println(listData.get(i)[2]);
				return;
			}
		}
		throw new UserDoesNotExist_Exception
			("User does not exist", new UserDoesNotExist());
	}

	public void removeUser(String userId)
			throws UserDoesNotExist_Exception {
		// TODO Auto-generated method stub
			
		for(int i = 0; i < listData.size(); i++) {
			if(userId.equals(listData.get(i)[0])) {
				listData.remove(i);
				return;
			}
		}
		throw new UserDoesNotExist_Exception
			("User does not exist", new UserDoesNotExist());
	}

	public byte[] requestAuthentication(String userId, byte[] reserved)
			throws AuthReqFailed_Exception {		
		
		for(int i = 0; i < listData.size(); i++) {
			if(userId.equals(listData.get(i)[0]) 
						&& reserved.toString().equals(listData.get(i)[2])) {
					byte [] response = new byte[1];
					response[0] = '1';
					
					return response;
			}
		}
		throw new AuthReqFailed_Exception("Autentication Failed", new AuthReqFailed());
	}
	
}
