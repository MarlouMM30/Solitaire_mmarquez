package com.svi.solitaire.objects;

import com.svi.solitaire.enums.Color;
import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

public class Card {
	Rank rank;
	Suit suit;
	private boolean isFaceUp = true;

	public Card(Suit suit, Rank rank) {
		this.rank = rank;
		this.suit = suit;

	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public boolean isFaceUp() {
		return isFaceUp;
	}

	public void setFaceUp(boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}

	public Color getColor() {
		if (suit.getSuitIdentity() == "D" || suit.getSuitIdentity() == "H") {
			return Color.RED;
		} else {
			return Color.BLACK;
		}
	}

	@Override
	public String toString() {
		if (this.isFaceUp == true) {
			return (rank.getRankIdentity() + "-" + suit.getSuitIdentity());
		} else {
			return "[XX]";
		}

	}
}
