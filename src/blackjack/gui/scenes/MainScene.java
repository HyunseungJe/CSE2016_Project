package blackjack.gui.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import blackjack.gui.Scene;

public class MainScene extends Scene implements ActionListener {
	private JButton gameStartButton;
	private JButton rankingButton;
	
	public MainScene() {
		setBounds(0, 0, WIDTH, HEIGHT);
		setLayout(null);
		setBackground(Color.WHITE);
		
		JLabel label = new JLabel("블랙잭 게임!1!!!!");
		label.setBounds(100, 100, 800, 200);
		label.setFont(new Font("Serif", Font.PLAIN, 100));
		add(label);
		
		gameStartButton = new JButton("게임 시작");
		gameStartButton.setBounds(400, 400, 100, 50);
		gameStartButton.addActionListener(this);
		add(gameStartButton);
		
		rankingButton = new JButton("랭킹");
		rankingButton.setBounds(400, 480, 100, 50);
		rankingButton.addActionListener(this);
		add(rankingButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == gameStartButton) {
			changeScene("GameSetting");
		}
		else if(source == rankingButton) {
			changeScene("Ranking");
		}
	}
}
