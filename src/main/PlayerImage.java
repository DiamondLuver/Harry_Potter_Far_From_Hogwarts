package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerImage extends PositionVariable{
    GamePanel gp;

    KeyMouseHandler keyH;
    int spriteWidth =150, spriteheight=150;
    Rectangle harryRec = new Rectangle(x,y, spriteWidth,spriteheight);
    public PlayerImage(GamePanel gp, KeyMouseHandler keyH){

        this.gp = gp;
        this.keyH = keyH;
        defaultPosition();
        getPlayerImage();
    }

    public void defaultPosition(){
        x = gp.PANEL_WIDTH/4;
        y = 100;

    }
    public void getPlayerImage(){
        try {
            img1 = ImageIO.read(getClass().getResourceAsStream("/img/harry1.png"));
            img2 = ImageIO.read(getClass().getResourceAsStream("/img/harry2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        if(gp.gameState == gp.playState) {
            if (keyH.spacePressed || keyH.mouseIsClicked) { // when the player pressed space bar
                if (y > 0) { // and character is not go out of the top of frame bound
                    y -= gp.speed; // when press it goes up
                }else if(y + spriteheight > gp.PANEL_HEIGHT){
                    y = gp.PANEL_HEIGHT- spriteheight;
                }
                harryRec = new Rectangle(x,y, spriteWidth,spriteheight);
            } else {
                y = y + gp.speed;
                harryRec = new Rectangle(x,y, spriteWidth,spriteheight);
            }
        }

    }

    public void draw(Graphics2D g2D){
        BufferedImage spriteImg = null;
        if(gp.count %2 ==0){
            spriteImg = img1;
        }else {
            spriteImg = img2;
        }
        g2D.drawImage(spriteImg, x, y, spriteWidth, spriteheight, null);
    }


}
