package com.svi.solitaire.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.objects.Card;
import com.svi.solitaire.objects.Deck;

public class SolitaireMethods {

	public static Deck deck = new Deck();

	public static Rank getRank(String rankIdentity) {

		for (Rank ranks : Rank.values()) {
			if (ranks.getRankIdentity().equals(rankIdentity)) {

				return ranks;
			}
		}

		return null;
	}

	public static Suit getSuit(String suitIdentity) {

		for (Suit suits : Suit.values()) {
			if (suits.getSuitIdentity().equals(suitIdentity)) {

				return suits;
			}
		}

		return null;
	}

	public static Deck createDeck() {

		try {
			Scanner scanner = new Scanner(new File("input.txt"));
			String[] lineText = scanner.nextLine().split(",");

			for (String delimeterText : lineText) {
				String token[] = delimeterText.split("-");
				Suit suit = getSuit(token[0]);
				Rank rank = getRank(token[1]);
				Card card = new Card(suit, rank);
				deck.add(card);

			}

			scanner.close();

			System.out.println("KLONDIKE SOLITAIRE");
			System.out.println("\nINPUT FILE LOADED SUCCESSFULLY!!!");
			System.out.println("\nHere is the Initial Deck of Cards: \n");
			System.out.println(deck.getDeck().toString());

		} catch (IOException e) {

			System.out.println("\nFILE NOT FOUND!!!");
		}
		return deck;

	}

	public static ArrayList<ArrayList<Card>> createManoeuvre(Deck deck) {

		ArrayList<ArrayList<Card>> manoeuvreTableau = new ArrayList<ArrayList<Card>>();

		for (int i = 0; i < 7; i++) {
			ArrayList<Card> manoeuvereLine = new ArrayList<Card>();
			manoeuvreTableau.add(manoeuvereLine);

		}

		for (int j = 0; j < manoeuvreTableau.size(); j++) {
			for (int k = j; k < manoeuvreTableau.size(); k++) {

				Card card = deck.removeCard(0);

				if (k != j) {
					card.setFaceUp(false);

				}

				manoeuvreTableau.get(k).add(card);
			}

		}

		ArrayList<Card> remainingCards = deck.getDeck();
		System.out.println("\nWASTE: ");
		System.out.println(remainingCards + "\n");
		System.out.println("MANOEUVERE TABLEAU: ");
		for (int l = 0; l < manoeuvreTableau.size(); l++) {
			System.out.println(manoeuvreTableau.get(l));

		}

		return manoeuvreTableau;

	}

	public static ArrayList<ArrayList<Card>> createFoundation() {

		ArrayList<ArrayList<Card>> foundationTableau = new ArrayList<ArrayList<Card>>();

		System.out.println("\nFOUNDATION: ");

		for (int x = 0; x < 4; x++) {
			ArrayList<Card> foundationLine = new ArrayList<Card>();
			foundationTableau.add(foundationLine);

		}
		System.out.println(foundationTableau + " ");

		return foundationTableau;
	}

	public static ArrayList<Card> drawCard(int draw, ArrayList<Card> talon) {

		if (deck.getDeck().isEmpty()) {
			Main.isGameEnded = true;
			if (deck.getDeck().isEmpty()) {
				return null;
			}
			deck.getDeck().addAll(talon);
			talon.clear();
		}

		for (int m = 0; m < draw; m++) {
			Card card = deck.removeCard(0);
			talon.add(card);
			if (deck.getDeck().isEmpty()) {
				break;
			}

		}
		System.out.println("\nTALON:");
		System.out.println(talon);

		return talon;
	}

	private static int countOpenCards(ArrayList<Card> manoeuvereLine) {

		int count = 0;
		for (Card card : manoeuvereLine) {
			if (card.isFaceUp() == true)
				count++;
		}
		return count;
	}

