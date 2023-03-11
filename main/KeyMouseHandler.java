package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyMouseHandler implements KeyListener, MouseListener {
    public boolean spacePressed = false;
    public boolean mouseIsClicked = false;
    public int countClick;
    boolean soundOn = true;
    String startText = "Click to Start";
    GamePanel gp;
    public KeyMouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.playState) {
            if(countClick >0){
                gp.stopMusic(1);
                gp.uiDisplay.playStateText ="";
            }
        }

        // PLAY STATE
        if (code == KeyEvent.VK_SPACE) {
            gp.stopMusic(1);
            spacePressed = gp.gameState == gp.playState;
        }
        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        // SUB TITLE STATE
        else if(gp.gameState == gp.titleSubState){
            // key up and down control
            titleSubState(code);
            // LEADERBOARD STATE
        }else if (gp.gameState == gp.titleLeaderBoardState){
            titleLeaderBoardState(code);
        }


        // PAUSE STATE
        if (code == KeyEvent.VK_ESCAPE) {
            gp.playMusic(1);
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }

        // INSTRUCTION STATE
        if (code == KeyEvent.VK_I) {
            gp.playMusic(1);
            if(gp.gameState==gp.playState){
                gp.gameState = gp.instructionState;
            } else if(gp.gameState==gp.instructionState){
                gp.gameState = gp.playState;
            }
        }

        // OPTIONS STATE
        if(code == KeyEvent.VK_O){
            gp.playMusic(1);
            if(gp.gameState==gp.playState){
                gp.gameState = gp.optionsState;

            }else if(gp.gameState==gp.optionsState){
                gp.gameState = gp.playState;
                gp.uiDisplay.commandNum = 0;
            }
        }
        if(gp.gameState == gp.optionsState){
            optionsState(code);
        }

    }
    public void titleState(int code) {
        if (code == KeyEvent.VK_UP) {
            gp.playMusic(1);
            gp.uiDisplay.commandNum--;
            if (gp.uiDisplay.commandNum < 0)
                gp.uiDisplay.commandNum = 2;
        }
        else if (code == KeyEvent.VK_DOWN) {
            gp.playMusic(1);
            gp.uiDisplay.commandNum++;
            if (gp.uiDisplay.commandNum > 2)
                gp.uiDisplay.commandNum = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            // NEW GAME
            if (gp.uiDisplay.commandNum == 0) {
                gp.restart();
                gp.resetPosition();
                gp.uiDisplay.playStateText = startText;
            }
            // LOAD GAME
            if (gp.uiDisplay.commandNum == 1) {
                if (gp.gameState == gp.titleState) {
                    gp.gameState = gp.titleSubState;
                    gp.uiDisplay.commandNum = 0;
                }
            }
            // QUIT
            if (gp.uiDisplay.commandNum == 2) {
                System.exit(0);
            }
        }
    }

    public void titleSubState(int code){
        if (code == KeyEvent.VK_UP) {
            gp.playMusic(1);
            gp.uiDisplay.commandNum--;
            if (gp.uiDisplay.commandNum < 0)
                gp.uiDisplay.commandNum = 2;
        }
        else if (code == KeyEvent.VK_DOWN) {
            gp.playMusic(1);
            gp.uiDisplay.commandNum++;
            if (gp.uiDisplay.commandNum > 2)
                gp.uiDisplay.commandNum = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.playMusic(1);
            // CONTINUE
            if (gp.uiDisplay.commandNum == 0) {
                gp.keyH.countClick = 0;
                gp.gameState = gp.playState;
                gp.uiDisplay.playStateText = startText;
            }
            // SHOW LEADER BOARD
            if (gp.uiDisplay.commandNum == 1) {
                // LEADER BOARD
                gp.gameState = gp.titleLeaderBoardState;
                gp.uiDisplay.commandNum = 0;
            }
            // BACK
            if (gp.uiDisplay.commandNum == 2) {
                gp.gameState = gp.titleState;
                gp.uiDisplay.commandNum = 0;
            }
        }
    }
    public void titleLeaderBoardState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.playMusic(1);
            gp.gameState = gp.titleSubState;

        }

    }
    public void optionsState(int code){
        if (code == KeyEvent.VK_UP) {
            gp.playMusic(1);
            gp.uiDisplay.commandNum--;
            if (gp.uiDisplay.commandNum < 0)
                gp.uiDisplay.commandNum = 3;
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.playMusic(1);
            gp.uiDisplay.commandNum++;
            if (gp.uiDisplay.commandNum > 3)
                gp.uiDisplay.commandNum = 0;
        }
        // if user hit enters
        if (code == KeyEvent.VK_ENTER) {
            gp.playMusic(1);
            if (gp.uiDisplay.commandNum == 0) {
                gp.gameState = gp.playState;
            }
            // restart
            if (gp.uiDisplay.commandNum == 1) {
                gp.restart();
                gp.uiDisplay.playStateText = startText;
                gp.resetPosition();
                gp.uiDisplay.commandNum = 0;
            }
            // Turn on or off music
            if (gp.uiDisplay.commandNum == 2) {
                if (gp.mu.play[0]) {
                    gp.uiDisplay.music_control = "OFF";
                    soundOn = false;
                    gp.stopMusic(0);
                    gp.stopMusic(3);
                    System.out.println("Stop");
                }
                else{
                    gp.uiDisplay.music_control = "ON";
                    soundOn = true;
                    gp.playMusic(0);
                    System.out.println("Start");
                }
            }
            // go back to main screen
            else if (gp.uiDisplay.commandNum == 3) {
                gp.gameState = gp.titleState;
//                gp.uiDisplay.newGameText = "Continue";
                gp.uiDisplay.commandNum = 0;
            }
        }
    }
    public void gameOverState(){
        if(gp.gameState == gp.gameOverState) {
            gp.resetPosition();
            // SAVE RECORD OF PLAYER
            gp.record.saveRecord(gp.timeInSec);
            gp.gameState = gp.titleState;

        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        gp.stopMusic(1);
        // count click in play state
        if(gp.gameState == gp.playState) {
            countClick++;
            if (code == 32) {
                gp.stopMusic(1);
                spacePressed = false;
            }
        }
        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState) {
            gameOverState();
        }


    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseIsClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(gp.gameState == gp.playState) {
            countClick++;
            mouseIsClicked = false;
            if(countClick >1){
                gp.uiDisplay.playStateText ="";
            }
        }
        if(gp.gameState == gp.titleLeaderBoardState) {
            gp.gameState = gp.titleSubState;
        }

        // GAME OVER STATE
        gameOverState();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
