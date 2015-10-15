package com.eixox.generators.creditCard;

import com.eixox.generators.CreditCardGenerator;

public class Elo extends CreditCardGenerator {

	public Elo() {
		super("636368", "438935", "504175", "451416", "636297", "5067", "4576", "4011", "506699");
	}

	@Override
	public String getTitle() {
		return "Elo";
	}

	@Override
	public String getSummary() {
		return "Números de cartões de crédito validos Elo.";
	}

	@Override
	public String getLink() {
		return "https://gist.github.com/erikhenrique/5931368";
	}

	@Override
	public int getDigitCount() {
		return 16;
	}

}
