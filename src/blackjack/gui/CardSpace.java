package blackjack.gui;

import javax.swing.*;
import java.awt.*;

import java.util.Vector;
import blackjack.Card;

public class CardSpace extends JPanel {
    private Vector<Card> cards;
    private CardImages cardImages;
    private JLabel label;
    
    public CardSpace(Vector<Card> cards, CardImages cardImages, String text, Color color) {
        super();
        setBackground(color);
        setLayout(null);
        this.cards = cards;
        this.cardImages = cardImages;

        label = new JLabel(text);
        label.setBounds(20, 10, 70, 50);
        this.add(label);
    }

    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            g.drawImage(cardImages.getImage(card.getSuit(), card.getRank()), 100 + i % 3 * 20 + i / 3 * 150, 30 + i % 3 * 20, this);
        }
    }
}
