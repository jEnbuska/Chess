package com.company;

/**
 * Created by WinNabuska on 12.2.2016.
 */
public class Main {

    public static void main(String[] args) {
        ChessView view = new ChessView();
        Game game = new Chess();
        game.initializeGame();
        GameController controller = new ChessController(view, game);
        game.addObserver(controller);
        game.playOneGame(2);
    }
}
