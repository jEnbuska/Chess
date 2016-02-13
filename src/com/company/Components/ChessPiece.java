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
        this.hasFoe = p -> team.getOpponent().getMembers().anyMatch(foe -> foe.position.equals(p));
        this.hasFriend = p -> team.getMembers().anyMatch(friend -> friend.position.equals(p));
        this.hasOpponentsKing = p -> team.getOpponent().getKing().position.equals(p);
        this.kingStaysSafe = p -> {
            boolean check;
            board.clear(position);
            ChessPiece placeHolder = board.get(p);
            if(placeHolder!=null)
                team.remove(placeHolder);
            Point originalPosition = position;
            setPosition(p);
            check = !team.kingIsThreatened();
            setPosition(originalPosition);
            if(placeHolder!=null)
                placeHolder.setPosition(p);
            return check;
        };
    }

    /*Template method*/
    @Override
    public final Stream<Point> getOptions() {
        return getMovementRange().filter(location -> kingStaysSafe.or(hasOpponentsKing).test(location));
    }
    //moveTo should never be called inside from method 'getMovementRange' is same as setPosition but it is public and it does set hasMoved to true
    public final void moveTo(Point newPos){
        hasMoved=true;
        setPosition(newPos);
    }

    public final Point getPosition(){
        return position;
    }

    //SetPosition is same as moveTo but it is protected and does not set hasMoved to true
    protected final void setPosition(Point newPos){
        if(ChessBoard.validLocation(position))
            board.clear(position);
        if(ChessBoard.validLocation(newPos))
            board.set(newPos, this);
        position=newPos;
    }

    /*getMovementRange does not have to care about teams kings safety.
        DO NOT call methods 'safeMoves' or 'moveTo' (instead of moveTo use setPosition when you have to)
        method inside from from getMovementRange*/
    protected abstract Stream<Point> getMovementRange();
}
