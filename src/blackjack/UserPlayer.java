package blackjack;

public class UserPlayer extends Player { 
    private int money;
    private int bet = 0;

    public UserPlayer(int money) {
        super();
        this.money = money;
    }

    public void betMoney(int bet) {
        money -= bet;
        this.bet = bet;
    }

    public void calcResult(double multiplier) {
        money += (int)(bet * multiplier);
        bet = 0;
    }

    public int getMoney() { return money; }
    public int getBet() { return bet; }
}
