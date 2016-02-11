package com.company;


import java.awt.*;
import java.util.EnumMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by joonaen on 8.2.2016.
 */
public abstract class ChessPiece {

    protected final ChessBoard board;
    protected Point position;
    protected final Predicate<Point> hasFoe, hasFriend, isEmpty, kingStaysSafe, hasOpponentsKing;
    protected final ChessTeam team;
    protected static EnumMap<Direction, Consumer<Point>> oneStepMoves = new EnumMap<Direction, Consumer<Point>>(Direction.class);
    static{
        oneStepMoves.put(Direction.NORTH, p -> p.translate(0,-1));      oneStepMoves.put(Direction.NORTH_EAST, p -> p.translate(1, -1));
        oneStepMoves.put(Direction.EAST, p -> p.translate(1, 0));       oneStepMoves.put(Direction.SOUT_EAST, p -> p.translate(1, 1));
        oneStepMoves.put(Direction.SOUTH, p -> p.translate(0, 1));      oneStepMoves.put(Direction.SOUT_WEST, p -> p.translate(-1,1));
        oneStepMoves.put(Direction.WEST, p -> p.translate(-1,0));       oneStepMoves.put(Direction.NORTH_WEST, p -> p.translate(-1,-1));
    }

    public ChessPiece(Point initialPosition, ChessTeam team, ChessBoard board){
        this.board=board;
        this.team=team;
        this.position=initialPosition;
        board.set(position, this);
        this.isEmpty = p -> board.isEmpty(p);
        hasFoe = p -> team.getOpponent().getMembers().anyMatch(foe -> foe.position.x==p.x && foe.position.y==p.x);
        hasFriend = p -> team.getMembers().anyMatch(friend -> friend.position.x==p.x && friend.position.y==p.x);
        hasOpponentsKing = p -> team.getOpponent().getKing().position.equals(p);
        kingStaysSafe = p -> {
            boolean check;
            if(!team.kingIsThreatened()) {
                board.clear(position);
                ChessPiece placeHolder = board.get(p);
                board.set(p, this);
                check = !team.kingIsThreatened();
                board.set(p, placeHolder);
                board.set(this.position, this);
            } else {
                check=true;
            }
            return check;
        };
    }


    protected abstract Stream<Point> possibleMoves();
    public abstract Stream<Point> safeMoves();

    public final void moveTo(Point newPos){
        board.clear(position);
        if(ChessBoard.validLocation(newPos))
            board.set(newPos, this);
        position=newPos;
    }

    public final Point getPosition(){
        return position;
    }
}
