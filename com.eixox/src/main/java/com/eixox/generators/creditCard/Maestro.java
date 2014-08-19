package com.eixox.generators.creditCard;

import com.eixox.generators.CreditCardGenerator;


public class Maestro extends CreditCardGenerator {

	public Maestro() {
		super("5018", "5020", "5038", "5893", "6304", "6759", "6761", "6762", "6763", "0606");
	}

	@Override
	public String getTitle() {
		return "Maestro";
	}

	@Override
	public String getSummary() {
		return "Números de cartões de crédito válidos Maestro.";
	}

	@Override
	public String getLink() {
		return "http://pt.wikipedia.org/wiki/MasterCard_Maestro";
	}

	@Override
	public int getDigitCount() {
		return 16;
	}

}
