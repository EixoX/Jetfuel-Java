package com.eixox.generators;

import org.junit.Assert;
import org.junit.Test;

public class RandomPasswordTests {

	@Test
	public void randomPasswordLength1() {
		String password = PasswordGenerator.getRandomPassword(1);
		System.err.println(password);
		Assert.assertEquals(password.length(), 1);
	}

	@Test
	public void randomPasswordLength7() {
		String password = PasswordGenerator.getRandomPassword(7);
		System.err.println(password);
		Assert.assertEquals(password.length(), 7);
	}

	@Test
	public void randomPasswordLenght7Hashed() {
		String password = PasswordGenerator.getRandomPassword(7);
		System.err.println(password);
		Assert.assertEquals(password.length(), 7);

		try {
			String hash = PasswordGenerator.hashPassword(password);
			System.err.println(hash);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
