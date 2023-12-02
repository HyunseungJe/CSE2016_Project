package blackjack;

public class UserPlayer extends Player { 
    private int money;
    private int bet = 0;
    private int prevBet = 100;
    private int lastGameResult = -1;
    private int userNum;

    public static final int STATE_PLAYING = 0;
    public static final int STATE_STAND = 1;
    public static final int STATE_BUSTED = 2;
    public static final int STATE_MONEY_RUN_OUT = 3;

    private int state = STATE_PLAYING;

    public UserPlayer(int money, int userNum) {
        super();
        this.money = money;
        this.userNum = userNum;
    }

    public void betMoney(int bet) {
        money -= bet;
        this.bet = bet;
        prevBet = bet;
    }

    public void calcResult(int result) {
        double multiplier;
        if(result == Game.RESULT_WIN) multiplier = 2;
        else if(result == Game.RESULT_BLACKJACK) multiplier = 2.5;
        else if(result == Game.RESULT_LOSE) multiplier = 0;
        else if(result == Game.RESULT_TIE) multiplier = 1;
        else throw new RuntimeException("Invalid Game Result: " + result);

        money += (int)(bet * multiplier);
        prevBet = bet;
        bet = 0;
        lastGameResult = result;
    }

    public void stand() { state = STATE_STAND; }
    public void bust() { state = STATE_BUSTED; }
    public void runOutMoney() { state = STATE_MONEY_RUN_OUT; }

    @Override
    public void clear() {
        super.clear();
        if(state != STATE_MONEY_RUN_OUT) state = STATE_PLAYING;
    }

    // Getter
    public int getMoney() { return money; }
    public int getBet() { return bet; }
    public int getPrevBet() { return prevBet; }
    public int getState() { return state; }
    public int getLastGameResult() { return lastGameResult; }
    public int getPlayerNum() { return userNum; }
}
