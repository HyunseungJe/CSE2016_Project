package blackjack;

public class UserData {
	public static final String FILE_PATH = "userdata.csv";
	public static final int TOKENS_PER_LINE = 7;

	private String id;
	private int money;
	
	private int gameCnt;
	private int gameWinCnt;
	private int gameDrawCnt;
	private int gameLoseCnt;
	private int gameBlackjackCnt;
	
	public UserData(String id, int money, int gameCnt, int gameWinCnt, int gameDrawCnt, int gameLoseCnt, int gameBlackjackCnt) {
		this.id = id;
		this.money = money;
		this.gameCnt = gameCnt;
		this.gameWinCnt = gameWinCnt;
		this.gameLoseCnt = gameLoseCnt;
		this.gameDrawCnt = gameDrawCnt;
		this.gameBlackjackCnt = gameBlackjackCnt;
	}

	// Getter & Setter
	public String getId() { return id; }
	public int getMoney() { return money; }
	public void setMoney(int money) { this.money = money; }
	public int getGameCnt() { return gameCnt; }
	public int getGameWinCnt() { return gameWinCnt; }
	public int getGameDrawCnt() { return gameDrawCnt; }
	public int getGameLoseCnt() { return gameLoseCnt; }
	public int getGameBlackjackCnt() { return gameBlackjackCnt; }
	
	public void calcGameResult(int result) {
		if(result == Game.RESULT_WIN) {
			gameWinCnt++;
		}
		else if(result == Game.RESULT_TIE) {
			gameDrawCnt++;
		}
		else if(result == Game.RESULT_LOSE) {
			gameLoseCnt++;
		}
		else if(result == Game.RESULT_BLACKJACK) {
			gameBlackjackCnt++;
		}
		else throw new RuntimeException("Invaild Game Result: " + result);
		gameCnt++;
	}
}
