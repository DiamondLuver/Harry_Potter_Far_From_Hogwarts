package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UI {
    public int commandNum = 0;
    GamePanel gp;
    Font hollaween;
    Graphics2D g2;
    BufferedImage img, img2;
    int textGapX = 50, textGapY = 100;
    String music_control = "ON";
    String text, playStateText = "Click to Start", newGameText ="NEW GAME";


    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            hollaween = Font.createFont(Font.TRUETYPE_FONT, new File("src/font/font1.otf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/font/font1.otf")));
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/bgblur.jpg")));
            img2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/harry1.png")));


        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(hollaween);
        g2.setColor(Color.white);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            if(gp.keyH.soundOn){
                gp.mu.play(0);}
            drawTitleScreen();

        }
        // SUBTITLE STATE
        if(gp.gameState == gp.titleSubState){
            drawTitleSubScreenLoadGame();
        }
        // LEADERBOARD STATE
        if(gp.gameState == gp.titleLeaderBoardState){
            drawLeaderBoardScreen();
        }
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            int x;
            if(gp.keyH.soundOn){
                gp.mu.play(0);
                gp.stopMusic(3);
            }

            if(gp.timeInSec%10==0 && gp.timeInSec!=0){
                text = "It's getting fast!!!";
                drawTextBox(0, gp.PANEL_HEIGHT-200,400, 100, text);
            }else{
                text = "";
                drawTextBox(gp.PANEL_WIDTH, 200,0, 100, text);
            }

            x = getXforCenteredText(playStateText);
            int y = gp.PANEL_HEIGHT / 2;
            g2.drawString(playStateText, x, y);
            g2.drawString("Score : " + gp.timeInSec, 30, 50);//display score

        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            if(gp.keyH.soundOn) {
                gp.mu.play(3);
                gp.stopMusic(0);
                gp.mu.loop(3);
            }
            pauseScreen();
        }

        // INSTRUCTION STATE
        if (gp.gameState == gp.instructionState) {
            int frameX = 100;
            int frameY = 50;
            int width = 500;
            int height = 500;
            drawSubFrame(frameX, frameY, width, height);
            drawInstruction(frameX, frameY);
            if(gp.keyH.soundOn) {
                gp.mu.play(1);
                gp.stopMusic(2);
                gp.mu.loop(0);
            }
        }

        // OPTIONS STATE
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }

        // GAME OVER STATE
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }

    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50F));

        // SUB WINDOW
        int width = gp.PANEL_WIDTH / 3;
        int height = gp.PANEL_HEIGHT - 100;
        int x = gp.PANEL_WIDTH / 2 - width / 2;
        int y = gp.PANEL_HEIGHT / 2 - height / 2;

        drawSubFrame(x, y, width, height);
        g2.setColor(Color.white);
        options_top(x,y);
    }
    public void drawTitleSubScreenLoadGame(){
        int width = gp.PANEL_WIDTH / 3;
        int height = gp.PANEL_HEIGHT - 100;
        int x = gp.PANEL_WIDTH / 2 - width / 2;
        int y = gp.PANEL_HEIGHT / 2 - height / 2;
        g2.drawImage(img, 0, 0, gp.PANEL_WIDTH, gp.PANEL_HEIGHT, null);
        drawSubFrame(x, y, width, height);
        g2.setColor(Color.white);
        gameLoadOptions(x, y);
    }
    public void gameLoadOptions(int frameX, int frameY){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        int textX;
        int textY;

        // TITLE
        String text = "Load Game Option";
        textX = getXforCenteredText(text);
        textY = frameY + textGapY;
        g2.setColor(Color.black);
        g2.drawString(text, textX + 5, textY + 5);
        g2.setColor(Color.white);
        g2.drawString(text, textX, textY);

        // Continue
        textX = frameX + textGapX;
        textY += textGapY;
        g2.drawString("Continue", textX, textY);
        if(commandNum == 0){
            g2.drawString(">",textX-30,textY);
        }
        // LEADER BOARD
        textY += textGapY;
        g2.drawString("Show Leader Board", textX, textY);
        if(commandNum == 1){
            g2.drawString(">",textX-30,textY);
        }
        // BACK
        textY += textGapY;
        g2.drawString("Back", textX, textY);
        if(commandNum == 2){
            g2.drawString(">",textX-30,textY);
        }
        // HELP MESSAGE
        defaultHelpText(textY);
    }
    public void drawLeaderBoardScreen(){
        // LOAD THE SCORE TO ARRAYLIST <RECORDS
        int width = gp.PANEL_WIDTH / 3;
        int height = gp.PANEL_HEIGHT - 100;
        int x = gp.PANEL_WIDTH / 2 - width / 2;
        int y = gp.PANEL_HEIGHT / 2 - height / 2;

        g2.drawImage(img, 0, 0, gp.PANEL_WIDTH, gp.PANEL_HEIGHT, null);
        drawSubFrame(x, y, width, height);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        int textX;
        int textY;

        // TITLE
        String text = "LEADER BOARD SCORE";
        textX = getXforCenteredText(text);
        textY = y + textGapY;
        g2.setColor(Color.black);
        g2.drawString(text, textX + 5, textY + 5);
        g2.setColor(Color.white);
        g2.drawString(text, textX, textY);

        textY += textGapY;
        g2.drawString("DATE", textX+20, textY);
        g2.drawString("SCORE", textX + textX/3+10, textY);

        // SCORE PRINTING
        for(int i = 0; i<5; i++) {
            textX = x + textGapX -20;
            textY += textGapY*2/3;
            String data = gp.records.get(i).date;
            g2.drawString(data, textX, textY);
            data = String.valueOf(gp.records.get(i).score);
            g2.drawString(data, textX + textX/2+40, textY);
        }

        // ESCAPE KEY TO GO BACK
        textY += textGapY;
        g2.setColor(Color.red);
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 20F));
        g2.drawString("PRESS ESC to go Back", textX, textY);

    }
    public void drawTitleScreen() {
        gp.stopMusic(2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
        int x, y;

        g2.drawImage(img, 0, 0, gp.PANEL_WIDTH, gp.PANEL_HEIGHT, null);
        g2.drawImage(img2, 100, 400, 200, 200, null);

        String text = "Harry Potter";

        x = getXforCenteredText(text);
        y = gp.PANEL_HEIGHT / 5;
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x - 5, y - 5);

        text = "Far From Hogwarts";
        x = getXforCenteredText(text);
        y += textGapY;
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x - 5, y - 5);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));

        // NEW GAME
        x = getXforCenteredText(newGameText);
        y += 200;
        g2.drawString(newGameText, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 50, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += textGapY;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 50, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += textGapY;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 50, y);
        }

    }
    public void drawSubFrame(int x, int y, int width, int height) {
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke((float) 5.5));
        g2.drawRoundRect(x-10, y-10, width+20, height+20, 10, 10);
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRoundRect(x, y, width, height, 10, 10);

    }
    public void drawInstruction(int frameX, int frameY){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        g2.setColor(Color.white);
        int textX;
        int textY;
        // TITLE
        String text = "Instruction";
        textX = frameX/2 + textGapX*2;
        textY = frameY + textGapY;
        g2.setColor(Color.black);
        g2.drawString(text, textX + 5, textY + 5);
        g2.setColor(Color.white);
        g2.drawString(text, textX, textY);

        // CLICK KEY I TO SEE INSTRUCTION
        textX = frameX + textGapX;
        textY += textGapY;
        g2.drawString("I - Show Instructions", textX, textY);

        // CLICK KEY ESC to PAUSE
        textY += textGapY;
        g2.drawString("Esc - Pause the Game", textX, textY);

        // PRESS SPACEBAR OR MOUSE CLICK TO FLY UP
        textY += textGapY;
        g2.drawString("<Space Bar> or Mouse Click - Fly up", textX, textY);
    }
    public void pauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 100f));
        text = "PAUSED";
        int x;
        int y = gp.PANEL_HEIGHT / 2;
        x = getXforCenteredText(text);
        g2.setColor(new Color(10, 0, 0, 100));
        g2.fillRect(0, 0, gp.PANEL_WIDTH, gp.PANEL_HEIGHT);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }

    public void options_top(int frameX, int frameY){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        int textX;
        int textY;
        int gap = 70;

        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gap;
        g2.setColor(Color.black);
        g2.drawString(text, textX + 5, textY + 5);
        g2.setColor(Color.white);
        g2.drawString(text, textX, textY);

        // Continue
        textX = frameX + gap;
        textY += gap;
        g2.drawString("Continue", textX, textY);
        if(commandNum == 0){
            g2.drawString(">",textX-30,textY);
        }
        // RESTART
        textY += gap;
        g2.drawString("Restart", textX, textY);
        if(commandNum == 1){
            g2.drawString(">",textX-30,textY);
        }
        // MUSIC
        textY += gap;
        g2.drawString("Music", textX, textY);
        g2.drawString(music_control,textX+textX/2, textY);
        if(commandNum == 2){
            g2.drawString(">",textX-30,textY);
        }
        //END GAME
        textY+= gap;

        g2.drawString("Back to Main Screen",textX, textY);
        if(commandNum == 3){
            g2.drawString(">",textX-30,textY);
        }

        // HELP MESSAGE
        defaultHelpText(textY);
    }
    public void drawGameOverScreen(){
        int x, y;

        g2.setColor(new Color(10, 0, 0, 100));
        g2.fillRect(0, 0, gp.PANEL_WIDTH, gp.PANEL_HEIGHT);

        text = "GAME OVER";
        g2.setFont(g2.getFont().deriveFont(110f));
        y = gp.PANEL_HEIGHT / 3;
        x = getXforCenteredText(text);
        // SHADOW
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40f));
        text = "<Press Space key or Click to Go back to Main Screen>";
        x = getXforCenteredText(text);
        g2.drawString(text, x, y+100);

    }
    public void drawTextBox(int x, int y,  int width, int height, String text){
        drawSubFrame(x, y, width, height);
        g2.setColor(Color.white);
        int textX;
        int textY;
        // TITLE
        textX = x+10;
        textY = y + 60;
        g2.setColor(Color.white);
        g2.drawString(text, textX, textY);
    }
    public int getXforCenteredText(String text) {
        int x;
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.PANEL_WIDTH / 2 - length / 2;
        return x;
    }
    public void defaultHelpText(int textY){
        // HELP
        g2.setColor(Color.red);
        text = "Hit enter key to change setting";
        int textX = getXforCenteredText(text);
        textY += textGapY;
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 20F));
        g2.drawString(text, textX, textY);
    }

}
