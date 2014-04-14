package com.eixox;

import org.junit.Assert;

import org.junit.Test;

import com.eixox.Cryptography;

public class CryptographyTest {

	@Test
	public void testAesEncrypt() {

		byte[] key = "eixox2013eixox01".getBytes();

		String input = "Ol� Rodrigo Portela. � um prazer v�-lo";

		String output = Cryptography.aesEncrypt(key, input);

		Assert.assertNotNull(output);
		System.out.println("Ciphered: " + output);

		String input2 = Cryptography.aesDecrypt(key, output);
		System.out.println("Decrypted: " + input2);

		Assert.assertEquals(input, input2);
	}

}
