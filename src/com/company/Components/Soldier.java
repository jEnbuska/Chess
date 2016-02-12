package com.company.Components;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by WinNabuska on 10.2.2016.
 */
public class Soldier extends ChessPiece {


    public Soldier(Point initialPosition, ChessTeam team, ChessBoard board) {
        super(initialPosition, team, board);
    }
    @Override
    protected Stream<Point> possibleMoves() {
        Point firstStep = position.getLocation();
        singleSteps.get(team.getHeading()).accept(firstStep);
        Set<Point> moves = new HashSet<>();
        if(ChessBoard.validLocation(firstStep) && isEmpty.test(firstStep)){
            moves.add(firstStep);
            if(!hasMoved) {
                Point secondStep = firstStep.getLocation();
                singleSteps.get(team.getHeading()).accept(secondStep);
                if(ChessBoard.validLocation(secondStep) && isEmpty.test(secondStep)){
                    moves.add(secondStep);
                }
            }
        }
        Point sideFront1 = firstStep.getLocation();
        singleSteps.get(Direction.EAST).accept(sideFront1);
        if(ChessBoard.validLocation(sideFront1) && hasFoe.test(sideFront1))
            moves.add(sideFront1);
        Point sideFront2 = firstStep.getLocation();
        singleSteps.get(Direction.WEST).accept(sideFront2);
        if(ChessBoard.validLocation(sideFront2) && hasFoe.test(sideFront2))
            moves.add(sideFront2);
        return moves.stream();
    }
}
