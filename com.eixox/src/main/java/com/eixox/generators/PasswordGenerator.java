package com.eixox.generators;

import java.security.MessageDigest;
import java.util.Random;

import com.eixox.Convert;

public class PasswordGenerator {
	
	private static final Random random = new Random();
	private static final String allowedChars = "ABCDEFGHJIJKLMNOPQRSTUVWXYZ123456890-_";
	
	public static String getRandomPassword(int length) {
		String password = "";
		for (int i = 0; i < length; i++) {
			int randomInt = random.nextInt(allowedChars.length());
			password += allowedChars.charAt(randomInt);
		}
		
		return password;
	}
	
	public static String hashPassword(String password) throws Exception {
		String salt = getRandomPassword(5);
		StringBuilder sb = new StringBuilder();
		
		sb.append("sha1$");
		sb.append(salt);
		sb.append("$");
		
		MessageDigest md = MessageDigest.getInstance("sha1");
		String inputString = salt + password;
		byte[] inputBytes = inputString.getBytes("UTF-8");
		byte[] outputBytes = md.digest(inputBytes);
		String outputString = Convert.toHex(outputBytes);
		
		sb.append(outputString);
		
		return sb.toString();
	}
	
	public static String getRandomHashedPassword(int length) throws Exception {
		return hashPassword(getRandomPassword(length));
	}

}
