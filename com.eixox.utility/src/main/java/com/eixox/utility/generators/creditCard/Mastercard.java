package com.eixox.utility.generators.creditCard;

import com.eixox.utility.generators.CreditCardGenerator;

public class Mastercard extends CreditCardGenerator {
	public Mastercard() {
		super("548045");
	}

	@Override
	public String getTitle() {
		return "Mastercard";
	}

	@Override
	public String getSummary() {
		return "Números de cartões de crédito válidos Mastercard.";
	}

	@Override
	public String getLink() {
		return "http://pt.wikipedia.org/wiki/Mastercard";
	}

	@Override
	public int getDigitCount() {
		return 16;
	}

}
