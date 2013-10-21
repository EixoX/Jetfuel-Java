package com.eixox;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Cryptography {

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
		return Base64.encodeBase64String(output);
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
		byte[] contentBytes = Base64.decodeBase64(content);
		byte[] output = aesDecrypt(key, contentBytes);
		return new String(output);
	}

}
