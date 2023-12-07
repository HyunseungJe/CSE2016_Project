package blackjack;

public class GameSetting {
    private int playerNum; 
    private int minBet = 2;
    private int maxBet = 500;
    private String[] playerIds;

    public GameSetting(int playerNum, String[] playerIds) {
        this.playerNum = playerNum;
        this.playerIds = playerIds;
    }

    // Getter & Setter
    public int getPlayerNum() { return playerNum; }
    public String[] getPlayerIds() { return playerIds; }
    public int getMinBet() { return minBet; }
    public int getMaxBet() { return maxBet; }
}
