package blackjack;

import java.io.*;

public class UserDataFileWriter {
    private BufferedWriter outfile;

    public UserDataFileWriter() {
        try {
            outfile = new BufferedWriter(new FileWriter(UserData.FILE_PATH));
        } catch (IOException e) {
            System.out.println("UserDataFileWriter Error: " + e.getMessage());
        }
    }

    public void writeUserData(UserData data) {
        String line = "";
        String arr[] = { 
            data.getId(), 
            Integer.toString(data.getMoney()), 
            Integer.toString(data.getGameCnt()), 
            Integer.toString(data.getGameWinCnt()), 
            Integer.toString(data.getGameDrawCnt()), 
            Integer.toString(data.getGameLoseCnt()), 
            Integer.toString(data.getGameBlackjackCnt()) 
        };
        for(int i = 0; i < UserData.TOKENS_PER_LINE; i++) {
            line += arr[i] + ", ";
        }
        line += "\n";
        try {
            outfile.write(line);
        } catch(IOException e) {
            System.out.println("UserDataFileWriter Error: " + e.getMessage());
        }
    }

    public void saveCSV() {
        try {
            if(outfile == null) throw new RuntimeException("outfile is null");
            outfile.close();
        } catch(IOException e) {
            System.out.println("UserDataFileWriter Error: " + e.getMessage());
        } catch(RuntimeException e) {
            System.out.println("UserDataFileWriter Error: " + e.getMessage());
        }
    }
}
