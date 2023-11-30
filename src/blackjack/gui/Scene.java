package blackjack.gui;

import javax.swing.*;

public abstract class Scene extends JPanel implements IGUIupdater {
	private ISceneChanger sceneChanger;
	
	public void setSceneChanger(ISceneChanger sceneChanger) { this.sceneChanger = sceneChanger; }
	public void sceneDisappeared() {}
	public void sceneOccured() {}
	
	protected void changeScene(String s) {
		sceneChanger.change(s);
	}
	
	public void updateGUI() {
		revalidate();
		repaint();
	}
}
