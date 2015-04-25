package pt.ulisboa.tecnico.sdid.id.cli;

import sd-id.InvalidEmail_Exception;
import sd-id.EmailAlreadyExists_Exception;
import sd-id.InvalidUser_Exception;
import sd-id.UserAlreadyExists_Exception;
import sd-id.UserDoesNotExist_Exception;
import sd-id.AuthReqFailed_Exception;


/**
 *
 *  ID CLIENT MAIN FUNCTION
 *
 *  Program that calls remote operations.
 *
 *  @author: Francisco Maria Calisto
 *
 */

public class IdClientMain {

    public static void main(String[] args) throws Exception {

        IdClient client = new IdClient();

        try {
            System.out.print("alice@");
            System.out.println(client.createUser("alice" , "alice@"));

            System.out.print("@tecnico");
            System.out.println(client.createUser("alice" , "@tecnico"));

            System.out.print("alice");
            System.out.println(client.createUser("alice" , "alice"));

        } catch(InvalidEmail_Exception e) {
            System.out.println("Caught expected invalid email exception.");
        }

    }

}
