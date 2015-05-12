package pt.ulisboa.tecnico.sdis.id.ws.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class SymCryptoTest {
	
	private String plaintext = "O pocoyo e gay";
	
	@Test
	public void encryptThenDecrypt() throws Exception {
		SymCrypto crypto = new SymCrypto();
		byte[] encrypted = crypto.encrypt(plaintext);
		String decrypted = crypto.decrypt(encrypted);
		
		assertEquals(plaintext, decrypted);
	}
}
