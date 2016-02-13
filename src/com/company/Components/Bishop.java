package com.company.Components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by WinNabuska on 11.2.2016.
 */
public class Bishop extends ChessPiece {

    private static List<Consumer<Point>> transverseSteps = Arrays.asList(
            singleSteps.get(Direction.NORTH_EAST), singleSteps.get(Direction.SOUT_EAST),
            singleSteps.get(Direction.SOUT_WEST), singleSteps.get(Direction.NORTH_WEST)
    );

    public Bishop(Point initialPosition, ChessTeam team, ChessBoard board) {
        super(initialPosition, team, board);
    }

    @Override
    protected Stream<Point> getMovementRange() {
        List<Point> moves = new ArrayList<>();
        transverseSteps.forEach(move -> {
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
        return team.getDescription() + "bishop";
    }
}
