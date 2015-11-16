package com.eixox;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public final class Cryptography {

	private Cryptography() {
	}

	public static byte[] aesEncrypt(byte[] key, byte[] content) {
		try {
			Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			aesCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
			byte[] output = aesCipher.doFinal(content);
			return output;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String aesEncrypt(byte[] key, String content) {
		byte[] contentBytes = content.getBytes();
		byte[] output = aesEncrypt(key, contentBytes);
		return Base64.encode(output);
	}

	public static byte[] aesDecrypt(byte[] key, byte[] content) {
		try {
			Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			aesCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
			byte[] output = aesCipher.doFinal(content);
			return output;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String aesDecrypt(byte[] key, String content) {
		byte[] contentBytes = Base64.decode(content);
		byte[] output = aesDecrypt(key, contentBytes);
		return new String(output);
	}

	public static String md5Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] bytes = md.digest();
			return Base64.encode(bytes);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String sha256Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(input.getBytes());
			byte[] bytes = md.digest();
			return Base64.encode(bytes);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static byte[] sha1Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(input.getBytes("UTF-8"));
			byte[] bytes = md.digest();
			return bytes;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String sha1HashToBase64(String input) {
		return Base64.encode(sha1Hash(input));
	}

	public static String sha1HashToHex(String input) {
		return Convert.toHex(sha1Hash(input));
	}

	
}
