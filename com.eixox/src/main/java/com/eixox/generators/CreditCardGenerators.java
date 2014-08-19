package com.eixox.generators;

import com.eixox.generators.creditCard.AmericanExpress;
import com.eixox.generators.creditCard.DinersClubInternational;
import com.eixox.generators.creditCard.Maestro;
import com.eixox.generators.creditCard.Mastercard;
import com.eixox.generators.creditCard.Visa;

public class CreditCardGenerators {

	public static final AmericanExpress AMEX = new AmericanExpress();
	public static final Maestro MAESTRO = new Maestro();
	public static final Visa VISA = new Visa();
	public static final Mastercard MASTERCARD = new Mastercard();
	public static final DinersClubInternational DINERS_INTERNATIONAL = new DinersClubInternational();
}
