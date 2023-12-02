package blackjack;

import javax.swing.*;

import blackjack.gui.IGUIupdater;

public class Game { 
    private static final double RESULT_WIN = 2;
    private static final double RESULT_BLACKJACK = 2.5;
    private static final double RESULT_LOSE = 0;
    private static final double RESULT_TIE = 1;
    
    public static final int PHASE_BET = 1;
    public static final int PHASE_HIT_OR_STAND = 2;
    public static final int PHASE_GAME_RESULT = 3;
    
    private int phase;
    
    private int minBet;
    private int maxBet;

    private Deck deck;
    private Dealer dealer;
    private UserPlayer userPlayer;

    private IGUIupdater gui;

    public Game(IGUIupdater gui) {
    	minBet = 2;
    	maxBet = 500;
    	
        deck = new Deck();
        dealer = new Dealer();
        userPlayer = new UserPlayer(1000);
        this.gui = gui;
        init();
    }

    public void init() {
        dealer.clear();
        userPlayer.clear();

        phase = PHASE_BET;

        gui.updateGUI();
    }
    
    public void afterBet() {
    	dealer.hit(deck.draw());
        userPlayer.hit(deck.draw());
        userPlayer.hit(deck.draw());
        
        phase = PHASE_HIT_OR_STAND;
        
        gui.updateGUI();
        
        if(userPlayer.getHandSum() == 21) {
        	userStand();
        }
    }

    public void userHit() {
    	if(phase == PHASE_HIT_OR_STAND) {
    		userPlayer.hit(deck.draw());
            int userSum = userPlayer.getHandSum();
            if(userSum > 21) gameResult(RESULT_LOSE, "You Busted. You Loseㅠㅠ");
            else if(userSum == 21) userStand();
            gui.updateGUI();
    	}   
    }

    public void userStand() {
    	if(phase == PHASE_HIT_OR_STAND) {
    		while(dealer.bHit()) { dealer.hit(deck.draw()); }
            int userSum = userPlayer.getHandSum();
            int dealerSum = dealer.getHandSum();

            if(userSum == dealerSum) gameResult(RESULT_TIE, null);
            else if(userSum == 21) gameResult(RESULT_BLACKJACK, null);
            else if(dealerSum > 21) gameResult(RESULT_WIN, "Dealer Busted. You Win!!");
            else if(dealerSum == 21) gameResult(RESULT_LOSE, "Dealer Blackjack. You Loseㅠㅠ");
            else if(userSum > dealerSum) gameResult(RESULT_WIN, null);
            else if(userSum < dealerSum) gameResult(RESULT_LOSE, null);
            gui.updateGUI();
    	}
    }
    
    public void userBet(int betMoney) {
    	userPlayer.betMoney(betMoney);
    }

    public void gameResult(double multiplier, String message) {
        gui.updateGUI();
        String msg;
        if(multiplier == RESULT_WIN) {
            msg = message != null ? message : "You Win!!";
        }
        else if(multiplier == RESULT_LOSE) {
        	msg = message != null ? message : "You Loseㅠㅠ!!";
        }
        else if(multiplier == RESULT_TIE) {
        	msg = message != null ? message : "Tie";
        }
        else if(multiplier == RESULT_BLACKJACK) {
        	msg = message != null ? message : "Blackjack!!!!!";
        }
        else throw new RuntimeException("Invaild gameResult: " + multiplier);
        JOptionPane.showMessageDialog(null, msg);
        
        userPlayer.calcResult(multiplier);
        phase = PHASE_GAME_RESULT;
    }

    // Getter & Setter
    public int getPhase() { return phase; }
    public void setPhase(int phase) { this.phase = phase; }
    public Dealer getDealer() { return dealer; }
    public UserPlayer getUserPlayer() { return userPlayer; }
    public int getMinBet() { return minBet; }
    public int getMaxBet() { return maxBet; }

}
