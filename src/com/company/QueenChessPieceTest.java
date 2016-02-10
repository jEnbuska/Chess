package com.company;

import javafx.beans.property.SimpleMapProperty;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by joona on 10/02/2016.
 */
public class QueenChessPieceTest {

    private Chess chess;
    private Map<Point, Optional<ChessPiece>> grid;
    private KingChessPiece king;
    private List<ChessPiece> friends;
    private List<ChessPiece> foes;

    @org.junit.Before
    public void setUp() throws Exception {
        chess = new Chess();
        grid = (HashMap)Chess.coords().collect(Collectors.toMap(c-> c, v -> Optional.empty()));
        friends=new ArrayList<>(); foes = new ArrayList<>();
        king=new KingChessPiece(grid,new Point(3,0),friends, foes);
        grid.put(new Point(3,0), Optional.of(king));
        friends.add(king);
    }

    @org.junit.Test
    public void testPossibleMoves() throws Exception {
        QueenChessPiece queen = new QueenChessPiece(grid, new Point(4,0), king,friends,foes);
        Set<Point> actual = queen.possibleMoves().collect(Collectors.toSet());
        Set<Point> expected = new HashSet<>(Arrays.asList(
                new Point(4,1), new Point(5,2), new Point(6,3), new Point(7,4),
                new Point(2,1), new Point(1,2), new Point(0,3)
        ));
        assertEquals(actual,expected);
    }
}