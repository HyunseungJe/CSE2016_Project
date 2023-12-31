package blackjack.gui.scenes;

import java.util.Vector;

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
import blackjack.UserPlayer;
import blackjack.gui.CardImages;
import blackjack.gui.CardSpace;
import blackjack.gui.Scene;
import blackjack.gui.UserCardSpace;

public class GameScene extends Scene implements ActionListener, DocumentListener {
	private GameSetting setting;
	private Game game;
	private static final Color DEEP_GREEN = new Color(0, 124, 0);

	private CardImages cardImages = new CardImages();
	private CardSpace dealerCardSpace;
	private Vector<UserCardSpace> userCardSpaces = new Vector<UserCardSpace>();
	private JPanel cardSpace = new JPanel();
	
	private JLabel betLabel;
	private JLabel moneyLabel;
	private JLabel toBetLabel;
	private JLabel playerTurnLabel;
	
	private JButton hitButton;
	private JButton standButton;
	private JButton betButton;
	private JTextField betTextField;

	private JButton goMainButton;
	
	private JPanel userCardSpacePanel;
	
	public GameScene() {
		
		setLayout(new BorderLayout());
		cardSpace = new JPanel();
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

		goMainButton = new JButton("메인 화면");
		goMainButton.setBounds(930, 20, 150, 60);
		
		betTextField = new JTextField();
		betTextField.setText("100");
		betTextField.setBounds(200, 40, 120, 20);
		betTextField.getDocument().addDocumentListener(this);

		buttonPanel.add(hitButton);
		buttonPanel.add(standButton);
		buttonPanel.add(goMainButton);
		
		buttonPanel.add(betButton);
		buttonPanel.add(betTextField);

		dealerCardSpace = new CardSpace(cardImages, "딜러", DEEP_GREEN);

		betLabel = new JLabel();
		betLabel.setBounds(30, 70, 100, 30);		
		buttonPanel.add(betLabel);

		moneyLabel = new JLabel();
		moneyLabel.setBounds(30, 30, 100, 50);
		buttonPanel.add(moneyLabel);
		
		toBetLabel = new JLabel();
		toBetLabel.setBounds(200, 10, 200, 20);
		buttonPanel.add(toBetLabel);

		playerTurnLabel = new JLabel();
		playerTurnLabel.setBounds(30, 10, 120, 30);
		buttonPanel.add(playerTurnLabel);
		
		
		betButton.addActionListener(this);
		hitButton.addActionListener(this);
		standButton.addActionListener(this);
		goMainButton.addActionListener(this);
	
		cardSpace.add(dealerCardSpace);
		
		hitButton.setEnabled(false);
		standButton.setEnabled(false);
		
	}
	
	private void buttonHitStand() {
		int phase = game.getPhase();
		if(phase == Game.PHASE_HIT_OR_STAND) {
		}
		else if(phase == Game.PHASE_GAME_RESULT) {
			hitButton.setEnabled(false);
			standButton.setEnabled(false);	
			betButton.setEnabled(true);
			betTextField.setEnabled(true);	
			goMainButton.setEnabled(true);
			
			if(game.isAllBankrupt()) {
				JOptionPane.showMessageDialog(null, "모두가 파산했습니다..");
				changeScene("Main");
				return;
			}
			game.init();
			
			for(UserPlayer player : game.getUserPlayers()) {
				getUserCardSpaceByNum(player.getPlayerNum()).setBackgroundByUserState(player.getState());
			}
		}	
		updateGUI();
	}

	private UserCardSpace getUserCardSpaceByNum(int num) {
		return userCardSpaces.get(num);
	}	

	public void receiveGameSetting(GameSetting setting) {
		this.setting = setting;
	}

