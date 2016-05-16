package com.eixox;

public class Base36 {

	public long value;
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";

	public Base36(long value)
	{
		this.value = value;
	}

	public static final Base36 parse(String input)
	{
		return new Base36(parseBase36(input));
	}

	public static final long parseBase36(String input) {
		long converted = 0;
		int k = 1;
		int imax = input.length() - 1;
		boolean isNegative = input.charAt(0) == '-';
		int imin = isNegative ? 1 : 0;

		for (int i = imax; i >= imin; i--) {

			int d = ALPHABET.indexOf(input.charAt(i));

			if (d < 0)
				throw new RuntimeException(input + " is not a valid base 36 number");

			converted += (d * k);
			k *= 36;
		}
		return isNegative ? -converted : converted;
	}

	public static final String formatBase36(long value) {

		if (value < 0)
			return "-" + formatBase36(-value);
		else if (value == 0)
			return "0";
		else if (value < 36)
			return Character.toString(ALPHABET.charAt((int) value));
		else {
			StringBuilder builder = new StringBuilder(10);
			while (value > 0) {
				long d = value % 36;
				char c = ALPHABET.charAt((int) d);
				builder.append(c);
				value /= 36;
			}
			builder.reverse();
			return builder.toString();
		}
	}

	@Override
	public String toString() {
		return formatBase36(this.value);
	}

}
