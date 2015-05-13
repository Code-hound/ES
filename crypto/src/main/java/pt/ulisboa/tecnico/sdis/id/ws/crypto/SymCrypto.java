package pt.ulisboa.tecnico.sdis.id.ws.crypto;

//provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

/**
* 	 Secret key cryptography using the TripleDES algorithm.
*/

public class SymCrypto {
	private final String seed = "It was the best of times, it was the worst of times";
	private final String documentSeed = "I shot a man in Reno just to watch him die";
	
	String vector = "ABCD1234";
	IvParameterSpec iv;
	
	private SymKey symKey = new SymKey();
	private Key symmetricKey;
	private Key documentSymmetricKey;
	
	public SymCrypto() throws Exception {
		this.symmetricKey = symKey.getKey(seed);
		this.documentSymmetricKey = symKey.getKey(documentSeed);
		this.iv = new IvParameterSpec(vector.getBytes("UTF-8"));
	}
 
 public String encrypt(String plaintext) throws Exception {
	 byte[] messageToBytes = plaintext.getBytes("UTF8");
	 
 	 Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
 	 encrypt.init(Cipher.ENCRYPT_MODE, symmetricKey, this.iv);
 	 byte[] encryptedBytes = encrypt.doFinal(messageToBytes);
 	 
 	 Base64.Encoder encoder =  Base64.getEncoder();
 	 String encryptedString = new String(encoder.encode(encryptedBytes));
 	 return encryptedString;
 }
 
 public String decrypt(String encrypted) throws Exception {
	 Base64.Decoder decoder =  Base64.getDecoder();
	 byte[] encryptedDecoded = decoder.decode(encrypted);
	 
	 Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
	 decrypt.init(Cipher.DECRYPT_MODE, symmetricKey, this.iv);
	 byte[] decryptedBytes = decrypt.doFinal(encryptedDecoded);
	 String message = new String(decryptedBytes);
	 
	 return message;
 }
 
 public String encryptDocument(String plaintext) throws Exception {
	 byte[] messageToBytes = plaintext.getBytes("UTF8");
	 
 	 Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
 	 encrypt.init(Cipher.ENCRYPT_MODE, documentSymmetricKey, this.iv);
 	 byte[] encryptedBytes = encrypt.doFinal(messageToBytes);
 	 
 	 Base64.Encoder encoder =  Base64.getEncoder();
 	 String encryptedString = new String(encoder.encode(encryptedBytes));
 	 return encryptedString;
 }

 public String decryptDocument(String encrypted) throws Exception {
	 Base64.Decoder decoder =  Base64.getDecoder();
	 byte[] encryptedDecoded = decoder.decode(encrypted);
	 
	 Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
	 decrypt.init(Cipher.DECRYPT_MODE, documentSymmetricKey, this.iv);
	 byte[] decryptedBytes = decrypt.doFinal(encryptedDecoded);
	 String message = new String(decryptedBytes);
	 
	 return message;
 }
 
}
