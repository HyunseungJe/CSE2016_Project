package blackjack;

import javax.swing.*;

import blackjack.gui.IGUIupdater;

public class Game { 
    private static final double RESULT_WIN = 2;
    private static final double RESULT_BLACKJACK = 2.5;
    private static final double RESULT_LOSE = 0;
    private static final double RESULT_TIE = 1;

    private Deck deck;
    private Dealer dealer;
    private UserPlayer userPlayer;

    private IGUIupdater gui;

    Game(IGUIupdater gui) {
        deck = new Deck();
        dealer = new Dealer();
        userPlayer = new UserPlayer(1000);
        this.gui = gui;
        init();
    }

    public void init() {
        dealer.clear();
        userPlayer.clear();

        dealer.hit(deck.draw());
        userPlayer.hit(deck.draw());
        userPlayer.hit(deck.draw());

        userPlayer.betMoney(100); // 베팅 금액 설정은 아직 미구현

        gui.updateGUI();

        if(userPlayer.getHandSum() == 21) userStand();
    }

    public void userHit() {
        userPlayer.hit(deck.draw());
        int userSum = userPlayer.getHandSum();
        if(userSum > 21) gameResult(RESULT_LOSE);
        else if(userSum == 21) userStand();
    }

    public void userStand() {
        while(dealer.bHit()) { dealer.hit(deck.draw()); }
        int userSum = userPlayer.getHandSum();
        int dealerSum = dealer.getHandSum();

        if(userSum == dealerSum) gameResult(RESULT_TIE);
        else if(userSum == 21) gameResult(RESULT_BLACKJACK);
        else if(dealerSum > 21) gameResult(RESULT_WIN);
        else if(dealerSum == 21) gameResult(RESULT_LOSE);
        else if(userSum > dealerSum) gameResult(RESULT_WIN);
        else if(userSum < dealerSum) gameResult(RESULT_LOSE);
    }

    public void gameResult(double multiplier) {
        gui.updateGUI();
        if(multiplier == RESULT_WIN) {
            JOptionPane.showConfirmDialog(null, "You Win!!", "Game Result", JOptionPane.OK_CANCEL_OPTION);
        }
        else if(multiplier == RESULT_LOSE) {
            JOptionPane.showConfirmDialog(null, "You Loseㅠㅠ", "Game Result", JOptionPane.OK_CANCEL_OPTION);
        }
        else if(multiplier == RESULT_TIE) {
            JOptionPane.showConfirmDialog(null, "Tie", "Game Result", JOptionPane.OK_CANCEL_OPTION);
        }
        else if(multiplier == RESULT_BLACKJACK) {
            JOptionPane.showConfirmDialog(null, "Blackjack!!!!!", "Game Result", JOptionPane.OK_CANCEL_OPTION);
        }
        else throw new RuntimeException("Invaild gameResult: " + multiplier);

        userPlayer.calcResult(multiplier);
        init();
    }

    // @Temporary
    public Dealer getDealer() { return dealer; }
    public UserPlayer getUserPlayer() { return userPlayer; }

}
