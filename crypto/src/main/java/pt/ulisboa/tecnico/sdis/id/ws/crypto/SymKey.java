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
    		return null;
    	}
    	
    	final String utf8 = "UTF-8";

    	//String password = "It's a secret!  Make sure it's long enough (24 bytes)";
    	byte[] keyBytes = Arrays.copyOf(seed.getBytes(utf8), 24);
    	SecretKey key = new SecretKeySpec(keyBytes, "TripleDES");
    	return key;
    }
}
