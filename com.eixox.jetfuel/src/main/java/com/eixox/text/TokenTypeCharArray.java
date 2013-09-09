package com.eixox.text;

public final class TokenTypeCharArray implements TokenType {
	private final String name;
	private final char[] symbols;

	public TokenTypeCharArray(String name, char[] symbols) {
		this.name = name;
		this.symbols = symbols;
	}

	public final String getName() {
		return this.name;
	}

	public final char[] getSymbols() {
		return this.symbols;
	}

	public final boolean contains(char symbol, int position) {
		for (int i = 0; i < this.symbols.length; i++)
			if (symbol == this.symbols[i])
				return true;

		return false;
	}

	
	public final int getMinLength() {
		return 1;
	}

	
	public final int getMaxLength() {
		return 1;
	}

	public static final TokenTypeCharArray LowercaseLetters = new TokenTypeCharArray("Lowercase Letters", new char[] {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', 'ç', 'ñ', 'á', 'à', 'â', 'ã', 'é', 'è', 'ê', 'í', 'ì', 'î', 'ó', 'ò', 'õ', 'ô',
			'ú', 'ù', 'û' });

	public static final TokenTypeCharArray UppercaseLetters = new TokenTypeCharArray("Uppercase Letters", new char[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z', 'Ç', 'Ñ', 'Á', 'À', 'Â', 'Ã', 'É', 'È', 'Ê', 'Í', 'Ì', 'Î', 'Ó', 'Ò', 'Õ', 'Ô',
			'Ú', 'Ù', 'Û' });

	public static final TokenTypeCharArray NumberSigns = new TokenTypeCharArray("Number Signs", new char[] { '+', '-' });

	public static final TokenTypeCharArray NumberSeparators = new TokenTypeCharArray("Number Separators", new char[] {
			'.', ',' });

	public static final TokenTypeCharArray DecimalDigits = new TokenTypeCharArray("Decimal Digits", new char[] { '0',
			'1', '2', '3', '4', '5', '6', '7', '8', '9' });

	public static final TokenTypeCharArray HexDigits = new TokenTypeCharArray("Decimal Digits", new char[] { '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F' });

	public static final TokenTypeCharArray Whitespaces = new TokenTypeCharArray("White spaces", new char[] { 9, 10, 11,
			12, 13, 20, 32 });

	public static final TokenTypeCharArray Punctuation = new TokenTypeCharArray("Punctuation", new char[] { '.', ',',
			':', ';', '?', '!' });

}
