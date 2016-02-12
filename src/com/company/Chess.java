package com.company;

import com.company.Components.*;

import java.util.*;

/**
 * Created by joonaen on 8.2.2016.
 */
public class Chess extends Game {

    private ChessTeam [] teams;

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
        new ChessView(this);
        //TODO create UI
    }

    @Override
    void makePlay(int player) {
        teams[player].getMembers(); //TODO jotain
        teams[player].setTurn(false);
        teams[player+1%2].setTurn(true);
    }

    @Override
    boolean endOfGame() {
        ChessTeam teamInTurn = teams[0].hasTurn() ? teams[0] : teams[1];
        return teamInTurn.getMembers().noneMatch(m -> m.safeMoves().count()>0);
    }

    @Override
    void printWinner() {
        int winner = 1 + (teams[0].hasTurn() ? 1 : 0);
        System.out.println(winner + " wins the game");
    }
}
