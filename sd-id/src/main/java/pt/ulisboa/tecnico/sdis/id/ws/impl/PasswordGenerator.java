package pt.ulisboa.tecnico.sdis.id.ws.impl;

import java.security.SecureRandom;

public class PasswordGenerator {
	
	private String chars 
	= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	
	public String getNewPassword() {
		SecureRandom random = new SecureRandom();
		String password = "";
		while(password.length()<20) {
			int j = random.nextInt(chars.length());
			password = password+chars.charAt(j);
		}
		return password;
	}
}
