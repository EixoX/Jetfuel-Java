package com.eixox.text;

public interface TokenType {

	public String getName();

	public boolean contains(char symbol, int position);

	public int getMinLength();

	public int getMaxLength();
}