	public static void moveCardToFoundation(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> manoeuvreTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> manoeuvreLine : manoeuvreTableau) {
			if (!manoeuvreLine.isEmpty()) {
				Card lastCardOfTheLine = manoeuvreLine.get(manoeuvreLine.size() - 1);
				if (lastCardOfTheLine.getRank() == Rank.ACE) {
					System.out.println("\n" + lastCardOfTheLine.toString() + " moved to the Foundation");
					System.out.println(
							"__________________________________________________________________________________________________________");
					foundationTableau.get(lastCardOfTheLine.getSuit().getSuitValue())
							.add(manoeuvreLine.remove(manoeuvreLine.size() - 1));
					if (!manoeuvreLine.isEmpty()) {
						manoeuvreLine.get(manoeuvreLine.size() - 1).setFaceUp(true);
					}

					printAll(foundationTableau, manoeuvreTableau, talon);
					Main.isGameEnded = false;
					checkOtherMoves(foundationTableau, manoeuvreTableau, talon);
					return;

				} else {
					for (ArrayList<Card> suitFoundation : foundationTableau) {
						if (!suitFoundation.isEmpty()) {
							Card lastCardOfTheFoundation = suitFoundation.get(suitFoundation.size() - 1);
							if (lastCardOfTheLine.getRank().getRankValue() - 1 == lastCardOfTheFoundation.getRank()
									.getRankValue()
									&& lastCardOfTheFoundation.getSuit() == lastCardOfTheLine.getSuit()) {
								System.out.println("\n" + lastCardOfTheLine.toString() + " moved to the Foundation");
								System.out.println(
										"__________________________________________________________________________________________________________");
								suitFoundation.add(manoeuvreLine.remove(manoeuvreLine.size() - 1));
								if (!manoeuvreLine.isEmpty()) {
									manoeuvreLine.get(manoeuvreLine.size() - 1).setFaceUp(true);
								}

								printAll(foundationTableau, manoeuvreTableau, talon);
								Main.isGameEnded = false;
								checkOtherMoves(foundationTableau, manoeuvreTableau, talon);
								return;
							}
						}
					}
				}
			}
		}