	@Override
	public void sceneOccured() {
		game = new Game(this, setting);
		game.init();

		toBetLabel.setText("베팅할 금액(" + game.getGameSetting().getMinBet() + "$ ~ " + game.getGameSetting().getMaxBet() + "$)");

		dealerCardSpace.setDisplayCards(game.getDealer().getHand());

		userCardSpacePanel = new JPanel();
		userCardSpacePanel.setLayout(new GridLayout(1, setting.getPlayerNum()));

		for(int i = 0; i < setting.getPlayerNum(); i++) {
			UserCardSpace ucs = new UserCardSpace(cardImages, "플레이어" + (i + 1), Color.GREEN, game.getUserPlayers()[i].getId());
			userCardSpaces.add(ucs);
			userCardSpacePanel.add(ucs);
			ucs.setDisplayCards(game.getUserPlayers()[i].getHand());
		}
		cardSpace.add(userCardSpacePanel);

		updateGUI();
	}

	@Override
	public void sceneDisappeared() {
		userCardSpaces.clear();
		if(userCardSpacePanel == null) throw new RuntimeException("userCardSpace is null");
		cardSpace.remove(userCardSpacePanel);
		
		// 파일 저장
		game.saveUserData();
	}
	
	@Override
	public void updateGUI() {
		if(game != null) {
			UserPlayer player = game.getCurUserPlayer();
			betLabel.setText("베팅 금액 : " + player.getBet() + "$");
			moneyLabel.setText("현재 돈 : " + player.getMoney() + "$");			
			playerTurnLabel.setText("현재 턴 : " + "플레이어" + (player.getPlayerNum() + 1));

			for(UserPlayer pl : game.getUserPlayers()) {
				getUserCardSpaceByNum(pl.getPlayerNum()).updateLabel(pl.getMoney(), pl.getBet(), pl.getState());
			}

			UserPlayer prevPlayer = game.getPrevUserPlayer();
			getUserCardSpaceByNum(prevPlayer.getPlayerNum()).setBackgroundByUserState(prevPlayer.getState());
			if(game.getPhase() != Game.PHASE_GAME_RESULT) getUserCardSpaceByNum(player.getPlayerNum()).setBackground(Color.WHITE);

			betTextField.setText(Integer.toString(game.getCurUserPlayer().getPrevBet()));
			if(Integer.parseInt(betTextField.getText()) > game.getCurUserPlayer().getMoney())
				betTextField.setText(Integer.toString(game.getCurUserPlayer().getMoney()));
		}
		super.updateGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == betButton) {
			goMainButton.setEnabled(false);
			game.userBet(Integer.parseInt(betTextField.getText()));				

			if(game.getPhase() == Game.PHASE_HIT_OR_STAND) {
				hitButton.setEnabled(true);
				standButton.setEnabled(true);
				
				betButton.setEnabled(false);
				betTextField.setEnabled(false);
				game.afterBet();

				for(UserPlayer player : game.getUserPlayers()) {
					getUserCardSpaceByNum(player.getPlayerNum()).setBackgroundByUserState(player.getState());
				}

				buttonHitStand(); // 시작하자마자 모두가 블랙잭인 경우를 고려
			}
			updateGUI();
		}
		else if(source == hitButton) {
			game.userHit();		
			buttonHitStand();
		}
		else if(source == standButton) {
			game.userStand();
			buttonHitStand();
		}
		else if(source == goMainButton) {
			changeScene("Main");
		}
	}

	@Override public void insertUpdate(DocumentEvent e) { docEventUpdate(); }
	@Override public void removeUpdate(DocumentEvent e) { docEventUpdate(); }
	@Override public void changedUpdate(DocumentEvent e) { docEventUpdate(); }
	private void docEventUpdate() {
		int bet;
		String str = betTextField.getText();
		if(game.getPhase() != Game.PHASE_BET) return;
		try {
			bet = Integer.parseInt(str);
			if(bet < game.getGameSetting().getMinBet()) throw new RuntimeException();
			else if(bet > game.getGameSetting().getMaxBet()) throw new RuntimeException();
			else if(bet > game.getCurUserPlayer().getMoney()) throw new RuntimeException();
			betButton.setEnabled(true);
		} catch (Exception e) {
			betButton.setEnabled(false);
		}
	}
}