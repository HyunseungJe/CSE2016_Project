package blackjack;

import java.io.*;
import java.util.*;

public class UserDataFileReader {
	private BufferedReader infile;

	public UserDataFileReader() throws FileNotFoundException {
		File f = new File(UserData.FILE_PATH);
		if(f.exists()) {
			infile = new BufferedReader(new FileReader(f));
		}
	}

	public UserData getNextRecordOrNull() {
		UserData result = null;
		if(infile == null) return null;
		try {
			if(!infile.ready()) return null;
			String line = infile.readLine();

			StringTokenizer t = new StringTokenizer(line, ", ");
			if(t.countTokens() != UserData.TOKENS_PER_LINE) { throw new RuntimeException("Wrong Token Count"); }

			String id = t.nextToken();
			int money = Integer.parseInt(t.nextToken());
			int gameCnt = Integer.parseInt(t.nextToken());
			int gameWinCnt = Integer.parseInt(t.nextToken());
			int gameDrawCnt = Integer.parseInt(t.nextToken());
			int gameLoseCnt = Integer.parseInt(t.nextToken());
			int gameBlackjackCnt = Integer.parseInt(t.nextToken());

			result = new UserData(id, money, gameCnt, gameWinCnt, gameDrawCnt, gameLoseCnt, gameBlackjackCnt);

		} catch (IOException e) {
			System.out.println("UserDataFileReader Error: " + e.getMessage());
		} catch (RuntimeException e) {
			System.out.println("UserDataFileReader Error: " + e.getMessage());
		}
		return result;
	}
	
	public HashMap<String, UserData> read() {
		HashMap<String, UserData> map = new HashMap<String, UserData>();
		UserData data = getNextRecordOrNull();
		while(data != null) {
			map.put(data.getId(), data);
			data = getNextRecordOrNull();
		}
		return map;
	}
}
