package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        JFrame frame;
        GamePanel gp;

        // create frame to display
        frame = new JFrame("Harry Potter Far From Hogwarts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialize main.GamePanel
        gp = new GamePanel();

        // add icon to game
        frame.setIconImage(new ImageIcon("src/img/harry.png").getImage());


        // add the component to the frame
        frame.add(gp);
        frame.pack();
        frame.setResizable(false);
        frame.setSize(gp.PANEL_WIDTH, gp.PANEL_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
