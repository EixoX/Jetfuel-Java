package com.eixox;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

public final class StringHelper {

	// ____________________________________________________________________________
	public static final String attributeEncode(Object input) {
		if (input == null)
			return "";

		String s = input.toString();
		if (s.isEmpty())
			return s;

		return s.replace("\"", "&quot;");
	}

	// ____________________________________________________________________________
	public static final String concat(String... strings) {
		int length = 0;
		for (int i = 0; i < strings.length; i++)
			length += strings[i].length();

		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < strings.length; i++)
			builder.append(strings[i]);

		return builder.toString();
	}

	// ____________________________________________________________________________
	public static final boolean containsIgnoreCase(String lookup, String... items) {
		return indexOfIgnoreCase(lookup, items) >= 0;
	}

	// ____________________________________________________________________________
	public static final String digitsOnly(String input) {
		if (input == null || input.isEmpty())
			return input;

		int length = input.length();
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			if (Character.isDigit(c))
				builder.append(c);
		}
		return builder.toString();
	}

	// ____________________________________________________________________________
	public static final boolean equalsIgnoreCase(String left, String right) {

		if (left == null || left.isEmpty())
			return right == null || right.isEmpty();
		else
			return left.equalsIgnoreCase(right);
	}

	// ____________________________________________________________________________
	public static final boolean equalsAnyIgnoreCase(String left, String... items) {

		if (left != null && !left.isEmpty())
			for (int i = 0; i < items.length; i++)
				if (left.equalsIgnoreCase(items[i]))
					return true;

		return false;

	}

	// ____________________________________________________________________________
	public static final int indexOf(String content, int offset, int length, char c) {
		for (int i = offset; i < length; i++)
			if (c == content.charAt(i))
				return i;

		return -1;
	}

	// ____________________________________________________________________________
	public static final int indexOfAny(String content, char... chars) {
		return indexOfAny(content, 0, content.length(), chars);
	}

	// ____________________________________________________________________________
	public static final int indexOfAny(String content, int offset, char... chars) {
		return indexOfAny(content, offset, content.length(), chars);
	}

	// ____________________________________________________________________________
	public static final int indexOfAny(String content, int offset, int length, char... chars) {
		for (int i = offset; i < length; i++) {
			char c = content.charAt(i);
			for (int j = 0; j < chars.length; j++)
				if (chars[j] == c)
					return i;
		}
		return -1;
	}

	// ____________________________________________________________________________
	public static final int indexOfIgnoreCase(String lookup, String... items) {
		for (int i = 0; i < items.length; i++)
			if (lookup.equalsIgnoreCase(items[i]))
				return i;

		return -1;
	}

	// ____________________________________________________________________________
	public static final int indexOfWhitespace(String content) {
		return indexOfWhitespace(content, 0, content.length());
	}

	// ____________________________________________________________________________
	public static final int indexOfWhitespace(String content, int offset) {
		return indexOfWhitespace(content, offset, content.length());
	}

	// ____________________________________________________________________________
	public static final int indexOfWhitespace(String content, int offset, int length) {
		for (int i = offset; i < length; i++)
			if (Character.isWhitespace(content.charAt(i)))
				return i;

		return -1;
	}

	// ____________________________________________________________________________
	public static final int indexOfWhitespaceOrAny(String content, char... chars) {
		return indexOfWhitespaceOrAny(content, 0, content.length(), chars);
	}

	// ____________________________________________________________________________
	public static final int indexOfWhitespaceOrAny(String content, int offset, char... chars) {
		return indexOfWhitespaceOrAny(content, offset, content.length(), chars);
	}

	// ____________________________________________________________________________
	public static final int indexOfWhitespaceOrAny(String content, int offset, int length, char... chars) {
		for (int i = offset; i < length; i++) {
			char c = content.charAt(i);
			if (Character.isWhitespace(c))
				return i;
			else
				for (int j = 0; j < chars.length; j++)
					if (chars[j] == c)
						return i;
		}
		return -1;
	}

	// ____________________________________________________________________________
	public static final boolean isNullOrEmpty(String input) {
		return input == null || input.isEmpty();
	}

	// ____________________________________________________________________________
	public static final String isNullOrEmptyAlternate(Object input, String alternate) {
		if (input != null) {
			if (input instanceof String && !((String) input).isEmpty())
				alternate = (String) input;
			else
				alternate = input.toString();
		}
		return alternate;
	}

	// ____________________________________________________________________________
	public static final String join(String separator, Iterable<?> values) {
		if (values == null)
			return null;

		boolean prependSepa = false;
		StringBuilder builder = new StringBuilder();

		for (Object o : values) {
			if (prependSepa)
				builder.append(separator);

			else
				prependSepa = true;
			builder.append(o);
		}

		return builder.toString();
	}

	// ____________________________________________________________________________
	public static final String join(String separator, Object... values) {
		if (values == null)
			return null;
		else if (values.length == 0)
			return "";

		StringBuilder builder = new StringBuilder(values.length * 10);
		builder.append(values[0]);
		for (int i = 1; i < values.length; i++) {
			builder.append(separator);
			builder.append(values[i]);
		}
		return builder.toString();
	}

	// ____________________________________________________________________________
	public static final String join(String separator, String... values) {
		if (values == null)
			return null;
		else if (values.length == 0)
			return "";

		StringBuilder builder = new StringBuilder(values.length * 10);
		builder.append(values[0]);
		for (int i = 1; i < values.length; i++) {
			builder.append(separator);
			builder.append(values[i]);
		}
		return builder.toString();
	}

	// ____________________________________________________________________________
	public static final String joinLowercase(String separator, String... values) {
		if (values == null)
			return null;
		else if (values.length == 0)
			return "";

		StringBuilder builder = new StringBuilder(values.length * 10);
		builder.append(values[0].toLowerCase());
		for (int i = 1; i < values.length; i++) {
			builder.append(separator);
			builder.append(values[i].toLowerCase());
		}
		return builder.toString();
	}

	// ____________________________________________________________________________
	public static final String letterOrDigits(String input) {
		if (input == null || input.isEmpty())
			return input;

		int length = input.length();
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			if (Character.isLetterOrDigit(c))
				builder.append(c);
		}
		return builder.toString();
	}

	// ____________________________________________________________________________
	public static final String removeLatinAccents(String input) {

		if (input == null || input.isEmpty())
			return input;

		return input.replaceAll("[áàâã]", "a").replaceAll("[ÁÀÂÃ]", "A").replaceAll("[èéê]", "e").replaceAll("[ÉÈÊ]", "E").replaceAll("[íìî]", "i").replaceAll("[ÍÌÎ]", "I").replaceAll("[òóôõ]", "o").replaceAll("[ÓÒÔÕ]", "O")
				.replaceAll("[ùúû]", "u").replaceAll("[ÙÚÛ]", "U").replaceAll("[ç]", "c").replaceAll("[Ç]", "C").replaceAll("ñ", "n").replaceAll("Ñ", "N");
	}

	// ____________________________________________________________________________
	public static final String trim(String input) {
		if (input == null || input.isEmpty())
			return input;

		int length = input.length();
		int spos = 0;
		while (spos < length && Character.isWhitespace(input.charAt(spos)))
			spos++;
		int lpos = length - 1;
		while (lpos > spos && Character.isWhitespace(input.charAt(lpos)))
			lpos--;

		if (lpos == spos || spos == length)
			return "";
		else if (spos > 0 || lpos < (length - 1))
			return input.substring(spos, lpos);
		else
			return input;
	}

	// ____________________________________________________________________________
	public static final String urlEncode(String input) {
		if (input == null || input.isEmpty())
			return "";
		else
			try {
				return URLEncoder.encode(input, "utf-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
	}

	// ____________________________________________________________________________
	public static final String whitespaceCollapse(String input) {
		return trim(whitespaceReplace(input).replaceAll("  ", " "));
	}

	// ____________________________________________________________________________
	public static final String whitespaceReplace(String input) {

		if (input == null || input.isEmpty())
			return input;

		char[] chars = new char[input.length()];

		for (int i = 0; i < chars.length; i++) {
			char c = input.charAt(i);
			if (Character.isWhitespace(c))
				c = ' ';

			chars[i] = c;
		}

		return new String(chars);
	}

	// ____________________________________________________________________________
	public static final boolean noneIsNullOrEmpty(String... items) {
		for (int i = 0; i < items.length; i++)
			if (items[i] == null || items[i].isEmpty())
				return false;

		return true;
	}

	// ____________________________________________________________________________
	public static final String right(String input, int length) {
		if (input == null || input.isEmpty() || input.length() <= length) {
			return input;
		} else {
			return input.substring(input.length() - length, input.length());
		}
	}

	// ____________________________________________________________________________
	public static final boolean endsWithIgnoreCase(String input, String... patterns) {
		for (int i = 0; i < patterns.length; i++) {
			if (patterns[i].equalsIgnoreCase(right(input.trim(), patterns[i].length())))
				return true;
		}
		return false;
	}

	// ____________________________________________________________________________
	public static boolean equalsIgnoreCaseAndAccent(String left, String right) {

		if (left == null)
			return right == null;
		else if (left.isEmpty())
			return right.isEmpty();
		else if (left.length() != right.length())
			return false;
		else
			return left.toLowerCase(Locale.ENGLISH).equals(right.toLowerCase(Locale.ENGLISH));
	}

	// ____________________________________________________________________________
	public static final String ellipsis(String input, int maxlength) {
		if (input == null || input.isEmpty() || input.length() <= maxlength)
			return input;

		while (maxlength > 1 && !Character.isWhitespace(input.charAt(maxlength)))
			maxlength--;

		return input.substring(0, maxlength) + "...";

	}
}
