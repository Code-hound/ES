package pt.ulisboa.tecnico.sdis.id.cli;

import pt.ulisboa.tecnico.sdis.id.ws.*;
import pt.ulisboa.tecnico.sdis.id.exception.IdClient_Exception;


/**
 *
 *  ID CLIENT MAIN FUNCTION
 *
 *  Program that calls remote operations.
 *
 *  @author: Francisco Maria Calisto
 *
 */

// TODO

public class IdClientMain {

    public static void main(String[] args) throws Exception {
        
        String uddiURL = args[0];
        String name = args[1];
        
        IdClient client = new IdClient(uddiURL, name);
        
        // Check args
        
        if ( args.length < 2) {
        	System.err.println("No arguments");
        	System.err.printf("The usage java %s uddiURL name %n", IdClientMain.class.getName());
        	
        	return ;
        }
        
        try {
        	String id = "alice";
        	String email = "alice@tecnico.pt";
        	
        	byte[] pw = null;
        	
        	client.createUser(id,  email); //SUC
        	client.createUser(id,  email); //FAIL
        	client.renewPassword(id); //SUC
        	
        	/*
        	 * NOTA: Nao e possivel testar açgumas funcionalidades
        	 *       do Sistema dado que, por exemplo, no caso da
        	 *       funcao requestAuthentication nao e possivel
        	 *       retornar a password pedida.
        	 * 
        	 */
        	
        	client.requestAuthentication(id, pw); //FAIL
        	
        	client.removeUser(id); //SUC
        	client.renewPassword(id); //FAIL
        	client.removeUser(id); //FAIL
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        
        /*

        try {
            System.out.print("alice@");
            client.createUser("alice" , "alice@");

            System.out.print("@tecnico");
            client.createUser("alice" , "@tecnico");

            System.out.print("alice");
            client.createUser("alice" , "alice");

        } catch(InvalidEmail_Exception e) {
            System.out.println("Caught expected invalid email exception.");
        }

        try {
            System.out.print("alice@tecnico.pt");
            client.createUser("alice" , "alice@tecnico.pt");

        } catch(EmailAlreadyExists_Exception e) {
            System.out.println("Caught expected email already exist exception.");
        }

        try {
            System.out.print("alice@tecnico.pt");
            client.createUser(" " , "alicealice@tecnico.pt");

            System.out.print("alice@tecnico.pt");
            client.createUser(null , "alicealice@tecnico.pt");

        } catch(InvalidUser_Exception e) {
            System.out.println("Caught expected invalid user exception.");
        }
        */

        //TODO
        /*
        try {
            System.out.print("alice@tecnico.pt");
            System.out.println(client.createUser("alice" , "alice@tecnico.pt"));

        } catch(UserAlreadyExists_Exception e) {
            System.out.println("Caught expected user already exists exception.");
        }
        */
        
        /* ----- ATE AQUI TUDO BEM ----- */

    }

}
