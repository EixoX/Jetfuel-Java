package com.eixox.text;

public final class TokenTypeUnion implements TokenType {

	private final String name;
	private final TokenType[] tokenTypes;
	private final int minLength;
	private final int maxLength;

	public TokenTypeUnion(String name, TokenType... unionTokenTypes) {
		if (unionTokenTypes == null || unionTokenTypes.length == 0)
			throw new RuntimeException("At least one token type must be part of the union");

		this.name = name;
		this.tokenTypes = unionTokenTypes;

		int min = Integer.MAX_VALUE;
		int max = 0;
		for (int i = 0; i < unionTokenTypes.length; i++) {
			if (unionTokenTypes[i].getMinLength() < min)
				min = unionTokenTypes[i].getMinLength();

			max += unionTokenTypes[i].getMaxLength();
		}
		this.minLength = min;
		this.maxLength = max;
	}

	
	public final String getName() {
		return this.name;
	}

	public final TokenType[] getTokenTypes() {
		return this.tokenTypes;
	}

	
	public final boolean contains(char symbol, int position) {
		for (int i = 0; i < this.tokenTypes.length; i++)
			if (this.tokenTypes[i].contains(symbol, position))
				return true;

		return false;
	}

	
	public final int getMinLength() {
		return this.minLength;
	}

	
	public final int getMaxLength() {
		return this.maxLength;
	}

	public static final TokenTypeUnion LetterOrDigit = new TokenTypeUnion("Letter or digit",
			TokenTypeCharArray.LowercaseLetters, TokenTypeCharArray.UppercaseLetters, TokenTypeCharArray.DecimalDigits);

}
