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

        IdClient client = new IdClient();

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

        //TODO
        /*
        try {
            System.out.print("alice@tecnico.pt");
            System.out.println(client.createUser("alice" , "alice@tecnico.pt"));

        } catch(UserAlreadyExists_Exception e) {
            System.out.println("Caught expected user already exists exception.");
        }
        */

    }

}
