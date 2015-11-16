package com.eixox;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;

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

	@Test
	public void testHexStrings() {

		String testString = "12341234";
		byte[] testBytes = testString.getBytes();
		String hexString1 = Convert.toHex(testBytes);
		byte[] testBytes2 = Convert.fromHex(hexString1);
		String testString2 = new String(testBytes2);
		Assert.assertTrue(testString.equals(testString2));

	}

	@Test
	public void testSha1HashToHex() throws UnsupportedEncodingException, NoSuchAlgorithmException {

		String testString = "0sm0812341234";
		byte[] inputBytes = testString.getBytes("UTF-8");
		byte[] expectedInputBytes = new byte[] { 48, 115, 109, 48, 56, 49, 50, 51, 52, 49, 50, 51, 52 };

		if (inputBytes.length == expectedInputBytes.length) {
			for (int i = 0; i < inputBytes.length; i++)
				Assert.assertTrue(Byte.toString(inputBytes[i]) + " == " + Byte.toString(expectedInputBytes[i]),
						inputBytes[i] == expectedInputBytes[i]);

			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] outputBytes = md.digest(inputBytes);
			byte[] expectedHash = new byte[] { (byte) 147, 66, (byte) 171, 109, 75, 69, (byte) 128, (byte) 128,
					(byte) 186, 6, 104, (byte) 166, 68, (byte) 136, (byte) 197, 2, 92, 124, (byte) 198, (byte) 162 };

			if (outputBytes.length == expectedHash.length) {
				for (int i = 0; i < inputBytes.length; i++)
					Assert.assertTrue(Byte.toString(outputBytes[i]) + " == " + Byte.toString(expectedHash[i]),
							outputBytes[i] == expectedHash[i]);
				
				String outputString = Convert.toHex(outputBytes);
				String expected = "9342ab6d4b458080ba0668a64488c5025c7cc6a2";
				Assert.assertTrue(outputString.toLowerCase() + " == " + expected.toLowerCase(),
						expected.equalsIgnoreCase(outputString));
			} else {
				Assert.assertTrue("Output byte length mismatch", false);
			}

		} else {
			Assert.assertTrue("Input byte length mismatch", false);
		}

	}

}
