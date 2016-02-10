package com.company;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static java.lang.Math.*;

/**
 * Created by joonaen on 8.2.2016.
 */
public abstract class ChessPiece {

    protected final ChessBoard board;
    private final KingChessPiece king;
    private final List<ChessPiece> friends, foes;
    protected Point position;
    protected final Predicate<Point> hasFoe, doesNotCompromiseTheKing;

    public ChessPiece(ChessBoard board, Point position, @Nullable KingChessPiece king, List<ChessPiece> friends, List<ChessPiece> foes){
        this.board = board;
        this.position=position;
        this.king=king;
        this.friends=friends;
        this.foes=foes;
        hasFoe = p ->  foes().anyMatch(foesPos -> foesPos.equals(p));
        doesNotCompromiseTheKing = p -> {
            boolean chess;
            if(!king.isThreatened()) {
                board.put(position, Optional.empty());
                board.put(p, Optional.of(this));
                chess = king.isThreatened();
                board.put(position, Optional.of(this));
                board.put(p, Optional.empty());
            } else {
                chess=false;
            }
            return chess;
        };
    }

    protected Stream<ChessPiece> foes(){
        return foes.stream();
    }

    public Point getPosition(){
        return position;
    }

    public abstract Stream<Point> possibleMoves();

    public final void moveTo(Point p){
        board.set(position, Optional.empty());
        board.set(p,Optional.of(this));
        position=p;
    }
}
