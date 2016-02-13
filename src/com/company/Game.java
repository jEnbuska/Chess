package com.company;

/**
 * Created by joonaen on 8.2.2016.
 */
public abstract class Game {

    protected int playersCount;
    protected Runnable latestPlayerAction;

    public void performGameAction(Runnable gameAction){
        latestPlayerAction=gameAction;
    }

    abstract void initializeGame();

    abstract void makePlay(int player);

    abstract boolean endOfGame();

    abstract void printWinner();

    /* A template method : */
    public final void playOneGame(int playersCount) {
        this.playersCount = playersCount;
        initializeGame();
        int j = 0;
        while (!endOfGame()){
            makePlay(j);
            j = (j + 1) % playersCount;
        }
        printWinner();
    }
}
