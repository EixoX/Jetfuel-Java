package com.eixox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formats {

	public static final String moneyBRL(double value) {
		String strValue = Double.toString(value);

		strValue = strValue.replace(".", ",");
		strValue = strValue.substring(0, strValue.indexOf(",") + 2);

		return "R$ " + strValue;
	}

	public static final String zeroPadLeft(long value, int size) {
		char[] chars = new char[size];
		long d;
		int i = size - 1;
		do {
			d = value % 10;
			value /= 10;
			chars[i] = (char) (d + 48);
			i--;
		} while (i >= 0);
		return new String(chars);
	}

	public static final String cep(int value) {
		return Strings.concat(zeroPadLeft(value / 1000, 5), "-",
				zeroPadLeft(value % 1000, 3));
	}

	public static final String cpfOrCnpj(long value) {
		return value > 99999999999L ? cnpj(value) : cpf(value);
	}

	public static final String cpf(long value) {
		return Strings.concat(zeroPadLeft(((value / 100000000) % 1000), 3),
				".", zeroPadLeft(((value / 100000) % 1000), 3), ".",
				zeroPadLeft(((value / 100) % 1000), 3), "-",
				zeroPadLeft((value % 100), 2));
	}

	public static final String cnpj(long value) {
		return Strings.concat(zeroPadLeft(((value / 1000000000000L) % 100), 2),
				".", zeroPadLeft(((value / 1000000000) % 1000), 3), ".",
				zeroPadLeft(((value / 1000000) % 1000), 3), "/",
				zeroPadLeft(((value / 100) % 10000), 4), "-",
				zeroPadLeft((value % 100), 2));
	}

	public static final String fileSize(long size, int digits) {

		double sz = size;
		String term = " B";
		if (sz > 1024.0) {
			sz /= 1024.0;
			if (sz > 1024.0) {
				sz /= 1024.0;
				if (sz > 1024) {
					sz /= 1024.0;
					term = " GB";
				} else {
					term = " MB";
				}
			} else {
				term = " KB";
			}
		}

		double dec = Math.pow(10, digits);

		return Double.toString(Math.round(sz * dec) / dec) + term;

	}

	public static final String date(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String hex(byte[] digest) {
		// TODO Auto-generated method stub
		return null;
	}

}
