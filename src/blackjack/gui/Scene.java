package blackjack.gui;

import javax.swing.*;

public abstract class Scene extends JPanel implements IGUIupdater {
	private ISceneChanger sceneChanger;
	
	public void setSceneChanger(ISceneChanger sceneChanger) { this.sceneChanger = sceneChanger; }
	public void sceneDisappeared() {} // Do nothing for default
	public void sceneOccured() {} // Do nothing for default
	
	protected void changeScene(String s) {
		sceneChanger.change(s);
	}
	
	public void updateGUI() {
		revalidate();
		repaint();
	}
}
