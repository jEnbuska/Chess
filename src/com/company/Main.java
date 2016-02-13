package com.company;

/**
 * Created by WinNabuska on 12.2.2016.
 */
public class Main {

    public static void main(String[] args) {
        ChessView view = new ChessView();
        GameController controller = new ChessController(view);
        Game game = new Chess(controller);


    }
}
