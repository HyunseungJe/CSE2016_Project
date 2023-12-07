package blackjack.gui;

import java.awt.Dimension;

import javax.swing.*;

import blackjack.gui.scenes.GameSettingScene;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerIdComponent extends JPanel implements ActionListener {
	private GameSettingScene gameSettingScene;
	
    private JLabel id;
    private JButton deleteButton;
    
	public PlayerIdComponent(String idStr, GameSettingScene gameSettingScene) {
		this.gameSettingScene = gameSettingScene;
		
    	this.setPreferredSize(new Dimension(250, 30));
    	//this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    	this.setLayout(null);
    	
    	deleteButton = new JButton("X");
    	deleteButton.setBounds(0, 0, 50, 30);
    	add(deleteButton);
    	
    	deleteButton.addActionListener(this);
    	
    	id = new JLabel(idStr);
    	id.setBounds(60, 0, 200, 30);
    	add(id);
    	
    }
	
	public String getId() {
		return id.getText();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gameSettingScene.deletePlayer(this);
		gameSettingScene.updateGUI();
	}
}
