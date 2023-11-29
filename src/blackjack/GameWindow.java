package blackjack;

import javax.swing.*;

import blackjack.gui.CardImages;
import blackjack.gui.CardSpace;
import blackjack.gui.IGUIupdater;

import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements IGUIupdater {
	private final int WIDTH = 1000;
	private final int HEIGHT = 800;

	private JPanel screen;
	
	public GameWindow() {
		setTitle("Blackjack Project");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		// ----- 메인화면 -----
		
		screen = new JPanel();
		screen.setBounds(0, 0, WIDTH, HEIGHT);
		screen.setLayout(null);
		screen.setBackground(Color.WHITE);
		
		JLabel label = new JLabel("블랙잭 게임!1!!!!");
		label.setBounds(100, 100, 800, 200);
		label.setFont(new Font("Serif", Font.PLAIN, 100));
		
		JButton button = new JButton("게임 시작");
		button.setBounds(400, 400, 100, 50);
		
		this.add(screen);
		screen.add(label);
		screen.add(button);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				label.setVisible(false);
				button.setVisible(false);
				*/
				screen.remove(label);
				screen.remove(button);

				// 게임 시작
				gameStart();
			}
		});	
		updateGUI();
	}
	
	// 게임시작
	public void gameStart() {
		// GUI
		screen.setLayout(new BorderLayout());
		JPanel cardSpace = new JPanel();
		cardSpace.setBackground(Color.GRAY);
		cardSpace.setLayout(new GridLayout(2, 1));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setPreferredSize(new Dimension(0, 200));
		buttonPanel.setLayout(null);

		screen.add(cardSpace, BorderLayout.CENTER);
		screen.add(buttonPanel, BorderLayout.SOUTH);

		JButton hitButton = new JButton("Hit");
		hitButton.setBounds(200, 100, 100, 50);

		JButton standButton = new JButton("Stand");
		standButton.setBounds(350, 100, 100, 50);

		buttonPanel.add(hitButton);
		buttonPanel.add(standButton);


		// 게임
		Game game = new Game(this);

		CardImages cardImages = new CardImages();
		CardSpace dealerCardSpace = new CardSpace(game.getDealer().getHand(), cardImages, "딜러", Color.cyan);
		CardSpace userCardSpace = new CardSpace(game.getUserPlayer().getHand(), cardImages, "플레이어", Color.orange);

		JLabel betLabel = new JLabel("베팅 금액 : " + game.getUserPlayer().getBet());
		betLabel.setBounds(550, 70, 100, 50);		
		buttonPanel.add(betLabel);

		JLabel moneyLabel = new JLabel("현재 돈 : " + game.getUserPlayer().getMoney());
		moneyLabel.setBounds(550, 120, 100, 50);
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
	
		updateGUI();
	}
	
	public void updateGUI() {
		revalidate();
		repaint();
	}
}
