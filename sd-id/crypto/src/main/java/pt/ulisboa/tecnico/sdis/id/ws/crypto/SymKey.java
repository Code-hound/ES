package pt.ulisboa.tecnico.sdis.id.ws.crypto;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 	Program to read and write symmetric key file
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
        */
    	
    	
    	// I use String.getBytes() as a convenince a lot.  I specify the encoding
    	// to ensure that the result is consistent.
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
    		System.out.println("Equals.");
    	}
    	
    	
    	
    	
    	
    	/*
    	String vector1 = "ABC123";
    	String vector2 = "ZXW987";
    	
    	IvParameterSpec iv1 = new IvParameterSpec(vector1.getBytes("UTF-8"));
    	IvParameterSpec iv2 = new IvParameterSpec(vector2.getBytes("UTF-8"));
    	
    	Key key1 = null;
    	Key key2 = null;
    	
    	Cipher encrypt1 = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
    	encrypt1.init(Cipher.ENCRYPT_MODE, key1, iv1);
    	Cipher decrypt1 = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
    	decrypt1.init(Cipher.DECRYPT_MODE, key1, iv1);
    	key1 = getKey(null);
    	System.out.println("Key1:" + key1.getEncoded().toString());
    	
    	Cipher encrypt2 = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
    	encrypt2.init(Cipher.ENCRYPT_MODE, key2, iv1);
    	Cipher decrypt2 = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
    	decrypt2.init(Cipher.DECRYPT_MODE, key2, iv1);
    	key2 = getKey(null);
    	System.out.println("Key2:" + key2.getEncoded().toString());
    	*/
    	
    	/*
    	Key key1 = null;
    	Key key2 = null;
    	
    	SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
    	key1 = getKey(random);
    	System.out.println("Key1:" + key1.getEncoded().toString());
    	
    	key2 = getKey(random);
    	System.out.println("Key2:" + key2.getEncoded().toString());
    	
    	if (key2.equals(key1)) {
    		System.out.println("Equals.");
    	}
    	*/
    	
    }

    public static void write(String keyPath) throws Exception {
        // get a TripleDES private key
        System.out.println("Generating TripleDES key ..." );
        KeyGenerator keyGen = KeyGenerator.getInstance("TripleDES");
        keyGen.init(168);
        Key key = keyGen.generateKey();
        System.out.println( "Finish generating TripleDES key" );
        byte[] encoded = key.getEncoded();
        System.out.println("Key:");
        System.out.println(printHexBinary(encoded));

        System.out.println("Writing key to '" + keyPath + "' ..." );

        FileOutputStream fos = new FileOutputStream(keyPath);
        fos.write(encoded);
        fos.close();
    }

    public static Key read(String keyPath) throws Exception {
        System.out.println("Reading key from file " + keyPath + " ...");
        FileInputStream fis = new FileInputStream(keyPath);
        byte[] encoded = new byte[fis.available()];
        fis.read(encoded);
        fis.close();

		DESKeySpec keySpec = new DESKeySpec(encoded);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("TripleDES");
		Key key = keyFactory.generateSecret(keySpec);
		return key;
    }

}
