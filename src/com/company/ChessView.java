package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
public class ChessView extends JFrame implements GameView2D {

    public final static int BLOCK_SIZE = 100;
    private ChessBoardView boardPanel;
    private Map<Point, File> chessPieceImages;
    private List<Point> highLightedAreas;

    public ChessView(){

        this.chessPieceImages =new HashMap<>();
        highLightedAreas=new ArrayList<>();
        boardPanel=new ChessBoardView();
        add(boardPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void setController(GameController controller){
        boardPanel.addMouseListener(controller);
    }

    public void setChessPieceImages(Map<Point, File> images){
        this.chessPieceImages =images;
    }


    @Override
    public void showPlayerOptions(List<Point> areas){
        highLightedAreas=areas;
    }

    @Override
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
            System.out.println("repainting");
            graphics.setColor(Color.cyan);
            for(Point p : highLightedAreas){
                graphics.fillRect(p.x*BLOCK_SIZE, p.y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
            for(Map.Entry<Point, File> locationImage : chessPieceImages.entrySet()){
                Point l = locationImage.getKey();
                if(l.y==3 ||l.y == 5)
                    System.out.println("x = " + l.x + " y = " + l.y);
                try {
                    graphics.drawImage(
                            ImageIO.read(locationImage.getValue()), l.x*BLOCK_SIZE+15, l.y*BLOCK_SIZE+15, 70, 70, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            g.drawImage(img, 0,0, this);
        }
    }
}
