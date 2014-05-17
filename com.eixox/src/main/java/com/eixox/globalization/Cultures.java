package com.eixox.globalization;

public final class Cultures {

	public static final Culture EN_US = new EnUs();
	public static final Culture PT_BR = new PtBr();

	public static final Culture getCulture(String name) {
		if (name == null || name.isEmpty())
			return EN_US;
		if (name.equalsIgnoreCase("en-us") || name.equalsIgnoreCase("en"))
			return EN_US;
		if (name.equalsIgnoreCase("pt-br"))
			return PT_BR;

		throw new Error("No culture matches " + name);
	}
}
