package com.company.Components;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by joona on 10/02/2016.
 */
public class Queen extends ChessPiece {
    public Queen(Point initialPosition, ChessTeam team, ChessBoard board) {
        super(initialPosition, team, board);
    }
    @Override
    protected Stream<Point> getMovementRange() {
        List<Point> moves = new ArrayList<>();
        ChessPiece.singleSteps.values().forEach(move -> {
            Point location = position.getLocation();
            move.accept(location);
            while(ChessBoard.validLocation(location)){
                if (board.isEmpty(location)) {
                    moves.add(ChessBoard.coord(location));
                } else if (hasFoe.test(location)) {
                    moves.add(ChessBoard.coord(location));
                    break;
                } else if(hasFriend.test(location)){
                    break;
                }
                move.accept(location);
            }
        });
        return moves.stream();
    }

    @Override
    public String getDescription() {
        return team.getDescription() + "queen";
    }
}
