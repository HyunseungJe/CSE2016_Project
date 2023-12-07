package blackjack.gui.scenes;

import javax.swing.*;

import blackjack.gui.PlayerIdComponent;
import blackjack.gui.Scene;
import blackjack.gui.IGUIupdater;

import java.awt.*;

import java.awt.event.*;

public class GameSettingScene extends Scene implements ActionListener {
    private static final int MAX_PLAYER = 4;
    private static final int MAX_ID_CHARACTER = 15;
    
    private int playerNum = 1;

    private JLabel playerNumLabel;
    
    private JScrollPane scrollPane;
    private JPanel playerIdPanel;
    private JButton addPlayerButton;
    
    private JButton gameStartButton;
    
    private JButton backButton;

    public GameSettingScene() {
        setLayout(null);

        JLabel name = new JLabel("게임 설정");
        name.setBounds(400, 20, 100, 50);
        add(name);
        
        // ===== gameStartButton ======

        gameStartButton = new JButton("게임 시작");
        gameStartButton.setBounds(800, 600, 150, 100);
        add(gameStartButton);
        gameStartButton.addActionListener(this);
        
        // ===== backButton ========
        
        backButton = new JButton("뒤로가기");
        backButton.setBounds(20, 20, 100, 60);
        add(backButton);
        backButton.addActionListener(this);

        // ======= Player Number Panel ==================
        
        JPanel pnPanel = new JPanel();
        pnPanel.setLayout(null);
        //pnPanel.setBorder(new Border());
        pnPanel.setBackground(Color.WHITE);
        pnPanel.setBounds(80, 120, 380, 350);
        add(pnPanel);
        
        JLabel pnLabel = new JLabel("플레이어 수(1 ~ " + MAX_PLAYER + ")");
        pnLabel.setBounds(50, 30, 100, 30);
        pnPanel.add(pnLabel);
        
        addPlayerButton = new JButton("Add Player");
        addPlayerButton.setBounds(200, 30, 100, 30);
        pnPanel.add(addPlayerButton);

        addPlayerButton.addActionListener(this);
        
        // id Anonymous는 랭킹시스템에 등록되지 않음을 명시

        playerIdPanel = new JPanel();
        playerIdPanel.setLayout(new BoxLayout(playerIdPanel, BoxLayout.Y_AXIS));
        playerIdPanel.add(new PlayerIdComponent("Anonymous", this));
        
        scrollPane = new JScrollPane(playerIdPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(50, 80, 250, 200);
        pnPanel.add(scrollPane);

    }
    
    public void deletePlayer(PlayerIdComponent component) {
    	if(playerNum == 1) return;
    	playerIdPanel.remove(component);
    	addPlayerButton.setEnabled(true);
    	playerNum--;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if(source == addPlayerButton) {
    		String id = JOptionPane.showInputDialog("Enter Player's ID : \n(아이디가 Anonymous인 경우에는\n유저 데이터 저장 및 랭킹 등록이 되지 않습니다\n20자 이내로 알파벳, 숫자만 사용)", "Anonymous");
    		if(id == null) return;
    		if(!id.matches("[a-zA-Z0-9]+")) {
    			JOptionPane.showMessageDialog(null, "아이디에는 알파벳 또는 숫자만이 포함될 수 있습니다");
    			return;
    		}
    		else if(id.length() > 20) {
    			JOptionPane.showMessageDialog(null, "아이디는 20자 이하여야 합니다");
    			return;
    		}
    		if(!id.equals("Anonymous")) for(Component c : playerIdPanel.getComponents()) {
            	if(c instanceof PlayerIdComponent) {
            		if(((PlayerIdComponent)c).getId().equals(id)) {
            			JOptionPane.showMessageDialog(null, "아이디 " + id + "가 이미 게임에 참여되었습니다");
            			return;
            		}
            	}
            }
    		
            // 각종 예외 처리 - 알파벳, 숫자 혼용만 가능 / 같은 이름의 플레이어가 둘 이상 존재하면 안됨 / 20자 이내
    		
            if(playerNum >= MAX_PLAYER - 1) addPlayerButton.setEnabled(false);
            playerNum++;
            playerIdPanel.add(new PlayerIdComponent(id, this));
            updateGUI();
    	}
    	else if(source == gameStartButton) {
    		String[] playerIds = new String[playerNum];
    		int i = 0;
    		for(Component c : playerIdPanel.getComponents()) {
            	if(c instanceof PlayerIdComponent) {
            		playerIds[i] = (((PlayerIdComponent)c).getId());
            		i++;
            	}
            }
            ((GameScene)getScene("Game")).receiveSettingVal(playerNum, playerIds);
			changeScene("Game");
    	}
    	else if(source == backButton) {
    		changeScene("Main");
    	}
    }
}
