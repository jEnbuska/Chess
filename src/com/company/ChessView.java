package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joona on 12/02/2016.
 */
public class ChessView extends JFrame {

    public final static int BLOCK_SIZE = 100;
    private ChessBoardView boardPanel;
    private Map<Point, File> locationImages;
    private List<Point> highLightedAreas;

    public ChessView(){
        this.locationImages=new HashMap<>();
        highLightedAreas=new ArrayList<>();
        boardPanel=new ChessBoardView();
        add(boardPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setMouseListener(MouseListener listener){
        boardPanel.addMouseListener(listener);
    }

    public void setLocationImages(Map<Point, File> images){
        this.locationImages=images;
    }

    public void setHighLightedAreas(List<Point> areas){
        highLightedAreas=areas;
    }
    public void update(){
        boardPanel.repaint();
    }

    class ChessBoardView extends JPanel{
        private Graphics graphics;
        private BufferedImage img;
        private final int PANEL_SIZE = 800;

        public ChessBoardView(){
            setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
            img = new BufferedImage(PANEL_SIZE,PANEL_SIZE,BufferedImage.TYPE_INT_RGB);
            graphics = img.getGraphics();
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g); // pakollinen aina!
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0,0, PANEL_SIZE,PANEL_SIZE);
            graphics.setColor(Color.WHITE);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(i%2==0 && j%2==0 || i%2!=0 && j%2!=0){
                        graphics.fillRect(100*i, 100*j, 100, 100);
                    }
                }
            }
            System.out.println("draw");
            graphics.setColor(Color.cyan);
            for(Point p : highLightedAreas){
                graphics.fillRect(p.x*BLOCK_SIZE, p.y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                System.out.println("hl");
            }
            for(Map.Entry<Point, File> lImg : locationImages.entrySet()){
                Point l = lImg.getKey();
                try {
                    graphics.drawImage(
                            ImageIO.read(lImg.getValue()), l.x*BLOCK_SIZE, l.y*BLOCK_SIZE, 100, 100, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            g.drawImage(img, 0,0, this);
        }
    }
}
