package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScaleImg {
    public static BufferedImage scaleImage(BufferedImage original,int x, int y, int width, int height){
        BufferedImage scaleImage = new BufferedImage(width,height,original.getType());
        Graphics2D g2 = scaleImage.createGraphics();
        g2.drawImage(original, x,y,width,height,null);
        g2.dispose();
        return scaleImage;
    }
}
