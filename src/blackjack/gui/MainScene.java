package blackjack.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MainScene extends Scene {
	public MainScene() {
		//setBounds(0, 0, WIDTH, HEIGHT);
		setLayout(null);
		setBackground(Color.WHITE);
		
		JLabel label = new JLabel("블랙잭 게임!1!!!!");
		label.setBounds(100, 100, 800, 200);
		label.setFont(new Font("Serif", Font.PLAIN, 100));
		
		JButton button = new JButton("게임 시작");
		button.setBounds(400, 400, 100, 50);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//changeScene("Game");
				changeScene("GameSetting");
			}
		});
		
		add(label);
		add(button);
	}
}
