package com.eixox.generators;

import java.util.Random;

public abstract class CreditCardGenerator {

	private String[] prefixes;

	protected CreditCardGenerator(String... prefixes) {
		this.prefixes = prefixes;
		if (this.prefixes.length < 1)
			throw new RuntimeException("At least one prefix is required to identitfy a card");
	}

	public final int getPrefixCount() {
		return this.prefixes.length;
	}

	public final String getPrefix(int index) {
		return this.prefixes[index];
	}

	public abstract String getTitle();

	public abstract String getSummary();

	public abstract String getLink();

	public abstract int getDigitCount();

	public final String[] generate(int count) {
		String[] s = new String[count];
		for (int i = 0; i < count; i++)
			s[i] = generate();
		return s;
	}

	public final String generate() {
		int digitCount = getDigitCount();

		char[] digits = new char[digitCount];

		String prefix = this.prefixes.length > 1 ? this.prefixes[random.nextInt(this.prefixes.length)] : this.prefixes[0];

		int prefixLength = prefix.length();

		for (int i = 0; i < prefixLength; i++)
			digits[i] = prefix.charAt(i);

		setRandomDigits(digits, prefixLength, digitCount - prefixLength - 1);
		digits[digitCount - 1] = luhnVerifier(digits);
		return new String(digits);
	}

	public static final char luhnVerifier(char[] number) {
		int maxlength = number.length - 1;
		int sum = 0;
		int index;
		int n;
		for (int i = 0; i < maxlength; i++) {
			index = maxlength - i - 1;

			n = ((int) number[index] - 48);

			if (i % 2 == 0) {
				n += n;
			}

			sum += (n / 10) + (n % 10);
		}

		sum *= 9;
		sum %= 10;
		return (char) (48 + sum);
	}

	public static final char luhnVerifier(String number) {
		return luhnVerifier(number.toCharArray());
	}

	public static final void setRandomDigits(char[] array, int start, int length) {
		for (int i = 0; i < length; i++)
			array[start + i] = (char) (48 + random.nextInt(10));
	}

	private static final Random random = new Random();

	public static final boolean nextBoolean() {
		return random.nextBoolean();
	}
}
