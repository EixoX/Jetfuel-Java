package com.eixox.generators.creditCard;

import com.eixox.generators.CreditCardGenerator;


public class AmericanExpress extends CreditCardGenerator {

	public AmericanExpress() {
		super("37");
	}

	@Override
	public String getTitle() {
		return "Amex";
	}

	@Override
	public String getSummary() {
		return "Números de cartão de crédito válidos da American Express.";
	}

	@Override
	public String getLink() {
		return "http://pt.wikipedia.org/wiki/American_Express";
	}

	@Override
	public int getDigitCount() {
		return 15;
	}

}
