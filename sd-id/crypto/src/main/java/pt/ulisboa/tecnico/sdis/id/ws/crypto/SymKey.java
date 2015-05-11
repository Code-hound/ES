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

	public static Key getKey (SecureRandom random) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("TripleDES");
		keyGen.init(168, random);
		Key key = keyGen.generateKey();
		return key;
    }
    
    public static void main(String[] args) throws Exception {
    	
    	/*
    	 * 
    	 * Use String.getBytes() as a convenince a lot.
    	 * 
    	 * Specify the encoding to ensure that the result is consistent.
    	 * 
    	 */
    	
    	final String utf8 = "UTF-8";

    	String password = "It's a secret!  Make sure it's long enough (24 bytes)";
    	byte[] keyBytes = Arrays.copyOf(password.getBytes(utf8), 24);
    	SecretKey key = new SecretKeySpec(keyBytes, "TripleDES");

    	// Your vector must be 8 bytes long
    	String vector = "ABCD1234";
    	IvParameterSpec iv = new IvParameterSpec(vector.getBytes(utf8));

    	// Make an encrypter
    	Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
    	encrypt.init(Cipher.ENCRYPT_MODE, key, iv);

    	// Make a decrypter
    	Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
    	decrypt.init(Cipher.DECRYPT_MODE, key, iv);

    	// Example use
    	String message = "message";
    	byte[] messageBytes = message.getBytes(utf8);
    	byte[] encryptedByted = encrypt.doFinal(messageBytes);
    	byte[] decryptedBytes = decrypt.doFinal(encryptedByted);

    	// You can re-run the exmaple to see taht the encrypted bytes are consistent
    	String messageString = new String(messageBytes, utf8);
    	String encryptedString = new String(encryptedByted, utf8);
    	String decryptedString = new String(decryptedBytes, utf8);
    	
    	System.out.println(messageString);
    	System.out.println(encryptedString);
    	System.out.println(decryptedString);
    	
    	if (messageString.equals(decryptedString)) {
    		System.out.println("The message is equal after encryoted message");
    	}
    	
    }

}
