package blackjack;

public class Dealer extends Player {
    public boolean bHit() {
        return getHandSum() <= 16;
    } 
}
