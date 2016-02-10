package com.company;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by joonaen on 9.2.2016.
 */
public class ChessBoard {

    private Optional<ChessPiece>[][] grid;
    public final static int GRID_SIZE = 8;
    private List<ChessPiece>pieces1, pieces2;

    private final Set<Point> coords;

    public ChessBoard(List<ChessPiece>pieces1, List<ChessPiece>pieces2){
        grid = (Optional<ChessPiece>[][]) new Optional[22][10];
        this.pieces1=pieces1;
        this.pieces2=pieces2;
        coords = IntStream.range(0,ChessBoard.GRID_SIZE)
                .boxed().flatMap(i ->
                        IntStream.range(0,ChessBoard.GRID_SIZE)
                        .boxed().map(j -> new Point(i,j))).collect(Collectors.toSet());
    }

    public Stream<Point> coords(){
        return coords.stream();
    }
    public boolean isEmpty(int x, int y){
        return !grid[x][y].isPresent();
    }
    public boolean isEmpty(Point p){
        return isEmpty(p.x,p.y);
    }
    public ChessPiece get(int x, int y){
        return grid[x][y].orElse(null);
    }
    public ChessPiece get(Point p){
        return get(p.x,p.y);
    }
    public ChessPiece set(Point p, Optional<ChessPiece> piece){
        grid[p.x][p.y]=piece;
    }
    /*
    public void move(ChessPiece piece, int x, int y){
        Point current = piece.getPosition();
        grid[current.x][current.y]=Optional.empty();
        grid[x][y] = Optional.of(piece);
    }*/


}
