package com.eixox.generators;

import java.util.Random;

public class CpfGenerator {

	public static final String[] Localidades = new String[] { "Rio Grande do Sul", "Distrito Federal, Goiás, Mato Grosso, Mato Grosso do Sul e Tocantins",
			"Amazonas, Pará, Roraima, Amapá, Acre e Rondônia", "Ceará, Maranhão e Piauí", "Paraíba, Pernambuco, Alagoas e Rio Grande do Norte",
			"Bahia e Sergipe", "Minas Gerais", "Rio de Janeiro e Espírito Santo", "São Paulo", "Paraná e Santa Catarina" };

	public static final String getLocalidade(int ordinal) {
		return ordinal < 0 || ordinal > 9 ? null : Localidades[ordinal];
	}

	private static final Random random = new Random();

	public static final long generateForLocalidade(int localidade) {
		long a = random.nextInt(9) + 1;
		long b = random.nextInt(10);
		long c = random.nextInt(10);

		long d = random.nextInt(10);
		long e = random.nextInt(10);
		long f = random.nextInt(10);

		long g = random.nextInt(10);
		long h = random.nextInt(10);
		long i = localidade;

		if (a == b && b == c && c == d && d == e && e == f && f == g && g == h && h == i)
			return generate();

		// Note: compute 1st verification digit.
		long d1 = (a * 1 + b * 2 + c * 3 + d * 4 + e * 5 + f * 6 + g * 7 + h * 8 + i * 9) % 11;
		if (d1 == 10)
			d1 = 0;

		// d1 = (d1 >= 10 ? 0 : 11 - d1);

		// Note: compute 2nd verification digit.
		long d2 = (b * 1 + c * 2 + d * 3 + e * 4 + f * 5 + g * 6 + h * 7 + i * 8 + d1 * 9) % 11;
		if (d2 == 10)
			d2 = 0;

		return d2 + d1 * 10 + i * 100 + h * 1000 + g * 10000 + f * 100000 + e * 1000000 + d * 10000000 + c * 100000000 + b * 1000000000 + a * 10000000000L;
	}

	public static final long generate() {
		return generateForLocalidade(random.nextInt(10));
	}

}
