package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by WinNabuska on 12.2.2016.
 */
public abstract class GameController extends MouseAdapter{
    private Set<GameActor> currentActors;
    private GameActor selectedActor;
    public abstract void mouseClicked(MouseEvent e);
    public void setActiveActors(Set<GameActor> actors){
        this.currentActors=actors;
    }
    protected Stream<GameActor> currentActors(){
        return currentActors.stream();
    }
    protected GameActor getSelectedActor(){
        return selectedActor;
    }
    protected void setSelectedActor(GameActor actor){
        selectedActor=actor;
    }
}
