package com.company.Components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by WinNabuska on 10.2.2016.
 */
public class Rook extends ChessPiece {

    private static List<Consumer<Point>> perpendicularMoves = Arrays.asList(
            singleSteps.get(Direction.NORTH), singleSteps.get(Direction.EAST),
            singleSteps.get(Direction.SOUTH), singleSteps.get(Direction.WEST)
    );

    public Rook(Point initialPosition, ChessTeam team, ChessBoard board) {
        super(initialPosition, team, board);
    }

    @Override
    protected Stream<Point> getMovementRange() {
        List<Point> moves = new ArrayList<>();
        perpendicularMoves.forEach(move -> {
            Point location = position.getLocation();
            move.accept(location);
            while (ChessBoard.validLocation(location)) {
                if (board.isEmpty(location)) {
                    moves.add(ChessBoard.coord(location));
                } else if (hasFoe.test(location)) {
                    moves.add(ChessBoard.coord(location));
                    break;
                } else if (hasFriend.test(location)) {
                    break;
                }
                move.accept(location);
            }
        });
        return moves.stream();
    }

    @Override
    public String getDescription() {
        return team.getDescription() + "rook";
    }
}