package blackjack.gui;

import java.awt.Image;
import javax.swing.ImageIcon;
import blackjack.Card;

public class CardImages {
    private Image[] images = new Image[Card.SIZE_OF_ONE_SUIT * 4 + 1];
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 144;

    public CardImages() {
        for(int suit = 0; suit < 4; suit++) {
            for(int rank = 1; rank <= Card.SIZE_OF_ONE_SUIT; rank++) {
                images[suit * Card.SIZE_OF_ONE_SUIT + rank] = new ImageIcon("src\\images\\" + getCardName(suit, rank) + ".png").getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            }
        }
    }

    private String getCardName(int suit, int rank) {
        String strSuit;
        if(suit == Card.SPADES) strSuit = "spades";
        else if(suit == Card.HEARTS) strSuit = "hearts";
        else if(suit == Card.DIAMONDS) strSuit = "diamonds";
        else if(suit == Card.CLUBS) strSuit = "clubs";
        else throw new RuntimeException("Cannot get strSuit: " + suit);

        String strRank;
        if(2 <= rank && rank <= 10) strRank = Integer.toString(rank);
        else if(rank == Card.ACE) strRank = "ace";
        else if(rank == Card.JACK) strRank = "jack";
        else if(rank == Card.QUEEN) strRank = "queen";
        else if(rank == Card.KING) strRank = "king";
        else throw new RuntimeException("Cannot get strRank: " + rank);

        return strRank + "_of_" + strSuit;
    }

    public Image getImage(int suit, int rank) {
        return images[suit * Card.SIZE_OF_ONE_SUIT + rank];
    }
}
