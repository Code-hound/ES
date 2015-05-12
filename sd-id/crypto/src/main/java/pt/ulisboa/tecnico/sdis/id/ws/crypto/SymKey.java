package pt.ulisboa.tecnico.sdis.id.ws.crypto;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 
 * 	Symmetric Key File.
 * 
 * @author Francisco Maria Calisto
 * @author Francisco Silveira
 * 
 * @since 1.0.2
 * 
 */

public class SymKey {
    
    public Key getKey(String seed) throws Exception {
    	if (seed.getBytes().length < 24) {
    		System.out.println("You're a faggot");
    		return null;
    	}
    	
    	final String utf8 = "UTF-8";

    	//String password = "It's a secret!  Make sure it's long enough (24 bytes)";
    	byte[] keyBytes = Arrays.copyOf(seed.getBytes(utf8), 24);
    	SecretKey key = new SecretKeySpec(keyBytes, "TripleDES");
    	return key;
    }
    
    
    	/*
    	SecretKey keyDecrypt = new SecretKeySpec(keyBytes, "TripleDES");
    	
    	// Your vector must be 8 bytes long
    	String vector = "ABCD1234";
    	IvParameterSpec iv = new IvParameterSpec(vector.getBytes(utf8));

    	// Make an encrypter
    	

    	// Make a decrypter
    	Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
    	decrypt.init(Cipher.DECRYPT_MODE, keyDecrypt, iv);

    	// Example use
    	String message = "message";
    	byte[] messageBytes = message.getBytes(utf8);
    	
    	byte[] decryptedBytes = decrypt.doFinal(encryptedByted);

    	// You can re-run the example to see taht the encrypted bytes are consistent
    	String messageString = new String(messageBytes, utf8);
    	String encryptedString = new String(encryptedByted, utf8);
    	String decryptedString = new String(decryptedBytes, utf8);
    	
    	System.out.println(messageString);
    	System.out.println(encryptedString);
    	System.out.println(decryptedString);
    	
    	if (messageString.equals(decryptedString)) {
    		System.out.println("The message is equal after encrypted and before decrypted message");
    	}
    	
    }
	*/
}
