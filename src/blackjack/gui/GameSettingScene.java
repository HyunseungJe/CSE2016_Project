package blackjack.gui;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;

public class GameSettingScene extends Scene {
    JLabel playerNumLabel;
    public GameSettingScene() {
        setLayout(null);

        JLabel name = new JLabel("게임 설정");
        name.setBounds(400, 20, 100, 50);

        JPanel pnPanel = new JPanel();
        pnPanel.setLayout(null);
        //pnPanel.setBorder(new Border());
        pnPanel.setBackground(Color.white);
        pnPanel.setBounds(80, 100, 200, 80);
        JLabel pnLabel = new JLabel("플레이어 수(1 ~ 8)");
        pnLabel.setBounds(50, 10, 100, 30);
        
        playerNumLabel = new JLabel();
        playerNumLabel.setBounds(90, 50, 30, 20);
        JButton pnPlusButton = new JButton("+");
        pnPlusButton.setBounds(120, 50, 50, 20);
        JButton pnMinusButton = new JButton("-");
        pnMinusButton.setBounds(20, 50, 50, 20);
        pnPanel.add(pnLabel);
        pnPanel.add(playerNumLabel);
        pnPanel.add(pnPlusButton);
        pnPanel.add(pnMinusButton);

        // Init pnPanel
        playerNumLabel.setText("1");
        pnMinusButton.setEnabled(false);

        pnPlusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(playerNumLabel.getText());
                if(n == 1) {
                    pnMinusButton.setEnabled(true);
                }
                else if(n + 1 >= 8) pnPlusButton.setEnabled(false);
                playerNumLabel.setText(Integer.toString(n + 1));
            }
        });

        pnMinusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.parseInt(playerNumLabel.getText());
                if(n == 8) {
                    pnPlusButton.setEnabled(true);
                }
                else if(n - 1 <= 1) pnMinusButton.setEnabled(false);
                playerNumLabel.setText(Integer.toString(n - 1));
            }
        });


        JButton gameStartButton = new JButton("게임 시작");
        gameStartButton.setBounds(800, 600, 150, 100);

		gameStartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                int playerNum = Integer.parseInt(playerNumLabel.getText());
                ((GameScene)getScene("Game")).receiveSettingVal(playerNum);
				changeScene("Game");
			}
		});

        add(name);
        add(gameStartButton);
        add(pnPanel);
    }
}
