package com.company.Components;


import com.company.GameActor;

import java.awt.*;
import java.util.EnumMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by joonaen on 8.2.2016.
 */
public abstract class ChessPiece implements GameActor {
    protected final ChessBoard board;
    protected boolean hasMoved;
    protected Point position;
    protected final Predicate<Point> hasFoe, hasFriend, isEmpty, kingStaysSafe, hasOpponentsKing;
    protected final ChessTeam team;
    protected static EnumMap<Direction, Consumer<Point>> singleSteps = new EnumMap<Direction, Consumer<Point>>(Direction.class);
    static{
        singleSteps.put(Direction.NORTH, p -> p.translate(0,-1));      singleSteps.put(Direction.NORTH_EAST, p -> p.translate(1, -1));
        singleSteps.put(Direction.EAST, p -> p.translate(1, 0));       singleSteps.put(Direction.SOUT_EAST, p -> p.translate(1, 1));
        singleSteps.put(Direction.SOUTH, p -> p.translate(0, 1));      singleSteps.put(Direction.SOUT_WEST, p -> p.translate(-1,1));
        singleSteps.put(Direction.WEST, p -> p.translate(-1,0));       singleSteps.put(Direction.NORTH_WEST, p -> p.translate(-1,-1));
    }


    public ChessPiece(Point initialPosition, ChessTeam team, ChessBoard board){
        this.board=board;
        this.team=team;
        this.position=initialPosition;
        board.set(position, this);
        hasMoved=false;
        /*Predicates*/
        this.isEmpty = p -> board.isEmpty(p);
        this.hasFoe = p -> team.getOpponent().getMembers().stream().anyMatch(foe -> foe.position.equals(p));
        this.hasFriend = p -> team.getMembers().stream().anyMatch(friend -> friend.position.equals(p));
        this.hasOpponentsKing = p -> team.getOpponent().getKing().position.equals(p);
        //kingStaysSafe should only be called with positions that are on the grid and are enemy or occypyed by enemy
        this.kingStaysSafe = p -> {
            boolean check;
            board.clear(position);
            ChessPiece enemy = board.get(p);
            if(enemy!=null)
                team.remove(enemy);
            Point originalPosition = position;
            setPosition(p);
            check = !team.kingIsThreatened();
            setPosition(originalPosition);
            if(enemy!=null)
                enemy.setPosition(p);
            return check;
        };
    }

    /*Template method*/
    @Override
    public final Stream<Point> getOptions() {
        if(team.hasTurn()) {
            return getMovementRange().filter(location -> kingStaysSafe.or(hasOpponentsKing).test(location));
        }else{
            return Stream.empty();
        }
    }

    @Override
    public boolean hasTurn() {
        return team.hasTurn();
    }

    //moveTo should never be called inside from method 'getMovementRange' is same as setPosition but it is public and it does set hasMoved to true
    public final void moveTo(Point newPos){
        System.out.println("has turn was " + hasTurn());
        hasMoved=true;
        System.out.println("moving");
        setPosition(newPos);
    }

    public final Point getPosition(){
        return position;
    }

    //SetPosition is same as moveTo but it is protected and does not set hasMoved to true. Allso it removes any potential enemys from the position
    protected final void setPosition(Point newPos){
        if(ChessBoard.validLocation(position)) {
            board.clear(position);
        }
        if(ChessBoard.validLocation(newPos)) {
            if(hasFoe.test(newPos)){
                team.remove(board.get(newPos));
            }
            board.set(newPos, this);
        }
        position=newPos;
    }
    /*getMovementRange does not have to care about teams kings safety.
            DO NOT call methods 'safeMoves' or 'moveTo' (instead of moveTo use setPosition when you have to)
            method inside from from getMovementRange*/
    protected abstract Stream<Point> getMovementRange();
}
