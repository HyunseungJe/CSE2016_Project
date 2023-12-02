package blackjack;

public class GameSetting {
    private int playerNum; 
    private int minBet = 2;
    private int maxBet = 500;

    public GameSetting(int playerNum) {
        this.playerNum = playerNum;
    }

    // Getter & Setter
    public int getPlayerNum() { return playerNum; }
    public int getMinBet() { return minBet; }
    public int getMaxBet() { return maxBet; }
}
