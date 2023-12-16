package blackjack.gui;

import javax.swing.*;
import java.awt.*;

import java.util.Vector;
import blackjack.Card;

public class CardSpace extends JPanel {
    private Vector<Card> cards;
    private CardImages cardImages;
    private JLabel label;
    protected Color color;
 
    public CardSpace(CardImages cardImages, String text, Color color) {
        super();
        this.color = color;
        setBackground(color);
        setLayout(null);
        this.cardImages = cardImages;

        label = new JLabel(text);
        label.setBounds(20, 10, 70, 50);
        this.add(label);

    }

    public void paint(Graphics g) {
        super.paint(g);
        if(cards == null) return; // If cards == null, draw empty space
        for(int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            //g.drawImage(cardImages.getImage(card.getSuit(), card.getRank()), 100 + i % 3 * 20 + i / 3 * 150, 30 + i % 3 * 20, this);
            g.drawImage(cardImages.getImage(card.getSuit(), card.getRank()), 150 + i * 20, 30 + i * 20, this);
        }
    }

    public void setDisplayCards(Vector<Card> cards) { this.cards = cards; }
}
