package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by joona on 12/02/2016.
 */
public class ChessView extends JFrame {

    private Game game;
    private ChessBoardView boardPanel;

    public ChessView(Game game){
        boardPanel=new ChessBoardView(game);
        System.out.println("view created");
        add(boardPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    class ChessBoardView extends JPanel{

        private Graphics graphics;
        private BufferedImage img;

        public ChessBoardView(Game game){
            setPreferredSize(new Dimension(800,800));
            img = new BufferedImage(400,300,BufferedImage.TYPE_INT_RGB);
            graphics = img.getGraphics();
            graphics.setColor(Color.GRAY);
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g); // pakollinen aina!
            graphics.setColor(Color.BLACK);
            graphics.fill3DRect(100, 100, 100, 100, false);
            g.drawImage(img, 0,0, this);
        }
    }
}
