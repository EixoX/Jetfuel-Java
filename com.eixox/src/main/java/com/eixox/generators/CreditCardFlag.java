package com.eixox.generators;

public enum CreditCardFlag {
	Amex(0), 
	Bankcard(1), 
	DinersClubCarteBlanche(3), 
	DinersClubInternational(5), 
	DinersClub_UnitedStates_Canada(6), 
	DiscoverCard(7), InstaPayment(8), JCB(9), Laser(
			10), Maestro(11), MasterCard(12), Solo(13), Switch(14), Visa(15), VisaElectron(16);

	private final int mask;

	private CreditCardFlag(int mask) {
		this.mask = mask;
	}

	public int getMask() {
		return mask;
	}
}
