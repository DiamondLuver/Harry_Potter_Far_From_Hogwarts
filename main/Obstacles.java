package main;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Obstacles extends PositionVariable{
    GamePanel gp;
    KeyMouseHandler keyH;
    Random rand = new Random();
    BufferedImage[] cloud = new BufferedImage[2];

    int pillarWidth = 300, pillarHeight = 698*2;
    int pillarGap = 330;
    int y2 = pillarHeight+ y + pillarGap;

    Rectangle rec1 = new Rectangle(x,y, pillarWidth,pillarHeight);
    Rectangle rec2 = new Rectangle(x,y2, pillarWidth,pillarHeight);
    public Obstacles(GamePanel gp, KeyMouseHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImage();
        defaultPosition();
    }
    public void defaultPosition(){
        x = gp.PANEL_WIDTH;  // it is at the end of frame
        y = -pillarHeight + rand.nextInt(0, gp.PANEL_HEIGHT/2);
        y2 = pillarHeight+ y + pillarGap;
    }
    public void getPlayerImage(){
        try {
            img1 = ImageIO.read(getClass().getResourceAsStream("/img/pillar1.png"));
            img2 = ImageIO.read(getClass().getResourceAsStream("/img/pillar2.png"));
            cloud[0] = ImageIO.read(getClass().getResourceAsStream("/img/bg1cloud1.png"));
            cloud[0] = ScaleImg.scaleImage(cloud[0],x,y,gp.PANEL_WIDTH, gp.PANEL_HEIGHT);
            cloud[1] = ImageIO.read(getClass().getResourceAsStream("/img/bg1cloud2.png"));
            cloud[1] = ScaleImg.scaleImage(cloud[1],x,y,gp.PANEL_WIDTH, gp.PANEL_HEIGHT);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        // update both pillars
        if (x <= -pillarWidth) {
            x = gp.PANEL_WIDTH;  // move image 1 to the beginning of panel EX: bgX0 = - 600 (it will sta
            y = -pillarHeight + rand.nextInt(0, gp.PANEL_HEIGHT/2);
            y2 = pillarHeight+ y + pillarGap;
        }
        x -=gp.speed;
        rec1 = new Rectangle(x,y, pillarWidth,pillarHeight);
        rec2 = new Rectangle(x,y2, pillarWidth,pillarHeight);

    }
    public boolean gameOverWithObstacle() {
        if((gp.player.x >= x && gp.player.x < x+ pillarWidth)) {//touch the top pillar
            if ((gp.player.y > 0 && gp.player.y < (pillarHeight + y)))
                return true;
            else if(((gp.player.y + gp.player.spriteheight > y2) ))
                return true;
        }
      return false;
    }

    public void draw(Graphics2D g2D){
        g2D.drawImage(img1, x, y,pillarWidth, pillarHeight, null);
        g2D.drawImage(img2, x, y2, pillarWidth, pillarHeight, null);

    }

}

