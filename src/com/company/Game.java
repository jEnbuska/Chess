package com.company;

import java.util.Observable;

import java.util.Set;

/**
 * Created by joonaen on 8.2.2016.
 */
public abstract class Game extends Observable {

    protected int playersCount;
    protected Runnable latestPlayerAction = ()-> System.out.println("no action");

    public synchronized void performGameAction(Runnable gameAction){
        latestPlayerAction=gameAction;
        notify();
    }

    public abstract Set<GameActor> getActors();

    abstract void initializeGame();

    abstract void makePlay(int player);

    abstract boolean endOfGame();

    abstract void printWinner();

    /* A template method : */
    public final void playOneGame(int playersCount) {
        this.playersCount = playersCount;
        int j = 0;
        do{
            makePlay(j);
            j = (j + 1) % playersCount;
            setChanged();
            notifyObservers();
        }while (!endOfGame());
        printWinner();
    }
}
