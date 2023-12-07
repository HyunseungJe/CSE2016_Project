package blackjack;

import javax.swing.*;

import blackjack.gui.*;
import blackjack.gui.scenes.*;

public class GameWindow extends JFrame {
	private final int WIDTH = 1200;
	private final int HEIGHT = 800;
	
	public GameWindow() {
		setTitle("Blackjack Project");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		SceneManager sceneManager = new SceneManager(this);
		sceneManager.add("Main", new MainScene());
		sceneManager.add("GameSetting", new GameSettingScene());
		sceneManager.add("Game", new GameScene());
		sceneManager.add("Ranking", new RankingScene());
		sceneManager.change("Main");
		
	}
}