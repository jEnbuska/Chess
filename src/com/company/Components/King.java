package com.company.Components;

import java.awt.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.Math.abs;

/**
 * Created by joonaen on 9.2.2016.
 */
public class King extends ChessPiece{

    private final Predicate<Point>isOneStepAway;

    public King(Point position, ChessTeam team, ChessBoard board) {
        super(position, team, board);
        isOneStepAway = p ->
                (abs(p.x-position.x)<2 && abs(p.y-position.y)<2) &&
                        (abs(p.x-position.x) == 1 || abs(p.y-position.y) == 1);
    }

    @Override
    protected Stream<Point> possibleMoves() {
        return ChessBoard.coords()
                .filter(p -> isOneStepAway.test(p))
                .filter(p -> isEmpty.or(hasFoe).test(p));
    }
}
