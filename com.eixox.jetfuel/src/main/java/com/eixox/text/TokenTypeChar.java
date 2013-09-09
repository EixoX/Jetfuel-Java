package com.eixox.text;

public final class TokenTypeChar implements TokenType {

	private final String name;
	private final char symbol;

	public TokenTypeChar(String name, char symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	
	public final String getName() {
		return this.name;
	}

	
	public final boolean contains(char symbol, int position) {
		return this.symbol == symbol;
	}

	public static TokenTypeChar LeftCurlyPar = new TokenTypeChar("Left curly par", '(');
	public static TokenTypeChar RightCurlyPar = new TokenTypeChar("Right curly par", ')');

	
	public final int getMinLength() {
		return 1;
	}

	
	public final int getMaxLength() {
		return 1;
	}

}
