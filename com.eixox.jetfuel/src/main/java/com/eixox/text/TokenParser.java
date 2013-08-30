package com.eixox.text;

import java.util.ArrayList;
import java.util.List;

public class TokenParser extends ArrayList<TokenType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4012661580196419433L;

	public TokenParser(TokenType... tokenTypes) {
		super(8);
		for (int i = 0; i < tokenTypes.length; i++)
			super.add(tokenTypes[i]);
	}

	private final int peekRemainingTokenTypes(byte[] tokenTypeIgnoreList, int availableCounter, int position,
			char symbol) {
		for (int i = 0; i < tokenTypeIgnoreList.length && availableCounter > 0; i++) {
			if (tokenTypeIgnoreList[i] == 0 && !get(i).contains(symbol, position)) {
				availableCounter--;
			}
		}
		return availableCounter;
	}

	private final int countRemainingTokenTypes(byte[] tokenTypeIgnoreList, int availableCounter, int position,
			char symbol) {

		for (int i = 0; i < tokenTypeIgnoreList.length && availableCounter > 0; i++) {
			if (tokenTypeIgnoreList[i] == 0 && !get(i).contains(symbol, position)) {
				availableCounter--;
				tokenTypeIgnoreList[i] = 1;
			}
		}

		return availableCounter;
	}

	private final TokenType[] getTokenTypes(byte[] tokenTypeIgnoreList, int availableCounter) {
		TokenType[] arr = new TokenType[availableCounter];
		int j = 0;
		for (int i = 0; i < tokenTypeIgnoreList.length; i++)
			if (tokenTypeIgnoreList[i] == 0) {
				arr[j] = get(i);
				j++;
			}
		return arr;
	}

	public final Token readToken(String input, int offset, int length) {

		if (offset >= length)
			return null;

		int tokenTypeCount = this.size();
		byte[] tokenTypeIgnoreList = new byte[tokenTypeCount];

		// first check: is there any token type that matches the symbol?
		int availableCounter = countRemainingTokenTypes(tokenTypeIgnoreList, tokenTypeCount, 0, input.charAt(offset));
		if (availableCounter < 1)
			return null;

		int maxTokenLength = length - offset;
		int dummyCounter = availableCounter;
		int i = 1;

		// read the rest of the matching symbols.
		while (i < maxTokenLength
				&& peekRemainingTokenTypes(tokenTypeIgnoreList, dummyCounter, i, input.charAt(offset + i)) > 0) {
			availableCounter = countRemainingTokenTypes(tokenTypeIgnoreList, availableCounter, i,
					input.charAt(offset + i));
			i++;
		}

		return new Token(input.substring(offset, offset + i), getTokenTypes(tokenTypeIgnoreList, availableCounter));

	}

	public final List<Token> parse(String input) {

		ArrayList<Token> list = new ArrayList<>();

		if (input == null || input.isEmpty())
			return list;

		int length = input.length();
		int offset = 0;
		Token token = readToken(input, offset, length);
		while (token != null) {
			list.add(token);
			offset += token.getLength();
			token = readToken(input, offset, length);
		}

		return list;
	}

}
