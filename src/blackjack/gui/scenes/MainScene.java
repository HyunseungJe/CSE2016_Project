package blackjack.gui.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import blackjack.App;
import blackjack.gui.Scene;

public class MainScene extends Scene implements ActionListener {
	private JButton gameStartButton;
	private JButton rankingButton;
	private JLabel backgroundLabel;

	public MainScene() {
		setBounds(0, 0, WIDTH, HEIGHT);
		setLayout(null);
		setBackground(Color.WHITE);

		JLabel label = new JLabel("블랙잭!!!!1!!", SwingConstants.CENTER);
		label.setSize(1000, 200);
		label.setLocation((1200 - label.getWidth()) / 2, 200);
		label.setFont(new Font("Serif", Font.PLAIN, 100));
		add(label);

		gameStartButton = new JButton("게임 시작");
		gameStartButton.setSize(200, 100);
		gameStartButton.setLocation(370, 400);
		gameStartButton.addActionListener(this);
		add(gameStartButton);

		rankingButton = new JButton("랭킹");
		rankingButton.setSize(200, 100);
		rankingButton.setLocation(630, 400);
		rankingButton.addActionListener(this);
		add(rankingButton);
		
		JPanel pnPanel = new JPanel();
		pnPanel.setLayout(null);
		pnPanel.setBackground(Color.WHITE);
		pnPanel.setBounds(0, 200, 1200, 350);
		add(pnPanel);

		// ===== backgroundLabel ======
		ImageIcon backgroundImage = new ImageIcon(App.class.getResource("/images/gameMain.png"));
		backgroundLabel = new JLabel(backgroundImage);
		backgroundLabel.setBounds(0, 0, 1200, 800);
		backgroundLabel.setIcon(backgroundImage);
		add(backgroundLabel);
		backgroundLabel.setVisible(true);
		
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == gameStartButton) {
			changeScene("GameSetting");
		} else if (source == rankingButton) {
			changeScene("Ranking");
		}
	}
}
