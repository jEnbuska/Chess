package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by WinNabuska on 10.2.2016.
 */
public class RookChessPiece  extends ChessPiece {

    private static List<Consumer<Point>> perpendicularMoves = Arrays.asList(
            ChessPiece.oneStepMoves.get(Direction.NORTH), ChessPiece.oneStepMoves.get(Direction.EAST),
            ChessPiece.oneStepMoves.get(Direction.SOUTH), ChessPiece.oneStepMoves.get(Direction.WEST)
    );

    public RookChessPiece(Point initialPosition, ChessTeam team, ChessBoard board) {
        super(initialPosition, team, board);
    }

    @Override
    protected Stream<Point> possibleMoves() {
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
    public Stream<Point> safeMoves() {
        return possibleMoves().filter(location -> kingStaysSafe.or(hasOpponentsKing).test(location));
    }
}