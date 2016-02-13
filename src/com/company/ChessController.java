package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by WinNabuska on 12.2.2016.
 */
public class ChessController extends GameController {

    ChessView view;

    public ChessController(ChessView view){
        this.view=view;
        view.setMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            Files.list(Paths.get(".")).forEach(System.out::println);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Point gridPosition = e.getPoint();
        gridPosition.x = gridPosition.x/ChessView.BLOCK_SIZE;
        gridPosition.y = gridPosition.y/ChessView.BLOCK_SIZE;

        view.setHighLightedAreas(Arrays.asList(gridPosition));
        view.update();



        System.out.println(gridPosition.x + " " + gridPosition.y);
        /*if(getSelectedActor()==null){
            Optional<GameActor> selection = currentActors().filter(a -> a.getPosition().equals(gridPosition)).findAny();
            selection.ifPresent(a -> {
                setSelectedActor(a);
                //TODO highlight selected area
                //TODO highlight the positions the selected can move to
            });

        }else{
            //TODO check if selected actor can move to ...
        }*/
    }
}
