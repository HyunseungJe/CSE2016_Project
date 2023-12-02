package blackjack;

import javax.swing.*;

import blackjack.gui.IGUIupdater;

public class Game {     
    public static final int RESULT_WIN = 1;
    public static final int RESULT_BLACKJACK = 2;
    public static final int RESULT_LOSE = 3;
    public static final int RESULT_TIE = 4;

    public static final int PHASE_BET = 1;
    public static final int PHASE_HIT_OR_STAND = 2;
    public static final int PHASE_GAME_RESULT = 3;
    
    private int prevPlayerTurn = 0;
    private int playerTurn = 0;
    private int phase;
    
    private Deck deck;
    private Dealer dealer;
    private UserPlayer[] userPlayers;
    private GameSetting gameSetting;

    private IGUIupdater gui;

    public Game(IGUIupdater gui, GameSetting gameSetting) {
        this.gameSetting = gameSetting;
        this.gui = gui;
    	
        deck = new Deck();
        dealer = new Dealer();

        userPlayers = new UserPlayer[gameSetting.getPlayerNum()];
        for(int i = 0; i < gameSetting.getPlayerNum(); i++) userPlayers[i] = new UserPlayer(1000, i);
    }

    public void init() {
        dealer.clear();
        for(UserPlayer player : userPlayers) {
            player.clear();
        }
        phase = PHASE_BET;

    }
    
    public void afterBet() {
        dealer.hit(deck.draw());
        for(UserPlayer player : userPlayers) {
            player.hit(deck.draw());
            player.hit(deck.draw());
            if(player.getHandSum() == 21) {
                player.stand();
            }
        } 
        if(isGameDone()) gameResult();
    }

    public void userHit() {
    	if(phase == PHASE_HIT_OR_STAND) {
            UserPlayer userPlayer = getCurUserPlayer();
    		userPlayer.hit(deck.draw());
            int userSum = userPlayer.getHandSum();
            if(userSum == 21) userStand();
            else {
                if(userSum > 21) userPlayer.bust();
                nextTurn();
                //gui.updateGUI();
                if(isGameDone()) gameResult();
            }
    	}   
    }

    public void userStand() {
    	if(phase == PHASE_HIT_OR_STAND) {
            UserPlayer userPlayer = getCurUserPlayer();
            userPlayer.stand();
            nextTurn();
            if(isGameDone()) gameResult();
    	}
    }
    
    public void userBet(int betMoney) {
    	getCurUserPlayer().betMoney(betMoney);
        nextTurn();
        if(playerTurn == 0) {
            phase = PHASE_HIT_OR_STAND;
            if(getCurUserPlayer().getHandSum() == 21) userStand();
        }
    }
    
    public boolean isGameDone() {
        boolean bPlayingExist = false;
        for(UserPlayer player : userPlayers) {
            bPlayingExist |= (player.getState() == UserPlayer.STATE_PLAYING);
        }
        return !bPlayingExist;
    }

    public void gameResult() {
        while(dealer.bHit()) { dealer.hit(deck.draw()); }
        int dealerSum = dealer.getHandSum();
        for(UserPlayer player : userPlayers) {
            int userSum = player.getHandSum();

            if(userSum > 21) player.calcResult(RESULT_LOSE); // Player Bust
            else if(userSum == 21) {
                if(dealerSum == 21) player.calcResult(RESULT_TIE); // Both Blackjack
                else player.calcResult(RESULT_BLACKJACK); // Player Blackjack
            }
            else if(dealerSum > 21) player.calcResult(RESULT_WIN); // Dealer Bust
            else if(userSum > dealerSum) player.calcResult(RESULT_WIN);
            else if(userSum < dealerSum) player.calcResult(RESULT_LOSE);
            else if(userSum == dealerSum) player.calcResult(RESULT_TIE);
            else throw new RuntimeException("Unexpected Game Result Case");

            if(player.getMoney() < gameSetting.getMinBet()) player.runOutMoney(); // run out gui
        }

        playerTurn = 0;
        //prevPlayerTurn = 0;
        phase = PHASE_GAME_RESULT;
    }

    public void nextTurn() {
        prevPlayerTurn = playerTurn;
        do {
            playerTurn++;
            playerTurn %= gameSetting.getPlayerNum();
        }
        while(getCurUserPlayer().getState() != UserPlayer.STATE_PLAYING && !isGameDone());
    }

    // Getter & Setter
    public int getPhase() { return phase; }
    public void setPhase(int phase) { this.phase = phase; }
    public Dealer getDealer() { return dealer; }
    public UserPlayer[] getUserPlayers() { return userPlayers; }
    public UserPlayer getCurUserPlayer() { return userPlayers[playerTurn]; }
    public UserPlayer getPrevUserPlayer() { return userPlayers[prevPlayerTurn]; }
    public GameSetting getGameSetting() { return gameSetting; }
}
