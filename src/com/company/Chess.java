package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by joonaen on 8.2.2016.
 */
public class Chess extends Game {

    private String winner = null;
    private Set<ChessPiece> chessPieces;
    private List<KingChessPiece> kings;

    @Override
    void initializeGame() {
        playersCount=2;
        chessPieces = new HashSet<>();
        Optional<ChessPiece>[][] board =(Optional<ChessPiece>[][]) new Optional[8][8];
        //kings = Arrays.asList(new KingChessPiece())
        //TODO create UI
    }

    @Override
    void makePlay(int player) {
    }

    @Override
    boolean endOfGame() {
        return false;
    }

    @Override
    void printWinner() {
        System.out.println(winner + " wins the game");
    }
}
