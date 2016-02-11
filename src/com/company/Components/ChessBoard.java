package com.company.Components;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by joonaen on 9.2.2016.
 */
public class ChessBoard {

    private ChessPiece[][] grid;
    public final static int GRID_SIZE = 8;

    public ChessBoard(){
        grid =  new ChessPiece[22][10];
    }
    public static final Point [][] coords = new Point[8][8];
    static{
        IntStream.range(0,ChessBoard.GRID_SIZE)
                .forEach(y ->
                IntStream.range(0,ChessBoard.GRID_SIZE)
                        .forEach(x -> coords[x][y] = new Point(x,y)));
    }
    public static Point coord(int x, int y){
        try {
            return coords[x][y];
        }catch (IndexOutOfBoundsException e){
            return new Point(x,y);
        }
    }
    public static Stream<Point> coords(){
        return Arrays.stream(coords).flatMap(row -> Arrays.stream(row).map(Function.identity()));
    }
    public static Point coord(Point p){return coord(p.x,p.y);}
    public static boolean validLocation(Point p){
        return p.x>=0 && p.y>=0 && p.x<GRID_SIZE && p.y<GRID_SIZE;
    }
    public boolean isEmpty(int x, int y){
        return grid[x][y]==null;
    }
    public boolean isEmpty(Point p){
        return isEmpty(p.x,p.y);
    }
    public ChessPiece get(int x, int y){
        return grid[x][y];
    }
    public ChessPiece get(Point p){
        return get(p.x,p.y);
    }
    public void set(int x, int y, ChessPiece piece){try{grid[x][y]=piece; }catch (IndexOutOfBoundsException e){System.err.println("only for testing" + piece.toString() + " not set");}}
    public void set(Point p, ChessPiece piece){set(p.x, p.y, piece); }
    public void clear(int x, int y){grid[x][y]=null;}
    public void clear(Point p){clear(p.x,p.y);}
    /*
    public void move(ChessPiece piece, int x, int y){
        Point current = piece.getPosition();
        grid[current.x][current.y]=Optional.empty();
        grid[x][y] = Optional.of(piece);
    }*/


}
