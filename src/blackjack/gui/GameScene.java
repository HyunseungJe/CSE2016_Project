package blackjack.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import blackjack.Game;

public class GameScene extends Scene {
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

		JButton hitButton = new JButton("Hit");
		hitButton.setBounds(500, 50, 80, 50);

		JButton standButton = new JButton("Stand");
		standButton.setBounds(650, 50, 80, 50);
		
		JButton betButton = new JButton("Bet");
		betButton.setBounds(150, 75, 80, 20);
		
		JTextField betTextField = new JTextField();
		betTextField.setText("100");
		betTextField.setBounds(150, 50, 80, 20);
		betTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void insertUpdate(DocumentEvent e) { update(); }
			@Override public void removeUpdate(DocumentEvent e) { update(); }
			@Override public void changedUpdate(DocumentEvent e) { update(); }
			public void update() {
				int bet;
				String str = betTextField.getText();
				try {
					bet = Integer.parseInt(str);
					if(bet <= 0) throw new RuntimeException("Betting Money cannot be less or equal than 0");
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

		// 게임
		Game game = new Game(this);

		CardImages cardImages = new CardImages();
		CardSpace dealerCardSpace = new CardSpace(game.getDealer().getHand(), cardImages, "딜러", Color.cyan);
		CardSpace userCardSpace = new CardSpace(game.getUserPlayer().getHand(), cardImages, "플레이어", Color.orange);

		JLabel betLabel = new JLabel("베팅 금액 : " + game.getUserPlayer().getBet());
		betLabel.setBounds(50, 30, 100, 50);		
		buttonPanel.add(betLabel);

		JLabel moneyLabel = new JLabel("현재 돈 : " + game.getUserPlayer().getMoney());
		moneyLabel.setBounds(50, 70, 100, 50);
		buttonPanel.add(moneyLabel);

		hitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.userHit();		
				
				betLabel.setText("베팅 금액 : " + game.getUserPlayer().getBet());
				moneyLabel.setText("현재 돈 : " + game.getUserPlayer().getMoney());
				updateGUI();
			}
		});
		standButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.userStand();

				betLabel.setText("베팅 금액 : " + game.getUserPlayer().getBet());
				moneyLabel.setText("현재 돈 : " + game.getUserPlayer().getMoney());
				updateGUI();
			}
		});
	
		cardSpace.add(dealerCardSpace);
		cardSpace.add(userCardSpace);
	}

}
