package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static java.lang.Math.*;

/**
 * Created by joonaen on 9.2.2016.
 */
public class KingChessPiece extends ChessPiece{

    private final Predicate<Point>isOneStepAway;

    public KingChessPiece(Map<Point, Optional<ChessPiece>> board, Point position, List<ChessPiece> friends, List<ChessPiece> foes) {
        super(board, position, null, friends, foes);
        isOneStepAway = p ->
                (abs(p.x-position.x)<2 && abs(p.y-position.y)<2) &&
                        (abs(p.x-position.x) == 1 || abs(p.y-position.y) == 1);
    }

    @Override
    public Stream<Point> possibleMoves() {
        return Chess.coords()
                .filter(p -> isOneStepAway.and(doesNotCompromiseTheKing).test(p))
                .filter(p -> isEmpty.or(hasFoe).test(p));
    }

    public boolean isThreatened(){
        return foes()
                .anyMatch(foe -> foe.possibleMoves()
                        .anyMatch(coord -> coord.x==position.x && coord.y==position.y));
    }
}
