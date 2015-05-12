package pt.ulisboa.tecnico.sdis.id.cli;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


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
        
     // check args and get plaintext
        if (args.length != 1) {
            System.err.println("args: (text)");
            return;
        }
        final String plainText = args[0];
        final byte[] plainBytes = plainText.getBytes();

        System.out.println("Text:");
        System.out.println(plainText);
        System.out.println("Bytes:");
        System.out.println(printHexBinary(plainBytes));
        

        // get a TripleDES private key
        System.out.println("Generating TripleDES key ...");
        KeyGenerator keyGen = KeyGenerator.getInstance("TripleDES");
        keyGen.init(168);
        Key key = keyGen.generateKey();
        System.out.println("Key:");
        System.out.println(printHexBinary(key.getEncoded()));

        // get a TripleDES cipher object and print the provider
        Cipher cipher = Cipher.getInstance("TripleDES/ECB/PKCS5Padding");
        System.out.println(cipher.getProvider().getInfo());

        // encrypt using the key and the plaintext
        System.out.println("Text:");
        System.out.println(plainText);
        System.out.println("Bytes:");
        System.out.println(printHexBinary(plainBytes));

        System.out.println("Ciphering ...");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherBytes = cipher.doFinal(plainBytes);
        System.out.println("Result:");
        System.out.println(printHexBinary(cipherBytes));

        // decrypt the ciphertext using the same key
        System.out.println("Deciphering..." );
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] newPlainBytes = cipher.doFinal(cipherBytes);
        System.out.println("Result:");
        System.out.println(printHexBinary(newPlainBytes));

        System.out.println("Text:");
        String newPlainText = new String(newPlainBytes); 
        System.out.println(newPlainText);

    }

}
