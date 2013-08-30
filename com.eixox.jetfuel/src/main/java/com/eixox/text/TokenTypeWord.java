package com.eixox.text;

public class TokenTypeWord implements TokenType {

	private final String name;
	private final boolean ignoreCase;

	public TokenTypeWord(String word, boolean ignoreCase) {
		this.name = word;
		this.ignoreCase = ignoreCase;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public final boolean contains(char symbol, int position) {
		if (position >= name.length())
			return false;

		return ignoreCase ? Character.toLowerCase(symbol) == Character.toLowerCase(name.charAt(position))
				: symbol == name.charAt(position);

	}

	@Override
	public final int getMinLength() {
		return this.name.length();
	}

	@Override
	public final int getMaxLength() {
		return this.name.length();
	}

}
