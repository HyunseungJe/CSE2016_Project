package blackjack.gui.scenes;

import blackjack.UserData;
import blackjack.UserDataFileReader;
import blackjack.gui.Scene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.*;

public class RankingScene extends Scene implements ActionListener {
	private static final String[] HEADER = { "ID", "돈" , "전체 게임 수", "승리 수", "무승부 수", "승률", "블랙잭 수" };
	
	private JButton backButton;
	
	private JTable rankingTable;
	private JScrollPane scrollpane;
	
	public RankingScene() {
		setLayout(null);
		
		JLabel name = new JLabel("랭킹");
        name.setBounds(400, 30, 100, 50);
		add(name);
		
		backButton = new JButton("뒤로가기");
        backButton.setBounds(20, 20, 100, 60);
        backButton.addActionListener(this);
        add(backButton);

		scrollpane = new JScrollPane(rankingTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setBounds(100, 100, 800, 600);	
        add(scrollpane);        
	}
	
	@Override
	public void sceneOccured() {
		// 유저 데이터 파일 읽어오기
		String[][] contents = null;
		try {
			UserDataFileReader dataReader = new UserDataFileReader();
			UserData[] data = dataReader.read().values().toArray(new UserData[0]);
			
			contents = new String[data.length][HEADER.length];
			Arrays.sort(data, (a, b) -> b.getMoney() - a.getMoney());
			
			String row[] = new String[HEADER.length];
			for(int i = 0; i < data.length; i++) {
				row[0] = data[i].getId();
				row[1] = Integer.toString(data[i].getMoney());
				row[2] = Integer.toString(data[i].getGameCnt());
				row[3] = Integer.toString(data[i].getGameWinCnt());
				row[4] = Integer.toString(data[i].getGameDrawCnt());
				row[5] = String.format("%.2f",(double)data[i].getGameWinCnt() / data[i].getGameCnt());
				row[6] = Integer.toString(data[i].getGameBlackjackCnt());
				for(int j = 0; j < HEADER.length; j++) {
					contents[i][j] = row[j];
				}
				System.out.println();
			}
			
		} catch (FileNotFoundException e) {
			
		}
		
		rankingTable = new JTable(contents, HEADER);
		scrollpane.setViewportView(rankingTable);	
			
		updateGUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == backButton) {
			changeScene("Main");
		}
	}
}
