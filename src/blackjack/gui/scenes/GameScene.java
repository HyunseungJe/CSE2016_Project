package blackjack.gui.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import blackjack.Game;
import blackjack.GameSetting;
import blackjack.gui.CardImages;
import blackjack.gui.CardSpace;
import blackjack.gui.Scene;

public class GameScene extends Scene {
	private GameSetting setting;
	private Game game;

	private CardImages cardImages = new CardImages();
	private CardSpace dealerCardSpace;
	private CardSpace userCardSpace;
	
	private JLabel betLabel;
	private JLabel moneyLabel;
	private JLabel toBetLabel;
	
	private JButton hitButton;
	private JButton standButton;
	private JButton betButton;
	private JTextField betTextField;
	
	public GameScene() {
		
		setLayout(new BorderLayout());
		JPanel cardSpace = new JPanel();
		cardSpace.setBackground(Color.GRAY);
		cardSpace.setLayout(new GridLayout(2, 1));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setPreferredSize(new Dimension(0, 150));
		buttonPanel.setLayout(null);

		add(cardSpace, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		hitButton = new JButton("Hit");
		hitButton.setBounds(500, 50, 80, 50);

		standButton = new JButton("Stand");
		standButton.setBounds(650, 50, 80, 50);
		
		betButton = new JButton("Bet");
		betButton.setBounds(200, 75, 120, 40);
		
		betTextField = new JTextField();
		betTextField.setText("100");
		betTextField.setBounds(200, 40, 120, 20);
		betTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void insertUpdate(DocumentEvent e) { update(); }
			@Override public void removeUpdate(DocumentEvent e) { update(); }
			@Override public void changedUpdate(DocumentEvent e) { update(); }
			public void update() {
				int bet;
				String str = betTextField.getText();
				try {
					bet = Integer.parseInt(str);
					if(bet < game.getMinBet()) throw new RuntimeException();
					else if(bet > game.getMaxBet()) throw new RuntimeException();
					else if(bet > game.getUserPlayer().getMoney()) throw new RuntimeException();
					betButton.setEnabled(true);
				} catch (Exception e) {
					betButton.setEnabled(false);
				}
			}
		});

		buttonPanel.add(hitButton);
		buttonPanel.add(standButton);
		
		buttonPanel.add(betButton);
		buttonPanel.add(betTextField);

		dealerCardSpace = new CardSpace(cardImages, "딜러", Color.cyan);
		userCardSpace = new CardSpace(cardImages, "플레이어", Color.orange);

		betLabel = new JLabel();
		betLabel.setBounds(60, 70, 100, 30);		
		buttonPanel.add(betLabel);

		moneyLabel = new JLabel();
		moneyLabel.setBounds(60, 30, 100, 50);
		buttonPanel.add(moneyLabel);
		
		toBetLabel = new JLabel();
		toBetLabel.setBounds(200, 10, 200, 20);
		buttonPanel.add(toBetLabel);
		
		
		betButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//game.init();
				game.userBet(Integer.parseInt(betTextField.getText()));
				
				betLabel.setText("베팅 금액 : " + game.getUserPlayer().getBet());
				moneyLabel.setText("현재 돈 : " + game.getUserPlayer().getMoney());
				
				hitButton.setEnabled(true);
				standButton.setEnabled(true);
				
				betButton.setEnabled(false);
				betTextField.setEnabled(false);
				
				game.afterBet();
				buttonHitStand(); // Unless Initial Hand is Blackjack, Do nothing.
			}
		});

		hitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.userHit();		
				buttonHitStand();
			}
		});
		standButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.userStand();
				buttonHitStand();
			}
		});
	
		cardSpace.add(dealerCardSpace);
		cardSpace.add(userCardSpace);
		
		hitButton.setEnabled(false);
		standButton.setEnabled(false);
	}
	
	private void buttonHitStand() {
		if(game.getPhase() == Game.PHASE_GAME_RESULT) {
			game.init();
			betLabel.setText("베팅 금액 : " + game.getUserPlayer().getBet());
			moneyLabel.setText("현재 돈 : " + game.getUserPlayer().getMoney());
			
			hitButton.setEnabled(false);
			standButton.setEnabled(false);
			
			betButton.setEnabled(true);
			betTextField.setEnabled(true);
			
			if(Integer.parseInt(betTextField.getText()) > game.getUserPlayer().getMoney())
				betTextField.setText(Integer.toString(game.getUserPlayer().getMoney()));
			
			updateGUI();
			
			if(game.getUserPlayer().getMoney() < game.getMinBet()) {
				JOptionPane.showMessageDialog(null, "더 이상 베팅할 돈이 없습니다. 파산했습니다ㅋ");
				System.exit(0);
			}
		}	
	}

	public void receiveSettingVal(int playerNum) {
		setting = new GameSetting(playerNum);
	}

	@Override
	public void sceneOccured() {
		game = new Game(this, setting);

		betLabel.setText("베팅 금액 : " + game.getUserPlayer().getBet() + "$");
		moneyLabel.setText("현재 돈 : " + game.getUserPlayer().getMoney() + "$");
		toBetLabel.setText("베팅할 금액(" + game.getMinBet() + "$ ~ " + game.getMaxBet() + "$)");

		dealerCardSpace.setDisplayCards(game.getDealer().getHand());
		userCardSpace.setDisplayCards(game.getUserPlayer().getHand());

		game.init();
	}
}
