package main;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
class SortByScore implements Comparator<Record> {
    @Override
    public int compare(Record o1, Record o2) {
        return o2.score - o1.score;
    }
}
class Record {
    String date;
    int score;

}
public class LeaderBoardConfig {
    GamePanel gp;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
    LocalDateTime now;
    public LeaderBoardConfig(GamePanel gp){
        this.gp = gp;
    }
    public void saveRecord(int score){
        // if game is over
        now = LocalDateTime.now();
        PrintWriter fp;
        try {
            fp = new PrintWriter(new FileWriter("src/file/ScoreRecord.txt", true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fp.printf("%s\n%d\n", dtf.format(now), score);
        fp.close();
        System.out.println("saveRecord successfully");
    }
    public void loadRecord(ArrayList<Record> records){
        // if view leaderboard
        Scanner scanner;
        try {
            scanner= new Scanner(new File("src/file/ScoreRecord.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Record rec = new Record();
        while (scanner.hasNextLine()) {
            rec.date = scanner.nextLine();
            rec.score = Integer.parseInt(scanner.nextLine());
            records.add(rec);
            rec = new Record();
        }
        scanner.close();
        System.out.println("loadRecord successfully");
    }

}
