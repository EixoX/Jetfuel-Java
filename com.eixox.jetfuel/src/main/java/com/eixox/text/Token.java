package com.eixox.text;

public class Token {

	private final String content;
	private final TokenType[] tokenTypes;

	public Token(String content, TokenType[] tokenTypes) {
		this.content = content;
		this.tokenTypes = tokenTypes;
	}

	public final String getContent() {
		return this.content;
	}

	public final TokenType[] getTokenTypes() {
		return this.tokenTypes;
	}

	public final int getLength() {
		return this.content.length();
	}

	@Override
	public String toString() {
		return this.content;
	}

}
