package com.eixox;

public final class CharHelper {

	public static final char removeAccent(char c) {
		switch (c) {
		case '·':
		case '‡':
		case '‚':
		case '„':
		case '‰':
			return 'a';
		case 'È':
		case 'Ë':
		case 'Í':
		case 'Î':
			return 'e';
		case 'Ì':
		case 'Ï':
		case 'Ó':
		case 'Ô':
			return 'i';
		case 'Ú':
		case 'Û':
		case 'Ù':
		case 'ı':
		case 'ˆ':
			return 'o';
		case '˘':
		case '˙':
		case '˚':
		case '¸':
			return 'u';
		case '¿':
		case '¡':
		case '¬':
		case '√':
		case 'ƒ':
			return 'A';
		case '»':
		case '…':
		case ' ':
		case 'À':
			return 'E';
		case 'Ã':
		case 'Õ':
		case 'Œ':
		case 'œ':
			return 'I';
		case '“':
		case '”':
		case '‘':
		case '’':
		case '÷':
			return 'O';
		case 'Ÿ':
		case '⁄':
		case '€':
		case '‹':
			return 'u';
		case 'Á':
			return 'c';
		case '«':
			return 'C';
		case 'Ò':
			return 'n';
		case '—':
			return 'N';
		default:
			return c;
		}
	}
}
