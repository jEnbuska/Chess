package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by WinNabuska on 12.2.2016.
 */
public class ChessController extends GameController {

    private Map<String, File> chessPieceFiles;
    private Map<Point, File> locationImages;

    public ChessController(ChessView view, Game game){
        super(view, game);
        chessPieceFiles = getActors().stream().map(actor -> actor.getDescription()).distinct().collect(toMap(Function.identity(), description -> {
            File actorImage;
            actorImage= new File(".\\"+description + ".png");
            if(actorImage==null)
                throw new Error("image null");
            return actorImage;
        }));
        locationImages = new HashMap<>();
        view.setChessPieceImages(locationImages);
        view.setController(this);
        updateChessPieceLocationImages();
        view.update();
        /*try {
            Files.list(Paths.get(".")).forEach(System.out::println);
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point gridPosition = e.getPoint();
        gridPosition.x = gridPosition.x / ChessView.BLOCK_SIZE;
        gridPosition.y = gridPosition.y / ChessView.BLOCK_SIZE;
        for(GameActor a : getActors()){
            System.out.println(a.getDescription() + " t " + a.hasTurn());
        }
        if(getSelectedActor()==null) {
            Optional<GameActor> selectedActor = getActors().stream().filter(actor -> actor.hasTurn() && actor.getPosition().equals(gridPosition)).findFirst();
            selectedActor.ifPresent(actor -> {
                setSelectedActor(actor);
                showActorOptions(actor);
            });
        }else {
            Optional<Point> selectedMove = getSelectedActor().getOptions().filter(move -> move.getLocation().equals(gridPosition)).findFirst();
            if(selectedMove.isPresent()){
                getGame().performGameAction(() -> getSelectedActor().moveTo(selectedMove.get()));
            }else{
                Optional<GameActor> actor = findActorByPosition(gridPosition);
                if(actor.isPresent()){
                    setSelectedActor(actor.get());
                    showActorOptions(actor.get());
                }else{
                    setSelectedActor(null);
                    hideActorOptions();
                }
            }
        }
        getView().update();
    }

    private Optional<GameActor> findActorByPosition(Point gridPosition){
        return getActors().stream()
                .filter(actor -> actor.getPosition().equals(gridPosition) && actor.hasTurn())
                .findFirst();
    }

    private void showActorOptions(GameActor actor){
        getView().showPlayerOptions(actor.getOptions().collect(toList()));
    }
    private void hideActorOptions(){
        getView().showPlayerOptions(Arrays.asList());
    }

    private void updateChessPieceLocationImages(){
        locationImages.clear();
        for(GameActor actor : getActors()){
            locationImages.put(actor.getPosition(), chessPieceFiles.get(actor.getDescription()));
        }
        getView().setChessPieceImages(locationImages);
    }

    @Override
    protected void onGameClose() {
        System.out.println("Game ends");
    }

    @Override
    public void update(Observable o, Object arg) {
        updateChessPieceLocationImages();
        hideActorOptions();
        setSelectedActor(null);
        SwingUtilities.invokeLater(() -> {
            getView().update();
        });
    }
}
