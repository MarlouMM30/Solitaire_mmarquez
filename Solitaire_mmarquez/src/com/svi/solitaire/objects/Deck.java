package com.svi.solitaire.objects;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck;

	public Deck() {
		this.deck = new ArrayList<Card>();
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}
	public Card removeCard(int i) {
		return this.deck.remove(i);
	}

	public void add(Card card) {
		this.deck.add(card);
	}

	public void shuffle() {
		Collections.shuffle(this.deck);
	}
	
}
