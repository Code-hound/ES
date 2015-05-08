package pt.ulisboa.tecnico.sdis.id.ws.crypto;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * 	Program to read and write symmetric key file
 */

public class SymKey {


    public static void main(String[] args) throws Exception {

    	// check args
        if (args.length < 2) {
            System.err.println("args: (r/w) (key-file)");
            return;
        }
        final String mode = args[0];
        final String keyPath = args[1];

        if (mode.startsWith("w")) {
        	System.out.println("Generate and save keys");
        	write(keyPath);
        } else {
        	System.out.println("Load keys");
        	read(keyPath);
        }
        
        System.out.println("Done.");
    }

    public static void write(String keyPath) throws Exception {
        // get a AES private key
        System.out.println("Generating AES key ..." );
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(32);
        Key key = keyGen.generateKey();
        System.out.println( "Finish generating AES key" );
        byte[] encoded = key.getEncoded();
        System.out.println("Key:");
        System.out.println(printHexBinary(encoded));

        System.out.println("Writing key to '" + keyPath + "' ..." );

        FileOutputStream fos = new FileOutputStream(keyPath);
        fos.write(encoded);
        fos.close();
    }

    @SuppressWarnings("unused")
    public static void read(String keyPath) throws Exception {
    	
        byte[] key = null; // TODO
        byte[] input = null; // TODO
		byte[] output = null;
        
        System.out.println("Reading key from file " + keyPath + " ...");
        
        FileInputStream fis = new FileInputStream(keyPath);
        
        byte[] encoded = new byte[fis.available()];
        
        fis.read(encoded);
        fis.close();
        
        /*
		DESKeySpec keySpec = new DESKeySpec(encoded);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		Key key = keyFactory.generateSecret(keySpec);
		
		return key;
		*/
        
        SecretKeySpec keySpec = null;
        keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        output = cipher.doFinal(input);
    }

}
