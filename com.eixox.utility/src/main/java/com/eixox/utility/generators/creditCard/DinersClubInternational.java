package com.eixox.utility.generators.creditCard;

import com.eixox.utility.generators.CreditCardGenerator;

public class DinersClubInternational extends CreditCardGenerator {
	public DinersClubInternational() {
		super("3095");
	}

	@Override
	public String getTitle() {
		return "Diners Club";
	}

	@Override
	public String getSummary() {
		return "Números de cartões de crédito válidos Diners Club International.";
	}

	@Override
	public String getLink() {
		return "http://pt.wikipedia.org/wiki/Diners_club";
	}

	@Override
	public int getDigitCount() {
		return 16;
	}
}
