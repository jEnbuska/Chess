package com.company;

import java.awt.Point;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by joona on 10/02/2016.
 */
public class QueenChessPiece extends ChessPiece {

    Predicate<Point> isAtPerpendicularLine, isAtAngleBisectorLine;

    public QueenChessPiece(ChessBoard board, Point position, KingChessPiece king, List<ChessPiece> friends, List<ChessPiece> foes) {
        super(board, position, king, friends, foes);
        isAtPerpendicularLine = p -> position.x==p.x ^ position.y == p.y; // Same position is not allowed
        List<Point> rightUp = new ArrayList<>();
        for (int x = position.x+1, y = position.y+1; x<ChessBoard.GRID_SIZE && y<ChessBoard.GRID_SIZE ; x++, y++) {
            if()
        }
        isAtAngleBisectorLine = p ->  {
            Point copyP = p.getLocation();
            copyP.move(-position.x, -position.y); //TODO ei toimi
            return (copyP.x==copyP.y) && copyP.x>0;// Same position is not allowed
        };
    }

    @Override
    public Stream<Point> possibleMoves() {
        return null;
    }
}
