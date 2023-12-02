package blackjack.gui;

import java.util.HashMap;

import java.awt.*;

public class SceneManager implements ISceneManager {
	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();
	private Container screen;
	private String curScene = null;
	
	public SceneManager(Container c) {
		screen = c;
	}
	
	public void add(String s, Scene scene) {
		scene.setSceneChanger(this);
		scenes.put(s, scene);
	}
	public void change(String s) {
		if(curScene != null) {
			Scene sceneDisappeared = scenes.get(curScene);
			screen.remove(sceneDisappeared);
			sceneDisappeared.sceneDisappeared();
		}
		Scene sceneOccured = scenes.get(s);
		if(sceneOccured == null) throw new RuntimeException("Scene \"" + s + "\" doesn't exist");
		screen.add(sceneOccured);
		sceneOccured.sceneOccured();
		curScene = s;
		sceneOccured.updateGUI();
	}

	public Scene getScene(String s) {
		Scene scene = scenes.get(s);
		if(scene == null) throw new RuntimeException("Scene \"" + s + "\" doesn't exist");
		return scene;
	}
}
