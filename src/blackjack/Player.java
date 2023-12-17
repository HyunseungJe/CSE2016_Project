package blackjack;

import java.util.Vector;

public abstract class Player {
    protected Vector<Card> hand;

    protected Player() {
        hand = new Vector<Card>();
    }

    public void hit(Card card) {
        hand.add(card);
    }

    public int getHandSum() {
        int aceCnt = 0;
        int sum = 0;
        for(Card card : hand) {
            int rank = card.getRank();
            if(rank == Card.ACE) {
                aceCnt++;
                sum += 11;
            }
            else if(rank >= Card.JACK) sum += 10;
            else sum += rank;
        }
        while(aceCnt > 0 && sum > 21) {
            sum -= 10;
            aceCnt--;
        }
        return sum;
    }

    public Vector<Card> getHand() { return hand; }
    public abstract void clear();
}
