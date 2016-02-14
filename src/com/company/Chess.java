package com.company;

import com.company.Components.ChessBoard;
import com.company.Components.ChessTeam;
import com.company.Components.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by joonaen on 8.2.2016.
 */
public class Chess extends Game {

    private List<ChessTeam> teams;
    private Set<GameActor> actors;
    @Override
    public Set<GameActor> getActors() {
        return actors;
    }

    @Override
    void initializeGame() {
        playersCount=2;
        teams = new ArrayList<>();
        ChessBoard board = new ChessBoard();
        teams.add(new ChessTeam(board, Direction.NORTH));
        teams.add(new ChessTeam(board, Direction.SOUTH));
        actors=new HashSet<>();
        actors.addAll(teams.get(0).getMembers());
        actors.addAll(teams.get(1).getMembers());
        teams.get(0).setOpponent(teams.get(1));
        teams.get(1).setOpponent(teams.get(0));
        teams.get(0).setTurn(true);
        teams.get(1).setTurn(false);
    }

    @Override
    void makePlay(int player) {
        //System.out.println("black " +teams.get(0).getBishops().get(0).hasTurn() + " white " + teams.get(1).getBishops().get(0).hasTurn());
        teams.get(player).setTurn(true);
        teams.get(player).getOpponent().setTurn(false);
        synchronized (this){
            try {
                wait();
                latestPlayerAction.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    boolean endOfGame() {
        ChessTeam teamInTurn = teams.get(0).hasTurn() ? teams.get(0): teams.get(1);
        //teams.get(0).getMembers().forEach(m -> m.getOptions().forEach(System.out::println));
        //teams.get(1).getMembers().forEach(m -> m.getOptions().forEach(System.out::println));
        return teamInTurn.getMembers().stream().noneMatch(m -> m.getOptions().count()>0);
    }


    @Override
    void printWinner() {
        int winner = 1 + (teams.get(0).hasTurn() ? 1 : 0);
        System.out.println(winner + " wins the game");
    }

}
