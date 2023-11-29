package blackjack;

import java.util.Vector;

import java.util.Random;

public class Deck {
	private Vector<Card> cards;
	
	public Deck() {
		cards = new Vector<Card>();
		
		for(int suit = 0; suit < 4; suit++) {
			for(int rank = 1; rank <= Card.SIZE_OF_ONE_SUIT; rank++) {
				cards.add(new Card(suit, rank));
			}
		}
		shuffle();
	}
	
	public void shuffle() {
		Random random = new Random();
		for(int i = cards.size() - 1; i > 0; i--) {
			int j = random.nextInt(i);
			Card tmp = cards.get(i);
			cards.set(i, cards.get(j));
			cards.set(j, tmp);
		}
	}
	
	public Card draw() {
		if(cards.size() == 0) supply();
		Card card = cards.get(cards.size() - 1);
		cards.remove(cards.size() - 1);
		return card;
	}
	
	public void supply() {
		Deck deck = new Deck();
		deck.getCards().addAll(cards);
		cards = deck.getCards();
	}
	
	public Vector<Card> getCards() { return cards; }
	public int getCardCount() { return cards.size(); }
}
