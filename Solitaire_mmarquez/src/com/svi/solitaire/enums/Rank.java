package com.svi.solitaire.enums;

public enum Rank {
	KING(13, "K"), QUEEN(12, "Q"), JACK(11, "J"), TEN(10, "10"), NINE(9, "9"), EIGHT(8, "8"), SEVEN(7,
			"7"), SIX(6, "6"), FIVE(5, "5"), FOUR(4, "4"), THREE(3, "3"), TWO(2, "2"), ACE(1, "A");

	private final int rankValue;
	private final String rankIdentity;

	private Rank(int rankValue, String rankIdentity) {
		this.rankValue = rankValue;
		this.rankIdentity = rankIdentity;

	}

	public int getRankValue() {
		return rankValue;
	}

	public String getRankIdentity() {
		return rankIdentity;
	}

}
