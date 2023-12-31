package blackjack.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import blackjack.UserPlayer;


public class UserCardSpace extends CardSpace {
    private JLabel money;
    private JLabel bet;
    private JLabel state;
    
    private JLabel idLabel;

    public UserCardSpace(CardImages cardImages, String text, Color color, String id) {
        super(cardImages, text, color);

        idLabel = new JLabel();
        idLabel.setBounds(20, 40, 150, 50);
        idLabel.setText("(" + id + ")");
        this.add(idLabel);
        
        money = new JLabel();
        money.setBounds(20, 70, 70, 50);
        this.add(money);

        bet = new JLabel();
        bet.setBounds(20, 100, 150, 50);
        this.add(bet);
         
        state = new JLabel();
        state.setBounds(20, 200, 200, 100);
		state.setFont(new Font("Serif", Font.PLAIN, 20));
        this.add(state);
    }
    
    public void updateLabel(int money, int bet, int state) {
        this.money.setText("돈 : " + money + "$");
        this.bet.setText("베팅 금액 : " + bet + "$");
        String str = "";
        if(state == UserPlayer.STATE_STAND) str = "STAND";
        else if(state == UserPlayer.STATE_BUSTED) str = "BUSTED";
        else if(state == UserPlayer.STATE_MONEY_RUN_OUT) str = "BANKRUPT"; 
        this.state.setText(str);
    }

    public void setBackgroundByUserState(int state) {
        if(state == UserPlayer.STATE_BUSTED) {
            setBackground(Color.GRAY);
        }
        else if(state == UserPlayer.STATE_STAND) {
            setBackground(Color.YELLOW);
        }
        else if(state == UserPlayer.STATE_PLAYING) {
            setBackground(this.color);
        }
        else if(state == UserPlayer.STATE_MONEY_RUN_OUT) {
            setBackground(Color.DARK_GRAY);
        }
    }
}
