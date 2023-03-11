package main;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GamePanel extends JPanel implements ActionListener, Runnable {
    final int PANEL_WIDTH = 1200;
    final int PANEL_HEIGHT = 800;
    BufferedImage[] backgroundImage = new BufferedImage[2];
    int bgX1 = 0, bgX0 = 0; // background move only on axis X
    int speed = 3;
    KeyMouseHandler keyH = new KeyMouseHandler(this);
    PlayerImage player = new PlayerImage(this, keyH);
    Obstacles pillar = new Obstacles(this, keyH);
    UI uiDisplay = new UI(this);
    Thread gameThread;
    Sound mu = new Sound();
    LeaderBoardConfig record = new LeaderBoardConfig(this);
    ArrayList<Record> records = new ArrayList<>();

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int instructionState =3;
    public final int optionsState =4;
    public final int titleSubState = 5;
    public final int titleLeaderBoardState = 6;
    public final int gameOverState = 7;
    int framePerSec = 60; // FPS
    int count = 0, timeInSec = 0;

    GamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true); // so that the key input would work
        setUpGame();
        startGameThread();
    }
    public void setUpGame(){
        gameState = titleState;
        // LOAD THE RECORDS FROM FILE
        record.loadRecord(records);
        records.sort(new SortByScore());
        try {
            mu.setFile(0);
            mu.setFile(1);
            mu.setFile(2);
            mu.setFile(3);
            mu.loop(0);

        } catch (LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        // IMAGE BACKGROUND
        try {

            backgroundImage[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/bg1.png")));
            backgroundImage[0] = ScaleImg.scaleImage(backgroundImage[0],bgX0, 0,PANEL_WIDTH,PANEL_HEIGHT);
            backgroundImage[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/bg2.png")));
            backgroundImage[1] = ScaleImg.scaleImage(backgroundImage[1],bgX1, 0,PANEL_WIDTH,PANEL_HEIGHT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startGameThread() {

        gameThread = new Thread(this, "start Thread");
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / framePerSec;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                player.update();
                repaint();
                delta--;
            }
        }
    }
    public void update() {
        if (gameState == playState && keyH.countClick>1) {
            // timer as scorer
            count++;
            if(count == 60){
                count = 0;
                timeInSec ++;
                if(timeInSec%10==0){
                    speed +=1;

                }
            }
            pillar.update();

            updateBg();
            // CHECK IF GAME IS OVER
            gameOver();
        }

    }

    public void updateBg(){
        if (bgX0 <= -PANEL_WIDTH) {
            bgX0 = PANEL_WIDTH;
            bgX1 = 0;
        }
        if (bgX1 <= -PANEL_WIDTH) {
            bgX1 = PANEL_WIDTH;
            bgX0 = 0;
        }
        bgX0 -= speed;
        bgX1 -= speed;
    }



    public void gameOver(){
        if (player.y >= PANEL_HEIGHT - player.spriteheight || intersect() || pillar.gameOverWithObstacle() ) {
            try {
                playMusic(2);
                mu.stop(0);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gameState = gameOverState;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    // intersect
    public boolean intersect(){
        return player.harryRec.intersects(pillar.rec1) || player.harryRec.intersects(pillar.rec2);
    }
    // Restart the game
    public void restart() {
        speed = 3;
        gameState = playState;
        timeInSec = 0;
        gameThread = new Thread(this, "start Thread");
    }
    public void resetPosition() {
        bgX0 = 0;
        bgX1 = PANEL_WIDTH;
        keyH.mouseIsClicked = false;
        keyH.countClick =0;
        keyH.spacePressed = false;
        pillar.defaultPosition();
        player.defaultPosition();
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        // TITLE STATE
        if(gameState == titleState) {
            uiDisplay.draw(g2D);
        }
        else {
            // add image to the game
            g2D.drawImage(backgroundImage[1], bgX1, 0, null);
            g2D.drawImage(backgroundImage[0], bgX0, 0, null);


            // add character to the game
            player.draw(g2D);
            pillar.draw(g2D);
            g2D.drawImage(pillar.cloud[0],bgX1, 0,PANEL_WIDTH,PANEL_HEIGHT, null);
            g2D.drawImage(pillar.cloud[1], bgX0, 0,PANEL_WIDTH,PANEL_HEIGHT, null);
            uiDisplay.draw(g2D);
        }
        g2D.dispose();

    }

    public void playMusic(int i){
        try {
            mu.setFile(i);
        } catch (LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        mu.play(i);
    }

    public void stopMusic(int i){
        mu.stop(i);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
