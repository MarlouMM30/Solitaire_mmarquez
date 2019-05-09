package com.svi.solitaire.enums;

public enum Suit {
	DIAMONDS(3, "D"), HEARTS(2, "H"), SPADES(1, "S"), CLUBS(0, "C");

	private final int suitValue;
	private final String suitIdentity;

	private Suit(int suitValue, String suitIdentity) {
		this.suitValue = suitValue;
		this.suitIdentity = suitIdentity;
	}

	public int getSuitValue() {
		return suitValue;
	}

	public String getSuitIdentity() {
		return suitIdentity;
	}

}
