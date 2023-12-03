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

        playerTurn = 0;
        if(isAllBankrupt()) {
            JOptionPane.showMessageDialog(null, "모두가 파산했습니다..");
            System.exit(0);
        }
        else if(getCurUserPlayer().getState() != UserPlayer.STATE_PLAYING) nextTurn();

        phase = PHASE_BET;
    }

    public void userBet(int betMoney) {
    	getCurUserPlayer().betMoney(betMoney);
        nextTurn();
        if(isAllPlayerBet()) {
            phase = PHASE_HIT_OR_STAND;
            if(getCurUserPlayer().getHandSum() == 21) userStand();
        }
    }
    
    public void afterBet() {
        dealer.hit(deck.draw());
        for(UserPlayer player : userPlayers) {
            if(player.getState() == UserPlayer.STATE_MONEY_RUN_OUT) continue;
            player.hit(deck.draw());
            player.hit(deck.draw());
            if(player.getHandSum() == 21) {
                player.stand();
            }
        }
        if(isGameDone()) gameResult();
        else if(getCurUserPlayer().getState() == UserPlayer.STATE_STAND) nextTurn();
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

    public boolean isAllPlayerBet() {
        boolean bAllPlayerBet = true;
        for(UserPlayer pl : userPlayers) {
            if(pl.getState() == UserPlayer.STATE_MONEY_RUN_OUT) continue;
            bAllPlayerBet &= pl.getBet() != 0;
        } 
        return bAllPlayerBet;
    }
     
    public boolean isGameDone() {
        boolean bPlayingExist = false;
        for(UserPlayer player : userPlayers) {
            bPlayingExist |= (player.getState() == UserPlayer.STATE_PLAYING);
        }
        return !bPlayingExist;
    }

    public boolean isAllBankrupt() {
        boolean bAllBankrupt = true;
        for(UserPlayer player : userPlayers) {
            bAllBankrupt &= (player.getState() == UserPlayer.STATE_MONEY_RUN_OUT);
        }
        return bAllBankrupt;
    }

    public void gameResult() {
        while(dealer.bHit()) { dealer.hit(deck.draw()); }
        int dealerSum = dealer.getHandSum();

        String resultMsg = "";

        for(UserPlayer player : userPlayers) {
            if(player.getState() == UserPlayer.STATE_MONEY_RUN_OUT) continue;
            int userSum = player.getHandSum();

            String resultStr;

            if(userSum > 21) {
                player.calcResult(RESULT_LOSE); // Player Bust
                resultStr = "LOSE(Player Busted)";
            }
            else if(userSum == 21) {
                if(dealerSum == 21) {
                    player.calcResult(RESULT_TIE); // Both Blackjack
                    resultStr = "TIE(Player and Dealer Blackjack)";
                }
                else {
                    player.calcResult(RESULT_BLACKJACK); // Player Blackjack
                    resultStr = "WIN(Player Blackjack)";
                }
            }
            else if(dealerSum > 21) {
                player.calcResult(RESULT_WIN); // Dealer Bust
                resultStr = "WIN(Dealer Busted)";
            }
            else if(userSum > dealerSum) {
                player.calcResult(RESULT_WIN);
                resultStr = "WIN";
            }
            else if(userSum < dealerSum) {
                player.calcResult(RESULT_LOSE);
                resultStr = "LOSE";
            }
            else if(userSum == dealerSum) {
                player.calcResult(RESULT_TIE);
                resultStr = "TIE";
            }
            else throw new RuntimeException("Unexpected Game Result Case");

            if(player.getMoney() < gameSetting.getMinBet()) player.runOutMoney(); // run out gui

            resultMsg += "플레이어" + (player.getPlayerNum() + 1) + ": " + resultStr + "\n";
        }
        phase = PHASE_GAME_RESULT;
        gui.updateGUI();

        JOptionPane.showMessageDialog(null, resultMsg);

        //prevPlayerTurn = 0;
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
