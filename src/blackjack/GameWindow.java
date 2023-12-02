package blackjack;

import javax.swing.*;

import blackjack.gui.*;

public class GameWindow extends JFrame {
	private final int WIDTH = 1000;
	private final int HEIGHT = 800;
	
	public GameWindow() {
		setTitle("Blackjack Project");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		SceneManager sceneManager = new SceneManager(this);
		sceneManager.add("Main", new MainScene());
		sceneManager.add("Game", new GameScene());
		sceneManager.change("Main");
		
	}
}