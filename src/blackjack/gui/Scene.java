package blackjack.gui;

import javax.swing.*;

public abstract class Scene extends JPanel implements IGUIupdater {
	private ISceneManager sceneManager;
	
	public void setSceneChanger(ISceneManager sceneChanger) { this.sceneManager = sceneChanger; }
	public void sceneDisappeared() {} // Do nothing for default
	public void sceneOccured() {} // Do nothing for default
	
	protected void changeScene(String s) {
		sceneManager.change(s);
	}

	protected Scene getScene(String s) {
		return sceneManager.getScene(s);
	}
	
	public void updateGUI() {
		revalidate();
		repaint();
	}
}
