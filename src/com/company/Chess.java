package com.company;

import com.company.Components.ChessBoard;
import com.company.Components.ChessTeam;
import com.company.Components.Direction;

import static java.util.stream.Collectors.toSet;

/**
 * Created by joonaen on 8.2.2016.
 */
public class Chess extends Game {

    private ChessTeam [] teams;
    private GameController controller;
    public Chess(GameController controller){
        this.controller=controller;
    }

    @Override
    void initializeGame() {
        playersCount=2;
        teams = new ChessTeam[playersCount];
        ChessBoard board = new ChessBoard();
        teams[0] = new ChessTeam(board, Direction.NORTH);
        teams[1] = new ChessTeam(board, Direction.SOUTH);
        teams[0].setOpponent(teams[1]);
        teams[1].setOpponent(teams[0]);
        teams[0].setTurn(true);
    }

    @Override
    void makePlay(int player) {
        controller.setActiveActors(teams[player].getMembers().collect(toSet()));
        synchronized (this){
            try {
                wait();
                latestPlayerAction.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        teams[player].setTurn(false);
        teams[player+1%2].setTurn(true);
    }

    @Override
    boolean endOfGame() {
        ChessTeam teamInTurn = teams[0].hasTurn() ? teams[0] : teams[1];
        return teamInTurn.getMembers().noneMatch(m -> m.getOptions().count()>0);
    }


    @Override
    void printWinner() {
        int winner = 1 + (teams[0].hasTurn() ? 1 : 0);
        System.out.println(winner + " wins the game");
    }

    /*
        ChessTeam activeTeam = teams[0].hasTurn() ? teams[0] : teams[1];
        return activeTeam.getMembers().collect(toList());*/
}
