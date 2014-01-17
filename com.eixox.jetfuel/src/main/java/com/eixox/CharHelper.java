package com.eixox;

public final class CharHelper {

	public static final char removeAccent(char c) {
		switch (c) {
		case 'á':
		case 'à':
		case 'â':
		case 'ã':
		case 'ä':
			return 'a';
		case 'é':
		case 'è':
		case 'ê':
		case 'ë':
			return 'e';
		case 'í':
		case 'ì':
		case 'î':
		case 'ï':
			return 'i';
		case 'ò':
		case 'ó':
		case 'ô':
		case 'õ':
		case 'ö':
			return 'o';
		case 'ù':
		case 'ú':
		case 'û':
		case 'ü':
			return 'u';
		case 'À':
		case 'Á':
		case 'Â':
		case 'Ã':
		case 'Ä':
			return 'A';
		case 'È':
		case 'É':
		case 'Ê':
		case 'Ë':
			return 'E';
		case 'Ì':
		case 'Í':
		case 'Î':
		case 'Ï':
			return 'I';
		case 'Ò':
		case 'Ó':
		case 'Ô':
		case 'Õ':
		case 'Ö':
			return 'O';
		case 'Ù':
		case 'Ú':
		case 'Û':
		case 'Ü':
			return 'u';
		case 'ç':
			return 'c';
		case 'Ç':
			return 'C';
		case 'ñ':
			return 'n';
		case 'Ñ':
			return 'N';
		default:
			return c;
		}
	}
}