		if (!talon.isEmpty()) {
			Card lastCardOfTheTalon = talon.get(talon.size() - 1);
			if (lastCardOfTheTalon.getRank() == Rank.ACE) {
				System.out.println("\n" + lastCardOfTheTalon.toString() + " moved to the Foundation");
				System.out.println(
						"__________________________________________________________________________________________________________");
				foundationTableau.get(lastCardOfTheTalon.getSuit().getSuitValue()).add(talon.remove(talon.size() - 1));

				printAll(foundationTableau, manoeuvreTableau, talon);
				Main.isGameEnded = false;
				checkOtherMoves(foundationTableau, manoeuvreTableau, talon);
				return;

			} else {
				for (ArrayList<Card> suitFoundation : foundationTableau) {
					if (!suitFoundation.isEmpty()) {
						Card lastCardOfTheFoundation = suitFoundation.get(suitFoundation.size() - 1);
						if (lastCardOfTheTalon.getRank().getRankValue() - 1 == lastCardOfTheFoundation.getRank()
								.getRankValue() && lastCardOfTheTalon.getSuit() == lastCardOfTheFoundation.getSuit()) {
							System.out.println("\n" + lastCardOfTheTalon.toString() + " moved to the Foundation");
							System.out.println(
									"__________________________________________________________________________________________________________");
							suitFoundation.add(talon.remove(talon.size() - 1));

							printAll(foundationTableau, manoeuvreTableau, talon);
							Main.isGameEnded = false;
							checkOtherMoves(foundationTableau, manoeuvreTableau, talon);
							return;
						}
					}
				}
			}
		}
	}

	public static void moveCardFromTalonToLine(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> manoeuvreTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> manoeuvreLine : manoeuvreTableau) {
			if (!manoeuvreLine.isEmpty()) {
				Card lastCardOfTheLine = manoeuvreLine.get(manoeuvreLine.size() - 1);
				if (!talon.isEmpty()) {
					Card lastCardOfTheTalon = talon.get(talon.size() - 1);
					if (lastCardOfTheLine.getRank().getRankValue() - 1 == lastCardOfTheTalon.getRank().getRankValue()
							&& lastCardOfTheTalon.getColor() != lastCardOfTheLine.getColor()) {
						System.out.println("\n" + lastCardOfTheTalon.toString() + " moved to the Maneouvere Line");
						System.out.println(
								"__________________________________________________________________________________________________________");
						manoeuvreLine.add(talon.remove(talon.size() - 1));

						printAll(foundationTableau, manoeuvreTableau, talon);
						Main.isGameEnded = false;
						checkOtherMoves(foundationTableau, manoeuvreTableau, talon);
						return;
					}

				}
			}
		}
	}

	public static void moveKingToEmptyLine(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> manoeuvreTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> sourceManoeuvreLine : manoeuvreTableau) {
			for (ArrayList<Card> destinationManoeuvreLine : manoeuvreTableau) {
				if (!sourceManoeuvreLine.isEmpty() && destinationManoeuvreLine.isEmpty()) {
					Card firstFaceUpCardOfTheLine = sourceManoeuvreLine
							.get(sourceManoeuvreLine.size() - countOpenCards(sourceManoeuvreLine));
					if (firstFaceUpCardOfTheLine.getRank() == Rank.KING
							&& sourceManoeuvreLine.get(0).getRank() != Rank.KING) {
						int indexToBeMove = (sourceManoeuvreLine.size() - countOpenCards(sourceManoeuvreLine));
						for (int x = 0; x < countOpenCards(sourceManoeuvreLine); x++) {
							System.out.print("\n" + sourceManoeuvreLine.get(indexToBeMove) + " ");
							destinationManoeuvreLine.add(sourceManoeuvreLine.remove(indexToBeMove));
						}
						System.out.println(" moved to the Empty Line");
						System.out.println(
								"__________________________________________________________________________________________________________");
						if (!sourceManoeuvreLine.isEmpty()) {
							sourceManoeuvreLine.get(sourceManoeuvreLine.size() - 1).setFaceUp(true);
						}

						printAll(foundationTableau, manoeuvreTableau, talon);
						Main.isGameEnded = false;
						checkOtherMoves(foundationTableau, manoeuvreTableau, talon);
						return;

					}
				}

				if (!talon.isEmpty()) {
					if (destinationManoeuvreLine.isEmpty()) {
						Card lastCardOfTheTalon = talon.get(talon.size() - 1);
						if (lastCardOfTheTalon.getRank() == Rank.KING) {
							System.out.println("\n" + lastCardOfTheTalon.toString() + " moved to the Empty Line");
							System.out.println(
									"__________________________________________________________________________________________________________");
							destinationManoeuvreLine.add(talon.remove(talon.size() - 1));

							printAll(foundationTableau, manoeuvreTableau, talon);
							Main.isGameEnded = false;
							checkOtherMoves(foundationTableau, manoeuvreTableau, talon);
							return;
						}

					}
				}
			}
		}
	}

	public static void moveCardFromLineToLine(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> manoeuvreTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> sourceManoeuvreLine : manoeuvreTableau) {
			for (ArrayList<Card> destinationManoeuvreLine : manoeuvreTableau) {
				if (!sourceManoeuvreLine.isEmpty() && !destinationManoeuvreLine.isEmpty()) {
					Card firstFaceUpCardOfTheLine = sourceManoeuvreLine
							.get(sourceManoeuvreLine.size() - countOpenCards(sourceManoeuvreLine));
					Card lastCardOfTheLine = destinationManoeuvreLine.get(destinationManoeuvreLine.size() - 1);
					if (firstFaceUpCardOfTheLine.getRank().getRankValue() == lastCardOfTheLine.getRank().getRankValue()
							- 1 && firstFaceUpCardOfTheLine.getColor() != lastCardOfTheLine.getColor()) {
						int indexToBeMoved = (sourceManoeuvreLine.size() - countOpenCards(sourceManoeuvreLine));
						for (int x = 0; x < countOpenCards(sourceManoeuvreLine); x++) {
							System.out.print("\n" + sourceManoeuvreLine.get(indexToBeMoved) + " ");
							destinationManoeuvreLine.add(sourceManoeuvreLine.remove(indexToBeMoved));
						}
						System.out.println(" moved to Destination Line");
						System.out.println(
								"__________________________________________________________________________________________________________");
						if (!sourceManoeuvreLine.isEmpty()) {
							sourceManoeuvreLine.get(sourceManoeuvreLine.size() - 1).setFaceUp(true);
						}

						printAll(foundationTableau, manoeuvreTableau, talon);
						Main.isGameEnded = false;
						checkOtherMoves(foundationTableau, manoeuvreTableau, talon);
						return;
					}
				}
			}
		}
	}

	public static void printAll(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> manoeuvreTableau, ArrayList<Card> talon) {

		System.out.print("\nTALON: ");
		System.out.println(talon + "\n");
		System.out.println("REMAINING CARDS IN WASTE:");
		System.out.println(deck.getDeck() + "\n");

		System.out.println(foundationTableau + "\n");
		System.out.println("MANOEUVRE TABLEAU: ");
		for (int l = 0; l < manoeuvreTableau.size(); l++) {
			System.out.println(manoeuvreTableau.get(l));
		}
	}

	public static void checkIfGameWon(ArrayList<ArrayList<Card>> foundationTableau) {

		int totalNumberOfCardsInFoundation = foundationTableau.get(0).size() + foundationTableau.get(1).size()
				+ foundationTableau.get(2).size() + foundationTableau.get(3).size();
		if (totalNumberOfCardsInFoundation == 52) {
			Main.isGameEnded = true;
			System.out.println("\nWINNER");

			}
		}
	
	public static void checkOtherMoves (ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> manoeuvreTableau, ArrayList<Card> talon){
		
		moveCardToFoundation(foundationTableau, manoeuvreTableau, talon);
		moveCardFromTalonToLine(foundationTableau, manoeuvreTableau, talon);
		moveKingToEmptyLine(foundationTableau, manoeuvreTableau, talon);
		moveCardFromLineToLine(foundationTableau, manoeuvreTableau, talon);
		
	}
}
