package pt.ulisboa.tecnico.sdis.id.ws.crypto;

// provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.security.AlgorithmParameters;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * 
 * 	 Secret key cryptography using the AES/Rijndael algorithm.
 * 
 *   Rijndael isn't equal to AES, but instead is AES with some
 *   restrictions - fixed block size of 128/256 bits, and some crypto
 *   modes not supported.
 *   
 *   Any full AES implementation should be usable as Rijndael.
 *   
 *   PROBLEM: Wrong keysize: must be equal to 128, 192 or 256 #92 SOLVED
 *   
 */

public class SymCrypto {

    public static void main (String[] args) throws Exception {

        // check args and get plaintext
        if (args.length != 1) {
            System.err.println("args: (text)");
            return;
        }
        final String plainText = args[0];
        final byte[] plainBytes = plainText.getBytes("UTF-8");

        System.out.println("Text:");
        System.out.println(plainText);
        System.out.println("Bytes:");
        System.out.println(printHexBinary(plainBytes));
        

        // get a AES private key
        System.out.println("Generating AES key ...");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // key size is 128
        
        SecretKey key = keyGen.generateKey();
        
        //Key key = keyGen.generateKey();
        System.out.println("Key:");
        System.out.println(printHexBinary(key.getEncoded()));

        // get a AES cipher object and print the provider
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        System.out.println(cipher.getProvider().getInfo());
        //System.out.println(cipher.getProvider().getProperty(key, defaultValue));
        
        
        // encrypt using the key and the plaintext
        System.out.println("Text:");
        System.out.println(plainText);
        System.out.println("Bytes:");
        System.out.println(printHexBinary(plainBytes));

        System.out.println("Ciphering ...");
        System.out.println("Antes: "+ key.toString()); //APAGAR
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //AlgorithmParameters params = cipher.getParameters();
        System.out.println("Parameters: "+cipher.getParameters().toString());
        System.out.println("Depois: "+ key.toString()); //APAGAR
        byte[] cipherBytes = cipher.doFinal(plainBytes);
        System.out.println("Result:");
        System.out.println(printHexBinary(cipherBytes));

        // decrypt the ciphertext using the same key
        System.out.println("Deciphering..." );
        System.out.println("Antes DECRYPT_MODE: "+ key.toString()); //APAGAR
		cipher.init(Cipher.DECRYPT_MODE, key);
        System.out.println("Apos DECRYPT_MODE: "+ key.toString()); //APAGAR
        byte[] newPlainBytes = cipher.doFinal(cipherBytes);
        System.out.println("Result:");
        System.out.println(printHexBinary(newPlainBytes));

        System.out.println("Text:");
        String newPlainText = new String(newPlainBytes);
        System.out.println(newPlainText);

    }

}
