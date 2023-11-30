package blackjack.gui;

import java.util.HashMap;

import java.awt.*;

public class SceneManager implements ISceneChanger {
	private HashMap<String, Scene> Scenes = new HashMap<String, Scene>(); // Map 자료구조로 바꿀 예정
	private Container screen;
	private String curScene = null;
	
	public SceneManager(Container c) {
		screen = c;
	}
	
	public void add(String s, Scene scene) {
		scene.setSceneChanger(this);
		Scenes.put(s, scene);
	}
	public void change(String s) {
		if(curScene != null) {
			Scene sceneDisappeared = Scenes.get(curScene);
			screen.remove(sceneDisappeared);
			sceneDisappeared.sceneDisappeared();
		}
		Scene sceneOccured = Scenes.get(s);
		if(sceneOccured == null) throw new RuntimeException("Scene \"" + s + "\" doesn't exist");
		screen.add(sceneOccured);
		sceneOccured.sceneOccured();
		curScene = s;
		sceneOccured.updateGUI();
	}
}
