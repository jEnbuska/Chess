package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observer;
import java.util.Set;

/**
 * Created by WinNabuska on 12.2.2016.
 */
public abstract class GameController extends MouseAdapter implements Observer{
    private GameActor selectedActor;
    private Set<GameActor> actors;
    private GameView2D view;
    private Game game;

    public GameController(GameView2D view, Game game){
        this.actors = game.getActors();
        this.view = view;
        this.game = game;
    }
    protected Set<GameActor> getActors(){
        return actors;
    }
    protected GameView2D getView(){
        return view;
    }
    protected GameActor getSelectedActor(){
        return selectedActor;
    }
    protected void setSelectedActor(GameActor actor){
        selectedActor=actor;
    }
    protected Game getGame(){
        if(game==null)
            throw new Error("game null");
        return game;
    }
    public abstract void mouseClicked(MouseEvent e);
    protected abstract void onGameClose();

}
